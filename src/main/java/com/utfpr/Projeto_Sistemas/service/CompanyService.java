package com.utfpr.Projeto_Sistemas.service;

import com.utfpr.Projeto_Sistemas.dto.company.CreateCompanyDto;
import com.utfpr.Projeto_Sistemas.dto.company.CompanyDto;
import com.utfpr.Projeto_Sistemas.dto.company.UpdateCompanyDto;
import com.utfpr.Projeto_Sistemas.entities.*;
import com.utfpr.Projeto_Sistemas.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public boolean existsCompanyById(Long idCompany){
        return companyRepository.existsByIdCompany(idCompany);
    }


    public boolean createUser(CreateCompanyDto createCompanyDto) {
        //userDto -> user
        var Entity = new Company(
                createCompanyDto.name().toUpperCase(),
                createCompanyDto.username(),
                createCompanyDto.password(),
                createCompanyDto.email(),
                createCompanyDto.phone(),
                createCompanyDto.business(),
                createCompanyDto.city(),
                createCompanyDto.street(),
                createCompanyDto.state(),
                createCompanyDto.number(),
                Instant.now(),
                null, // date update
                Role.COMPANY
        );

        Company savedCompany = companyRepository.save(Entity);
        //check if company has been created
        return companyRepository.existsById(savedCompany.getIdCompany());
    }
    public CompanyDto getDataCompany(Long idCompany){
        Company company = (Company) companyRepository.findByIdCompany(idCompany);
        return new CompanyDto(company.getName(), company.getUsername(), company.getEmail(), company.getPhone(), company.getBusiness(), company.getAddress().getStreet(), company.getAddress().getNumber(), company.getAddress().getCity(), company.getAddress().getState());
    }
    public boolean updateCompany(UpdateCompanyDto updateCompanyDto, Long idCompany) {
        Company existingCompany = (Company) companyRepository.findByIdCompany(idCompany);

        existingCompany.setName(updateCompanyDto.name().toUpperCase());   //get the user from db, then put the new data on the user and save
        if (!updateCompanyDto.password().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(updateCompanyDto.password());
            existingCompany.setPassword(encryptedPassword);
        }
        existingCompany.setEmail(updateCompanyDto.email());
        existingCompany.setPhone(updateCompanyDto.phone());
        existingCompany.setBusiness(updateCompanyDto.business());
        Address existingAddress = (Address) existingCompany.getAddress();
        existingAddress.setStreet(updateCompanyDto.street());
        existingAddress.setNumber(updateCompanyDto.number());
        existingAddress.setCity(updateCompanyDto.city());
        existingAddress.setState(updateCompanyDto.state());
        existingCompany.setAddress(existingAddress);

        Company savedCompany = companyRepository.save(existingCompany);
        //check if user has been created
        return companyRepository.existsById(savedCompany.getIdCompany());
    }

    @Transactional
    public Integer deleteUser(Long idCompany){
        Integer result = companyRepository.deleteUserByIdCompany(Math.toIntExact(idCompany));
        companyRepository.flush(); // sync db
        return result;
    }
}
