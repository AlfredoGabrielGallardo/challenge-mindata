package com.mindata.challenge.service;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HeroService {

    @Transactional
    HeroResponseDTO createHero(HeroRequestDTO hero);
    @Transactional
    void updateHero(Long id, HeroRequestDTO hero);
    @Transactional
    void deleteHero(Long id);
    HeroResponseDTO getHeroById(Long id);
    List<HeroResponseDTO> getHeroesByName(String name);
    Page<HeroResponseDTO> getAllHeroes(PageRequest pagination);
}
