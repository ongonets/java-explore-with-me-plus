package ru.practicum.ewm;

import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ParamDto;
import ru.practicum.ewm.dto.StatDto;


public interface StatClient {
    public void hit (HitDto hitDto);
    public StatDto stat (ParamDto paramDto);
}
