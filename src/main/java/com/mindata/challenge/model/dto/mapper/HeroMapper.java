package com.mindata.challenge.model.dto.mapper;

import com.mindata.challenge.model.dto.HeroResponseDTO;
import com.mindata.challenge.model.entity.Hero;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HeroMapper {
    HeroResponseDTO toDto(Hero hero);
    List<HeroResponseDTO> toListDto(List<Hero> heroes);
    Page<HeroResponseDTO> toPageDto(Page<Hero> heroes);
}
