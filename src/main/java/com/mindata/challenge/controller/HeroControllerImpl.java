package com.mindata.challenge.controller;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import com.mindata.challenge.service.HeroService;
import com.mindata.challenge.util.MeasureExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HeroControllerImpl implements HeroController {

    private final HeroService heroService;

    @Autowired
    public HeroControllerImpl(HeroService heroService) {
        this.heroService = heroService;
    }

    @Override
    @MeasureExecutionTime
    @CacheEvict(allEntries = true, value = {"heroById", "heroesByName", "allHeroes"})
    public HeroResponseDTO createHero(HeroRequestDTO hero) {
        return this.heroService.createHero(hero);
    }

    @Override
    @MeasureExecutionTime
    @CacheEvict(allEntries = true, value = {"heroById", "heroesByName", "allHeroes"})
    public void updateHero(Long id, HeroRequestDTO hero) {
        this.heroService.updateHero(id, hero);
    }

    @Override
    @MeasureExecutionTime
    @CacheEvict(allEntries = true, value = {"heroById", "heroesByName", "allHeroes"})
    public void deleteHero(Long id) {
        this.heroService.deleteHero(id);
    }

    @Override
    @MeasureExecutionTime
    @Cacheable(cacheNames = "heroById", key = "#id")
    public HeroResponseDTO getHeroById(Long id) {
        return this.heroService.getHeroById(id);
    }

    @Override
    @MeasureExecutionTime
    @Cacheable(cacheNames = "heroesByName", key = "#name")
    public List<HeroResponseDTO> getHeroesByName(String name) {
        return this.heroService.getHeroesByName(name);
    }

    @Override
    @MeasureExecutionTime
    @Cacheable(cacheNames = "allHeroes", key = "#page.toString() + #size.toString() + #sort")
    public Page<HeroResponseDTO> getAllHeroes(int page, int size, String sort) {

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));

        return this.heroService.getAllHeroes(pageRequest);
    }
}
