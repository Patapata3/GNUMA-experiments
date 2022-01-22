package org.unibayreuth.gnumaexperiments.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.HyperParameterType;
import org.unibayreuth.gnumaexperiments.exceptions.ExperimentValidationException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service(ValidationService.NAME)
public class ValidationServiceBean implements ValidationService {
    private Map<HyperParameterType, Predicate<String>> typeValidatorMap = Map.of(
            HyperParameterType.BOOLEAN, this::isBoolean,
            HyperParameterType.DOUBLE, this::isDouble,
            HyperParameterType.INTEGER, this::isInteger);

    @Override
    public void validateHyperParameters(Map<String, String> hyperParameterValues, List<HyperParameter> hyperParameters) throws ExperimentValidationException {
        Preconditions.checkNotNull(hyperParameterValues, "Map of hyper parameter values cannot be null");
        Preconditions.checkNotNull(hyperParameters, "List of classifier hyper parameters cannot be null");

        List<String> missingParameterKeys = hyperParameters.stream()
                .filter(param -> !param.isOptional() && paramAbsent(param, hyperParameterValues))
                .map(HyperParameter::getKey)
                .collect(Collectors.toList());

        if (!missingParameterKeys.isEmpty()) {
            throw new ExperimentValidationException(String.format("Missing classifier hyperParameters: %s",
                    Arrays.toString(missingParameterKeys.toArray())));
        }

        List<HyperParameter> validatedParameters = hyperParameters.stream()
                .filter(param -> hyperParameterValues.containsKey(param.getKey()))
                .collect(Collectors.toList());

        String typeErrorString = validatedParameters.stream()
                .filter(param -> invalidType(hyperParameterValues.get(param.getKey()), param))
                .map(param -> String.format("Incorrect format of param %s: value %s is not of type %s",
                        param.getKey(), hyperParameterValues.get(param.getKey()), param.getType().getId()))
                .collect(Collectors.joining(";\n"));

        if (!typeErrorString.isEmpty()) {
            throw new ExperimentValidationException(typeErrorString);
        }

        String valueErrorString = validatedParameters.stream()
                .filter(param -> !CollectionUtils.isEmpty(param.getValueList()))
                .filter(param -> !param.getValueList().contains(hyperParameterValues.get(param.getKey())))
                .map(param -> String.format("Param %s value %s is not in the list of permitted values: %s",
                        param.getKey(), hyperParameterValues.get(param.getKey()), Arrays.toString(param.getValueList().toArray())))
                .collect(Collectors.joining(";\n"));

        if (!valueErrorString.isEmpty()) {
            throw new ExperimentValidationException(valueErrorString);
        }
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
            return false;
        }
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String value) {
        return !Objects.isNull(value) && ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value));
    }
}
