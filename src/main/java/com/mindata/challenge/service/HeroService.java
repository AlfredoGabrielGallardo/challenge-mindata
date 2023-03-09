package com.mindata.challenge.service;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface HeroService {

    HeroResponseDTO createHero(HeroRequestDTO hero);
    void updateHero(Long id, HeroRequestDTO hero);
    void deleteHero(Long id);
    HeroResponseDTO getHeroById(Long id);
    List<HeroResponseDTO> getHeroesByName(String name);
    Page<HeroResponseDTO> getAllHeroes(PageRequest pagination);
}
