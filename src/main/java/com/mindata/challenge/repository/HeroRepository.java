package com.mindata.challenge.repository;

import com.mindata.challenge.model.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    List<Hero> findByNameContainingIgnoreCaseOrderByIdAsc(String name);
}
