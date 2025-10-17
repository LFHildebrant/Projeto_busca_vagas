package com.utfpr.Projeto_Sistemas.repository;

import com.utfpr.Projeto_Sistemas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoy extends JpaRepository<User, Integer> {

    UserDetails findByUsername(String username);
    UserDetails findByIdUser(Long idUser);
    Boolean existsByIdUser(Long idUser);
    Integer deleteUserByIdUser(Integer idUser);
}
