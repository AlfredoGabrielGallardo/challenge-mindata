package com.mindata.challenge.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroRequestDTO {

    @Pattern(regexp = "^[A-Za-z]*$")
    @NotNull
    @NotBlank
    private String name;
}
