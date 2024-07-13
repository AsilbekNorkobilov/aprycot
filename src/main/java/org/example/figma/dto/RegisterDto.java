package org.example.figma.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link org.example.figma.entity.User}
 */
public record RegisterDto(String firstName, String lastName, String phone, @NotNull @NotEmpty String email, @NotNull @NotEmpty String password) implements Serializable {
  }