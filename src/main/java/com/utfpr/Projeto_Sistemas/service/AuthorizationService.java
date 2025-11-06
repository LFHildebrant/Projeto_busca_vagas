package com.utfpr.Projeto_Sistemas.service;

import com.utfpr.Projeto_Sistemas.repository.CompanyRepository;
import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    UserRepositoy userRepositoy;
    CompanyRepository companyRepository;

    @Autowired
    public AuthorizationService(CompanyRepository companyRepository, UserRepositoy userRepositoy) {
        this.companyRepository = companyRepository;
        this.userRepositoy = userRepositoy;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user =  userRepositoy.findByUsername(username);
        if (user != null) {
            return user;
        }
        UserDetails companyUser = companyRepository.findByUsername(username);
        if (companyUser != null) {
            return companyUser;
        }
        throw new UsernameNotFoundException("Usuário ou Empresa não encontrado: " + username);
    }
}
