package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ParamDto;
import ru.practicum.ewm.dto.StatDto;
import ru.practicum.ewm.mapper.HitMapper;
import ru.practicum.ewm.mapper.StatMapper;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.model.ParamStat;
import ru.practicum.ewm.repository.StatsRepository;
import ru.practicum.ewm.model.Stat;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StartsServiceImpl implements StatsService{
    private final StatsRepository repository;
    private final HitMapper hitMapper;
    private final StatMapper statMapper;

    @Override
    @Transactional
    public void addHit(HitDto paramHitDto) {
        log.info("StartsServiceImpl/addHit args: {}", paramHitDto);
        Hit hit = hitMapper.map(paramHitDto);
        repository.save(hit);
    }

    @Override
    public List<StatDto> getStats(ParamDto paramStatDto) {
        log.info("StartsServiceImpl/getStats args: {}", paramStatDto);
        ParamStat par = statMapper.map(paramStatDto);
        List<Stat> result = repository.getStat(par.getStart(), par.getEnd(), par.getUris(), par.getUnique());
        return result.stream()
                .map(statMapper::map)
                .collect(Collectors.toList());
    }
}
