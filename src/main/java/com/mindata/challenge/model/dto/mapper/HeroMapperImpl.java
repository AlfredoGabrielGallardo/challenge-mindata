package com.mindata.challenge.model.dto.mapper;

import com.mindata.challenge.model.dto.HeroResponseDTO;
import com.mindata.challenge.model.entity.Hero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HeroMapperImpl implements HeroMapper {

    @Override
    public HeroResponseDTO toDto(Hero hero) {
        return new HeroResponseDTO(hero.getId(), hero.getName());
    }

    @Override
    public List<HeroResponseDTO> toListDto(List<Hero> heroes) {
        return heroes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<HeroResponseDTO> toPageDto(Page<Hero> heroes) {
        List<HeroResponseDTO> heroResponseDTOs = heroes.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(heroResponseDTOs, heroes.getPageable(), heroes.getTotalElements());
    }
}
