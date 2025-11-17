package com.utfpr.Projeto_Sistemas.utilities;

public record TokenResponse(
        String token,
        Integer expires_in
) {
}
