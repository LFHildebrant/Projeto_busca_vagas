package com.utfpr.Projeto_Sistemas.controller;

import com.utfpr.Projeto_Sistemas.entities.Role;

public record CreateUserDto(String username, String password, String email, String name, String phone, String experience, String education) {


}
