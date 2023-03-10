package com.mindata.challenge.controller;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/hero")
@ValidateOnExecution
@Tag(name = "[OPERATIONS]", description = "Hero related operations")
public interface HeroController {

    @PostMapping()
    @Operation(summary = "Create Hero")
    HeroResponseDTO createHero(@Valid @RequestBody HeroRequestDTO hero);
    @PutMapping(value = "/{id}")
    @Operation(summary = "Update Hero")
    void updateHero(@PathVariable("id") Long id, @Valid @RequestBody HeroRequestDTO hero);
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete Hero")
    void deleteHero(@PathVariable("id") Long id);
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get Hero by ID")
    HeroResponseDTO getHeroById(@PathVariable("id") Long id);
    @GetMapping(params = { "name" })
    @Operation(summary = "Get Heroes by name")
    List<HeroResponseDTO> getHeroesByName(@RequestParam("name") String name);
    @GetMapping(value = "/all", params = {"page", "size", "sort"})
    @Operation(summary = "Get all Heroes")
    Page<HeroResponseDTO> getAllHeroes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort);
}
