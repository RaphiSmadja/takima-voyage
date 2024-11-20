package com.takima.demo.mapper;

import com.takima.demo.dto.VoyageurDTO;
import com.takima.demo.model.Voyageur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoyageurMapper {

    @Mapping(target = "voyageId", source = "voyage.id")
    @Mapping(target = "categoryId", source = "category.displayName")
    VoyageurDTO toDTO(Voyageur voyageur);

    @Mapping(target = "voyage", ignore = true)
    @Mapping(target = "category", ignore = true)

    Voyageur toEntity(VoyageurDTO voyageurDTO);
}
