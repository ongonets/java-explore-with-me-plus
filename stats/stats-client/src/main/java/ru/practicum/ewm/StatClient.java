package ru.practicum.ewm;

import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ParamDto;
import ru.practicum.ewm.dto.StatDto;

import java.util.List;


public interface StatClient {

    public void hit(HitDto hitDto);

    public List<StatDto> stat(ParamDto paramDto);
}
