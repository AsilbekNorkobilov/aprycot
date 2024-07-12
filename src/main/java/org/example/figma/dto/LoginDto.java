package org.example.figma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link org.example.figma.entity.User}
 */
public record LoginDto(@NotNull @NotEmpty String email, @NotNull @NotBlank String password) implements Serializable {
}