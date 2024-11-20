package com.takima.demo.mapper;

import com.takima.demo.dto.VoyageDTO;
import com.takima.demo.model.Voyage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoyageMapper {

    VoyageDTO toDTO(Voyage voyage);

    Voyage toEntity(VoyageDTO voyageDTO);
}
