package com.mindata.challenge.service;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import com.mindata.challenge.model.dto.mapper.HeroMapper;
import com.mindata.challenge.model.entity.Hero;
import com.mindata.challenge.model.exception.DuplicatedHeroException;
import com.mindata.challenge.model.exception.HeroNotFoundException;
import com.mindata.challenge.model.exception.InvalidHeroIdException;
import com.mindata.challenge.repository.HeroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final HeroMapper heroMapper;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, HeroMapper heroMapper) {
        this.heroRepository = heroRepository;
        this.heroMapper = heroMapper;
    }

    @Override
    public HeroResponseDTO createHero(HeroRequestDTO hero) {

        Hero newHero = new Hero();
        newHero.setName(hero.getName());

        try {
            Hero heroCreated = this.heroRepository.save(newHero);
            return heroMapper.toDto(heroCreated);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedHeroException(
                    String.format("There is already a super hero with name: %s", hero.getName()));
        }
    }

    @Override
    public void updateHero(Long id, HeroRequestDTO hero) {

        try {
            this.heroRepository.findById(id)
                    .map(heroObtained -> {
                        heroObtained.setName(hero.getName());
                        return this.heroRepository.save(heroObtained);
                    })
                    .orElseThrow(() -> new HeroNotFoundException(String.format("Hero with id: %d not found", id)));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidHeroIdException(String.format("You are using an invalid hero id: %d", id));
        }
    }

    @Override
    public void deleteHero(Long id) {

        try {
            this.heroRepository.findById(id)
                    .ifPresentOrElse(
                            heroObtained -> heroRepository.deleteById(heroObtained.getId()),
                            () -> {throw new HeroNotFoundException(String.format("Hero with id: %d not found", id));});
        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidHeroIdException(String.format("You are using an invalid hero id: %d", id));
        }
    }

    @Override
    public HeroResponseDTO getHeroById(Long id) {
        try {
            Hero heroObtained = heroRepository.findById(id).orElseThrow(
                    () -> new HeroNotFoundException(String.format("Hero with id: %d not found", id)));
            return heroMapper.toDto(heroObtained);

        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidHeroIdException(String.format("You are using an invalid hero id: %d", id));
        }
    }

    @Override
    public List<HeroResponseDTO> getHeroesByName(String name) {

        List<Hero> heroesObtainedList = this.heroRepository.findByNameContainingIgnoreCaseOrderByIdAsc(name);
        return heroMapper.toListDto(heroesObtainedList);
    }

    @Override
    public Page<HeroResponseDTO> getAllHeroes(PageRequest pagination) {

        Page<Hero> heroesObtainedPage = this.heroRepository.findAll(pagination);
        return heroMapper.toPageDto(heroesObtainedPage);
    }
}
