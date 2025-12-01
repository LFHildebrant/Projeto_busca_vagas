package com.utfpr.Projeto_Sistemas.repository;

import com.utfpr.Projeto_Sistemas.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    Application findByIdApplication(int idApplication);
    //Application findByIdUser(int idUser);
    Boolean existsByIdApplication(int idApplication);
    @Query("SELECT a FROM Application a JOIN FETCH a.user WHERE a.job.idJob = :jobId")
    List<Application> findByIdJobWithUser(@Param("jobId") long idJob);

    @Query("SELECT a FROM Application a WHERE a.job.idJob = :jobId AND a.user.idUser = :userId")
    Application findApplicationFeedback(@Param("jobId") long idJob, @Param("userId") long userId);

    List<Application> findAllByUser_idUser(long userId);
}
