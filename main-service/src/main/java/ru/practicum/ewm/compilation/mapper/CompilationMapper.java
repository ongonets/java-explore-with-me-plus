package ru.practicum.ewm.compilation.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import org.mapstruct.Mapper;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompilationMapper {

    CompilationDto mapToCompilationDto(Compilation compilation);

    @Mapping(target = "events", source = "events")
    Compilation mapToCompilation(NewCompilationDto compilationDto, List<Event> events);



}
