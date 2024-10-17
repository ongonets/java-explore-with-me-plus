package ru.practicum.ewm.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserShortDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserShortDto map(User user);
}
