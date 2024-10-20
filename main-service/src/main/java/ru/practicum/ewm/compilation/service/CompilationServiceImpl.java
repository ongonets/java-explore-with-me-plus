package ru.practicum.ewm.compilation.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.errorHandler.exception.NotFoundException;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = Compilation.builder()
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .events(new HashSet<>(events))
                .build();
        Compilation newComp = compilationRepository.save(compilation);
        return compilationMapper.mapToCompilationDto(newComp);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        var old = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Compilation not found"));
        var eventsIds = updateCompilationRequest.getEvents();
        if (eventsIds != null) {
            var events = eventRepository.findAllById(updateCompilationRequest.getEvents());
            old.setEvents(new HashSet<>(events));
        }
        if (updateCompilationRequest.getPinned() != null)
            old.setPinned(updateCompilationRequest.getPinned());
        if (updateCompilationRequest.getTitle() != null)
            old.setTitle(updateCompilationRequest.getTitle());

        var updated = compilationRepository.save(old);
        return compilationMapper.mapToCompilationDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Compilation not found"));
        return compilationMapper.mapToCompilationDto(compilation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (pinned == null) {
            return compilationRepository.findAll(pageable)
                    .stream()
                    .map(compilationMapper::mapToCompilationDto)
                    .collect(Collectors.toList());
        } else {
            return compilationRepository.getByPinnedOrderByPinnedAsc(pinned, pageable)
                    .stream()
                    .map(compilationMapper::mapToCompilationDto)
                    .collect(Collectors.toList());
        }
    }
}
