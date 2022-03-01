package org.unibayreuth.gnumaexperiments.service;

import org.unibayreuth.gnumaexperiments.dataModel.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dto.HyperParameterDTO;

import java.util.List;

public interface DTOConverterService {
    String NAME = "gnuma_DTOConverterService";

    /**
     * Convert list of {@link HyperParameterDTO} objects into a list of {@link HyperParameter} objects
     * @param hyperParameterDTOList - given list of dto objects
     * @return - converted list
     */
    List<HyperParameter> createHyperParametersFromDTO(List<HyperParameterDTO> hyperParameterDTOList);
}
