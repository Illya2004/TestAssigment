package com.kolis1on.testassigment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;

    @JsonProperty(required = false)
    private String address;

    @JsonProperty(required = false)
    private String phoneNumber;
}
