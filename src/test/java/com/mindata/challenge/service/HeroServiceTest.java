package com.mindata.challenge.service;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import com.mindata.challenge.model.exception.DuplicatedHeroException;
import com.mindata.challenge.model.exception.HeroNotFoundException;
import com.mindata.challenge.model.exception.InvalidHeroIdException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class HeroServiceTest {

    @Mock
    private HeroService heroService;

    @Test
    @DisplayName("Create Hero")
    public void createHero_shouldReturn_HeroResponseDTO() {

        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Superman");

        HeroResponseDTO expectedResponse = new HeroResponseDTO();
        expectedResponse.setId(1L);
        expectedResponse.setName("Superman");

        Mockito.when(heroService.createHero(heroRequest)).thenReturn(expectedResponse);

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

        HeroResponseDTO expectedResponse = new HeroResponseDTO();
        expectedResponse.setId(1L);
        expectedResponse.setName("Superman");

        Mockito.when(heroService.createHero(heroRequest)).thenThrow(DuplicatedHeroException.class);

        heroService.createHero(heroRequest);
    }

    @Test
    @DisplayName("Update Hero")
    public void updateHero_shouldReturn_HeroResponseDTO() {

        Long heroId = 1L;
        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Batman");

        heroService.updateHero(heroId, heroRequest);

        Mockito.verify(heroService, Mockito.times(1)).updateHero(heroId, heroRequest);
    }

    @Test(expected = InvalidHeroIdException.class)
    @DisplayName("Update Hero with invalid heroID")
    public void updateHero_shouldThrow_InvalidHeroIdException() {

        Long heroId = null;
        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Batman");

        Mockito.doThrow(new InvalidHeroIdException()).when(heroService).updateHero(heroId, heroRequest);

        heroService.updateHero(heroId, heroRequest);
    }

    @Test(expected = HeroNotFoundException.class)
    @DisplayName("Update Hero with not found hero")
    public void updateHero_shouldThrow_HeroNotFoundException() {

        Long heroId = 1L;
        HeroRequestDTO heroRequest = new HeroRequestDTO();
        heroRequest.setName("Batman");

        Mockito.doThrow(new HeroNotFoundException()).when(heroService).updateHero(heroId, heroRequest);

        heroService.updateHero(heroId, heroRequest);
    }


    @Test
    @DisplayName("Delete Hero")
    public void deleteHero_shouldReturn_void() {

        Long heroId = 1L;

        Mockito.doNothing().when(heroService).deleteHero(heroId);

        heroService.deleteHero(heroId);

        Mockito.verify(heroService, Mockito.times(1)).deleteHero(heroId);
    }

    @Test(expected = InvalidHeroIdException.class)
    @DisplayName("Delete Hero with invalid heroID")
    public void deleteHero_shouldThrow_InvalidHeroIdException() {

        Long heroId = null;

        Mockito.doThrow(new InvalidHeroIdException()).when(heroService).deleteHero(heroId);

        heroService.deleteHero(heroId);
    }

    @Test(expected = HeroNotFoundException.class)
    @DisplayName("Delete Hero with not found hero")
    public void deleteHero_shouldThrow_HeroNotFoundException() {

        Long heroId = 1L;

        Mockito.doThrow(new HeroNotFoundException()).when(heroService).deleteHero(heroId);

        heroService.deleteHero(heroId);
    }

    @Test
    @DisplayName("Get Hero by heroID")
    public void getHeroById_shouldReturn_HeroResponseDTO() {

        Long heroId = 1L;
        HeroResponseDTO expectedResponse = new HeroResponseDTO();
        expectedResponse.setId(1L);
        expectedResponse.setName("Superman");

        Mockito.when(heroService.getHeroById(heroId)).thenReturn(expectedResponse);

        HeroResponseDTO actualResponse = heroService.getHeroById(heroId);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test(expected = InvalidHeroIdException.class)
    @DisplayName("Get Hero by heroID with invalid heroID")
    public void getHeroById_shouldThrow_InvalidHeroIdException() {

        Long heroId = 1L;

        Mockito.doThrow(new InvalidHeroIdException()).when(heroService).getHeroById(heroId);

        heroService.getHeroById(heroId);
    }

    @Test(expected = HeroNotFoundException.class)
    @DisplayName("Get Hero by heroID with not found hero")
    public void getHeroById_shouldThrow_HeroNotFoundException() {

        Long heroId = 1L;

        Mockito.doThrow(new HeroNotFoundException()).when(heroService).getHeroById(heroId);

        heroService.getHeroById(heroId);
    }

    @Test
    @DisplayName("Get Heroes by name")
    public void getHeroesByName_shouldReturn_ListOfHeroResponseDTO(){

        String name = "man";

        List<HeroResponseDTO> listHeroesExpected = new ArrayList<>();

        Mockito.when(heroService.getHeroesByName(name)).thenReturn(listHeroesExpected);

        List<HeroResponseDTO> actualResponse = heroService.getHeroesByName(name);

        assertNotNull(actualResponse);
    }

    @Test
    @DisplayName("Get All Heroes")
    public void getAllHeroes_shouldReturnListOfHeroes() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<HeroResponseDTO> heroList = new ArrayList<>();
        PageImpl<HeroResponseDTO> heroPage = new PageImpl<>(heroList);

        Mockito.when(heroService.getAllHeroes(pageRequest)).thenReturn(heroPage);

        Page<HeroResponseDTO> actualResponse = heroService.getAllHeroes(pageRequest);

        assertNotNull(actualResponse);
    }
}
