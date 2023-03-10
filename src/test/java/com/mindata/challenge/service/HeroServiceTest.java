package com.mindata.challenge.service;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import com.mindata.challenge.model.dto.mapper.HeroMapper;
import com.mindata.challenge.model.entity.Hero;
import com.mindata.challenge.model.exception.DuplicatedHeroException;
import com.mindata.challenge.model.exception.HeroNotFoundException;
import com.mindata.challenge.model.exception.InvalidHeroIdException;
import com.mindata.challenge.repository.HeroRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class HeroServiceTest {

    @InjectMocks
    private HeroServiceImpl heroService;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private HeroMapper heroMapper;

    @Test
    @DisplayName("Create Hero")
    public void createHero_shouldReturn_HeroResponseDTO() {

        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Superman");

        Hero heroCreated = new Hero();
        heroCreated.setId(1L);
        heroCreated.setName("Superman");

        HeroResponseDTO expectedResponse = new HeroResponseDTO();
        expectedResponse.setId(1L);
        expectedResponse.setName("Superman");

        Mockito.when(heroRepository.save(any())).thenReturn(heroCreated);
        Mockito.when(heroMapper.toDto(heroCreated)).thenReturn(expectedResponse);

        HeroResponseDTO actualResponse = heroService.createHero(heroRequest);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test(expected = DuplicatedHeroException.class)
    @DisplayName("Create Hero duplicated")
    public void createHero_shouldThrow_DuplicatedHeroException() {

        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Superman");

        Mockito.when(heroRepository.save(any())).thenThrow(DuplicatedHeroException.class);

        heroService.createHero(heroRequest);
    }

    @Test
    @DisplayName("Update Hero")
    public void updateHero_shouldReturn_HeroResponseDTO() {

        Long heroId = 1L;
        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Batman");

        Hero heroObtained = new Hero();
        heroObtained.setId(1L);
        heroObtained.setName("Superman");

        Hero modifiedHero = new Hero();
        modifiedHero.setId(1L);
        modifiedHero.setName("Batman");

        Mockito.when(heroRepository.findById(heroId)).thenReturn(Optional.of(heroObtained));
        Mockito.when(heroRepository.save(any())).thenReturn(modifiedHero);

        heroService.updateHero(heroId, heroRequest);

        Mockito.verify(heroRepository).findById(heroId);
        Mockito.verify(heroRepository).save(any());
    }

    @Test(expected = InvalidHeroIdException.class)
    @DisplayName("Update Hero with invalid heroID")
    public void updateHero_shouldThrow_InvalidHeroIdException() {

        Long heroId = null;
        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Batman");

        Mockito.when(this.heroRepository.findById(heroId)).thenThrow(InvalidDataAccessApiUsageException.class);

        heroService.updateHero(heroId, heroRequest);
    }

    @Test(expected = HeroNotFoundException.class)
    @DisplayName("Update Hero with not found hero")
    public void updateHero_shouldThrow_HeroNotFoundException() {

        Long heroId = 100L;
        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Batman");

        Mockito.when(this.heroRepository.findById(heroId)).thenReturn(Optional.empty());

        heroService.updateHero(heroId, heroRequest);
    }

    @Test
    @DisplayName("Delete Hero")
    public void deleteHero_shouldReturn_void() {

        Long heroId = 1L;

        Hero heroObtained = new Hero();
        heroObtained.setId(1L);
        heroObtained.setName("Superman");

        Mockito.when(heroRepository.findById(heroId)).thenReturn(Optional.of(heroObtained));
        Mockito.doNothing().when(heroRepository).deleteById(any());

        heroService.deleteHero(heroId);

        Mockito.verify(heroRepository).deleteById(any());
    }

    @Test(expected = InvalidHeroIdException.class)
    @DisplayName("Delete Hero with invalid heroID")
    public void deleteHero_shouldThrow_InvalidHeroIdException() {

        Long heroId = null;

        Mockito.when(this.heroRepository.findById(heroId)).thenThrow(InvalidDataAccessApiUsageException.class);

        heroService.deleteHero(heroId);
    }

    @Test(expected = HeroNotFoundException.class)
    @DisplayName("Delete Hero with not found hero")
    public void deleteHero_shouldThrow_HeroNotFoundException() {

        Long heroId = 100L;

        Mockito.when(heroRepository.findById(heroId)).thenReturn(Optional.empty());

        heroService.deleteHero(heroId);
    }

    @Test
    @DisplayName("Get Hero by heroID")
    public void getHeroById_shouldReturn_HeroResponseDTO() {

        Long heroId = 1L;
        HeroResponseDTO expectedResponse = new HeroResponseDTO();
        expectedResponse.setId(1L);
        expectedResponse.setName("Superman");

        Hero heroObtained = new Hero();
        heroObtained.setId(1L);
        heroObtained.setName("Superman");

        Mockito.when(heroRepository.findById(heroId)).thenReturn(Optional.of(heroObtained));
        Mockito.when(heroMapper.toDto(heroObtained)).thenReturn(expectedResponse);

        HeroResponseDTO actualResponse = heroService.getHeroById(heroId);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test(expected = InvalidHeroIdException.class)
    @DisplayName("Get Hero by heroID with invalid heroID")
    public void getHeroById_shouldThrow_InvalidHeroIdException() {

        Long heroId = 1L;

        Mockito.when(this.heroRepository.findById(heroId)).thenThrow(InvalidDataAccessApiUsageException.class);

        heroService.getHeroById(heroId);
    }

    @Test(expected = HeroNotFoundException.class)
    @DisplayName("Get Hero by heroID with not found hero")
    public void getHeroById_shouldThrow_HeroNotFoundException() {

        Long heroId = 100L;

        Mockito.when(heroRepository.findById(heroId)).thenReturn(Optional.empty());

        heroService.getHeroById(heroId);
    }

    @Test
    @DisplayName("Get Heroes by name")
    public void getHeroesByName_shouldReturn_ListOfHeroResponseDTO(){

        String name = "man";

        List<Hero> heroesObtainedList = new ArrayList<>();
        Hero hero = new Hero();
        hero.setId(1L);
        hero.setName("Superman");
        heroesObtainedList.add(hero);

        List<HeroResponseDTO> heroesExpectedList = new ArrayList<>();
        HeroResponseDTO heroDto = new HeroResponseDTO();
        heroDto.setId(1L);
        heroDto.setName("Superman");
        heroesExpectedList.add(heroDto);

        Mockito.when(heroRepository.findByNameContainingIgnoreCaseOrderByIdAsc(name)).thenReturn(heroesObtainedList);
        Mockito.when(heroMapper.toListDto(any())).thenReturn(heroesExpectedList);

        List<HeroResponseDTO> actualResponse = heroService.getHeroesByName(name);

        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.size());
    }

    @Test
    @DisplayName("Get All Heroes")
    public void getAllHeroes_shouldReturnListOfHeroes() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

        List<Hero> heroesObtainedList = new ArrayList<>();
        Hero hero1 = new Hero();
        hero1.setId(1L);
        hero1.setName("Superman");

        heroesObtainedList.add(hero1);

        Page<Hero> heroesObtainedPage = new PageImpl<>(heroesObtainedList);

        List<HeroResponseDTO> listHeroesExpected = new ArrayList<>();
        HeroResponseDTO heroDto = new HeroResponseDTO();
        heroDto.setId(1L);
        heroDto.setName("Superman");
        listHeroesExpected.add(heroDto);

        Page<HeroResponseDTO> heroesExpectedPage = new PageImpl<>(listHeroesExpected);

        Mockito.when(heroRepository.findAll(pageRequest)).thenReturn(heroesObtainedPage);
        Mockito.when(heroMapper.toPageDto(heroesObtainedPage)).thenReturn(heroesExpectedPage);

        Page<HeroResponseDTO> actualResponse = heroService.getAllHeroes(pageRequest);

        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.getTotalElements());
    }
}
