package ru.practicum.ewm.compilation.mapper;

import org.mapstruct.MappingConstants;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import org.mapstruct.Mapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompilationMapper {

    CompilationDto mapToCompilationDto(Compilation compilation);

}
