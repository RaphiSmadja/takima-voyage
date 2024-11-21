package com.takima.demo.mapper;

import com.takima.demo.dto.VoyageurDTO;
import com.takima.demo.model.Voyageur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoyageurMapper {
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "type", source = "category.type")
    @Mapping(target = "voyageId", source = "category.voyage.id")
    VoyageurDTO toDTO(Voyageur voyageur);

    @Mapping(target = "category", ignore = true)
    Voyageur toEntity(VoyageurDTO voyageurDTO);
}