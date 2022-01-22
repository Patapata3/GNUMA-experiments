package org.unibayreuth.gnumaexperiments.service;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;
import org.unibayreuth.gnumaexperiments.exceptions.ExperimentValidationException;

import java.util.List;
import java.util.Map;

public interface ValidationService {
    String NAME = "gnuma_ValidationService";
    void validateHyperParameters(Map<String, String> hyperParameterValues, List<HyperParameter> hyperParameters) throws ExperimentValidationException;
}
