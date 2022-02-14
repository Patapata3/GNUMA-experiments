package org.unibayreuth.gnumaexperiments.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unibayreuth.gnumaexperiments.dataModel.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dataModel.enums.HyperParameterType;
import org.unibayreuth.gnumaexperiments.exceptions.ExperimentValidationException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;

@Service(ValidationService.NAME)
public class ValidationServiceBean implements ValidationService {
    private final Logger log = LoggerFactory.getLogger(ValidationServiceBean.class);

    private final Map<HyperParameterType, Predicate<String>> typeValidatorMap = Map.of(
            HyperParameterType.BOOLEAN, this::isBoolean,
            HyperParameterType.DOUBLE, this::isDouble,
            HyperParameterType.INTEGER, this::isInteger);

    @Override
    public void validateHyperParameters(Map<String, String> hyperParameterValues, List<HyperParameter> hyperParameters) throws ExperimentValidationException {
        log(log::info, "Started experiment parameter validation");
        Preconditions.checkNotNull(hyperParameterValues, "Map of hyper parameter values cannot be null");
        Preconditions.checkNotNull(hyperParameters, "List of classifier hyper parameters cannot be null");

        log(log::debug, "Checking for missing mandatory parameters");
        List<String> missingParameterKeys = hyperParameters.stream()
                .filter(param -> !param.isOptional() && paramAbsent(param, hyperParameterValues))
                .map(HyperParameter::getKey)
                .collect(Collectors.toList());

        if (!missingParameterKeys.isEmpty()) {
            String errorMessage = String.format("Missing classifier hyperParameters: %s",
                    Arrays.toString(missingParameterKeys.toArray()));
            log(log::error, errorMessage);
            throw new ExperimentValidationException(errorMessage);
        }

        log(log::debug, "Parameter type validation");
        List<HyperParameter> validatedParameters = hyperParameters.stream()
                .filter(param -> hyperParameterValues.containsKey(param.getKey()))
                .collect(Collectors.toList());

        String typeErrorString = validatedParameters.stream()
                .filter(param -> invalidType(hyperParameterValues.get(param.getKey()), param))
                .map(param -> String.format("Incorrect format of param {%s}: value {%s} is not of type {%s}",
                        param.getKey(), hyperParameterValues.get(param.getKey()), param.getType().getId()))
                .collect(Collectors.joining(";\n"));

        if (!typeErrorString.isEmpty()) {
            log(log::error, typeErrorString);
            throw new ExperimentValidationException(typeErrorString);
        }

        log(log::debug, "Validation of values that should belong to a value list");

        String valueErrorString = validatedParameters.stream()
                .filter(param -> !CollectionUtils.isEmpty(param.getValueList()))
                .filter(param -> !param.getValueList().contains(hyperParameterValues.get(param.getKey())))
                .map(param -> String.format("Param {%s} value {%s} is not in the list of permitted values: %s",
                        param.getKey(), hyperParameterValues.get(param.getKey()), Arrays.toString(param.getValueList().toArray())))
                .collect(Collectors.joining(";\n"));

        if (!valueErrorString.isEmpty()) {
            log(log::error, valueErrorString);
            throw new ExperimentValidationException(valueErrorString);
        }
        log(log::info, "Validation finished successfully");
    }

    private boolean paramAbsent(HyperParameter param, Map<String, String> hyperParameterValues) {
        return !hyperParameterValues.containsKey(param.getKey()) ||
                (Strings.isNullOrEmpty(param.getDefaultValue()) && Strings.isNullOrEmpty(hyperParameterValues.get(param.getKey())));
    }

    private boolean invalidType(String paramValue, HyperParameter paramConfig) {
        return paramConfig.getType() != HyperParameterType.STRING && !typeValidatorMap.get(paramConfig.getType()).test(paramValue);
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return Strings.isNullOrEmpty(value);
        }
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return Strings.isNullOrEmpty(value);
        }
    }

    private boolean isBoolean(String value) {
        return !Objects.isNull(value) && ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value));
    }
}
