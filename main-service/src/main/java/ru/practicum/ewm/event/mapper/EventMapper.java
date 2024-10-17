package ru.practicum.ewm.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.dto.StatDto;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.Location;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.mapper.UserMapper;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
uses = {UserMapper.class, CategoryMapper.class})
public interface EventMapper {

    @Mapping(target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(expression = "java(newEvent.getLocation().getLat())", target = "latitude")
    @Mapping(expression = "java(newEvent.getLocation().getLon())", target = "longitude")
    @Mapping(target = "category", ignore = true)
    Event map(NewEventDto newEvent);

    @Mapping(source = "statDto.hits", target = "views")
    EventFullDto mapToFullDto(Event event, StatDto statDto);

    @Mapping(source = "statDto.hits", target = "views")
    EventShortDto mapToShortDto(Event event, StatDto statDto);

    @Mapping(source = "latitude", target = "lat")
    @Mapping(source = "longitude", target = "lon")
    Location map(Event event);
}