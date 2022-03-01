package org.unibayreuth.gnumaexperiments.service;

import org.springframework.stereotype.Service;
import org.unibayreuth.gnumaexperiments.dataModel.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dto.HyperParameterDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service(DTOConverterService.NAME)
public class DTOConverterServiceBean implements DTOConverterService {
    /**
     * Convert list of {@link HyperParameterDTO} objects into a list of {@link HyperParameter} objects
     * @param hyperParameterDTOList - given list of dto objects
     * @return - converted list
     */
    @Override
    public List<HyperParameter> createHyperParametersFromDTO(List<HyperParameterDTO> hyperParameterDTOList) {
        return Objects.isNull(hyperParameterDTOList) ? new ArrayList<>() :
                hyperParameterDTOList
                .stream()
                .map(this::createHyperParameterFromDTO)
                .collect(Collectors.toList());
    }

    private HyperParameter createHyperParameterFromDTO(HyperParameterDTO hyperParameterDTO) {
        return new HyperParameter(hyperParameterDTO.getKey(), hyperParameterDTO.getType(), hyperParameterDTO.isOptional(),
                hyperParameterDTO.getDefaultValue(), hyperParameterDTO.getValueList(), hyperParameterDTO.getUpperBound(), hyperParameterDTO.getLowerBound());
    }
}
