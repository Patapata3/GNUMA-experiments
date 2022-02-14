package org.unibayreuth.gnumaexperiments.service;

import org.unibayreuth.gnumaexperiments.dataModel.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dto.HyperParameterDTO;

import java.util.List;

public interface DTOConverterService {
    String NAME = "gnuma_DTOConverterService";

    List<HyperParameter> createHyperParametersFromDTO(List<HyperParameterDTO> hyperParameterDTOList);
}
