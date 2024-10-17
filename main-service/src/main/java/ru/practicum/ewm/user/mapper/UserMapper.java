package ru.practicum.ewm.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.dto.UserShortDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserShortDto map(User user);
}
