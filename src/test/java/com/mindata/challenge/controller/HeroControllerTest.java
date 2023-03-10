package com.mindata.challenge.controller;

import com.mindata.challenge.config.ControllerAdviceConfig;
import com.mindata.challenge.model.exception.DuplicatedHeroException;
import com.mindata.challenge.model.exception.HeroNotFoundException;
import com.mindata.challenge.service.HeroService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class HeroControllerTest {

    @InjectMocks
    private HeroControllerImpl heroController;

    @Mock
    private HeroService heroService;

    private MockMvc mockMvc;

    private final String URL_CREATE = "/api/v1/hero";
    private final String URL_UPDATE = "/api/v1/hero/{id}";
    private final String URL_DELETE = "/api/v1/hero/{id}";
    private final String URL_GET_BY_ID = "/api/v1/hero/{id}";
    private final String URL_GET_BY_NAME = "/api/v1/hero?name={name}";
    private final String URL_GET_ALL = "/api/v1/hero/all?page={page}&size={size}&sort={sort}";


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(heroController).setControllerAdvice(new ControllerAdviceConfig()).build();
    }

    @Test
    public void createHero_withValidName_shouldReturn200() throws Exception {

        this.mockMvc.perform(post(URL_CREATE).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Batman\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void createHero_withInvalidName_shouldReturn400() throws Exception {

        this.mockMvc.perform(post(URL_CREATE).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"444\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createHero_withInvalidBody_shouldReturn400() throws Exception {

        this.mockMvc.perform(post(URL_CREATE).contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createHero_withDuplicateHero_shouldReturn409() throws Exception {

        Mockito.doThrow(new DuplicatedHeroException("There is already a super hero with that name")).
                when(this.heroService).createHero(any());

        this.mockMvc.perform(post(URL_CREATE).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Batman\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateHero_withFoundHeroAndValidName_shouldReturn200() throws Exception {

        this.mockMvc.perform(put(URL_UPDATE,1).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"newName\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateHero_withFoundHeroAndInvalidName_shouldReturn400() throws Exception {

        this.mockMvc.perform(put(URL_UPDATE,1).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"444\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateHero_withFoundHeroAndInvalidBody_shouldReturn400() throws Exception {

        this.mockMvc.perform(put(URL_UPDATE,1).contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateHero_withNotFoundHero_shouldReturn404() throws Exception {

        Mockito.doThrow(new HeroNotFoundException("Hero not found")).when(this.heroService).updateHero(any(), any());

        this.mockMvc.perform(put(URL_UPDATE,100).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Hulk\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteHero_withFoundHero_shouldReturn200() throws Exception {

        this.mockMvc.perform(delete(URL_DELETE,1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteHero_withNotFoundHero_shouldReturn404() throws Exception {

        Mockito.doThrow(new HeroNotFoundException("Hero not found")).when(this.heroService).deleteHero(any());

        this.mockMvc.perform(delete(URL_DELETE,1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getHeroById_withFoundHero_shouldReturn200() throws Exception {

        this.mockMvc.perform(get(URL_GET_BY_ID,1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getHeroById_withNotFoundHero_shouldReturn404() throws Exception {

        Mockito.doThrow(new HeroNotFoundException("Hero not found")).when(this.heroService).getHeroById(any());

        this.mockMvc.perform(get(URL_GET_BY_ID,1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getHeroesByName_withValidName_shouldReturn200() throws Exception {

        this.mockMvc.perform(get(URL_GET_BY_NAME,"man").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllHeroes_withValidPagination_shouldReturn200() throws Exception {

        this.mockMvc.perform(get(URL_GET_ALL, 0, 10, "asc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllHeroes_withRandomSort_shouldReturn200() throws Exception {

        this.mockMvc.perform(get(URL_GET_ALL, 0, 10, "random").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
