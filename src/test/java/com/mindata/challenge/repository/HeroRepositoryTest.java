package com.mindata.challenge.repository;

import com.mindata.challenge.model.entity.Hero;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HeroRepositoryTest {

    @Mock
    private HeroRepository heroRepository;

    @Test
    public void test(){

        String name = "man";

        List<Hero> expectedHeroes = new ArrayList<>();
        Hero hero = new Hero();
        hero.setId(1L);
        hero.setName("Batman");
        expectedHeroes.add(hero);

        Mockito.when(heroRepository.findByNameContainingIgnoreCaseOrderByIdAsc(name)).thenReturn(expectedHeroes);

        List<Hero> actualHeroes = heroRepository.findByNameContainingIgnoreCaseOrderByIdAsc(name);

        assertEquals(expectedHeroes, actualHeroes);
    }
}
