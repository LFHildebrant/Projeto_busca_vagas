package com.utfpr.Projeto_Sistemas.entities;

public record TokenResponse(
        String token,
        String expire_in
) {
}
