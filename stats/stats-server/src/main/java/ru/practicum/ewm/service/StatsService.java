package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ParamDto;
import ru.practicum.ewm.dto.StatDto;

import java.util.List;

public interface StatsService {
    void addHit(HitDto paramHitDto);

    List<StatDto> getStats(ParamDto paramStatDto);
}
