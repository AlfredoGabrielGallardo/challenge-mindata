package com.mindata.challenge.controller;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/hero")
@ValidateOnExecution
public interface HeroController {

    @PostMapping()
    HeroResponseDTO createHero(@Valid @RequestBody HeroRequestDTO hero);
    @PutMapping(value = "/{id}")
    void updateHero(@PathVariable("id") Long id, @Valid @RequestBody HeroRequestDTO hero);
    @PutMapping(value = "/{id}")
    void deleteHero(@PathVariable("id") Long id);
    @GetMapping(value = "/{id}")
    HeroResponseDTO getHeroById(@PathVariable("id") Long id);
    @GetMapping(params = { "name" })
    List<HeroResponseDTO> getHeroesByName(@RequestParam("name") String name);
    @GetMapping(value = "/all", params = {"page", "size", "sort"})
    Page<HeroResponseDTO> getAllHeroes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort);
}
