package com.pet.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private final String jwttoken;
    private final String username;
    private final String email;
}
