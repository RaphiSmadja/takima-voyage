package com.takima.demo.mapper;

import com.takima.demo.dto.CategoryDTO;
import com.takima.demo.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);
}
