package ru.practicum.ewm.compilation.controller;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor

public class CompilationController {

    @Autowired
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(defaultValue = "10", required = false) Integer size,
                                                /*количество элементов в наборе*/
                                                @Size(min = 0)
                                                @RequestParam(defaultValue = "0", required = false) Integer from,
                                                /*количество элементов, которые нужно пропустить для формирования
                                                текущего набора*/
                                                @RequestParam(required = false) Boolean pinned
                                                /*искать только закрепленные/
                                                не закрепленные подборки*/) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Long compId) {
        return compilationService.getCompilation(compId);
    }

}