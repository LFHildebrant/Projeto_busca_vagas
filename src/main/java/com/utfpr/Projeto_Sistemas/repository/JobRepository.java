package com.utfpr.Projeto_Sistemas.repository;

import com.utfpr.Projeto_Sistemas.entities.Company;
import com.utfpr.Projeto_Sistemas.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> , JpaSpecificationExecutor<Job> {

    Job findByIdJob(Long idUser);
    Boolean existsByIdJob(Long idUser);
    Integer deleteJobByIdJob(Integer idUser);
}
