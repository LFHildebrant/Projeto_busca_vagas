package com.utfpr.Projeto_Sistemas.repository;

import com.utfpr.Projeto_Sistemas.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findByUsername(String username);
    Company findByIdCompany(Long idUser);
    Boolean existsByIdCompany(Long idUser);
    Integer deleteUserByIdCompany(Integer idUser);
}
