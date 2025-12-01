package com.utfpr.Projeto_Sistemas.service;

import com.utfpr.Projeto_Sistemas.dto.FeedbackDto;
import com.utfpr.Projeto_Sistemas.dto.company.CompanyDto;
import com.utfpr.Projeto_Sistemas.dto.company.UpdateCompanyDto;
import com.utfpr.Projeto_Sistemas.dto.job.*;
import com.utfpr.Projeto_Sistemas.dto.jobsearch.FilterDto;
import com.utfpr.Projeto_Sistemas.dto.jobsearch.JobSearchDto;
import com.utfpr.Projeto_Sistemas.dto.user.UserJobDto;
import com.utfpr.Projeto_Sistemas.dto.user.UsersListByApplication;
import com.utfpr.Projeto_Sistemas.entities.*;
import com.utfpr.Projeto_Sistemas.repository.*;
import com.utfpr.Projeto_Sistemas.utilities.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserRepositoy userRepositoy;

    public boolean createJob(CreateJobDto createJobDto, Long idCompany) {
        Company company = companyRepository.findByIdCompany(idCompany);
        var Entity = new Job(
                company,
                createJobDto.title(),
                AreaJob.from(createJobDto.area()),
                createJobDto.description(),
                createJobDto.state(),
                createJobDto.city(),
                verifyNullableBigDecimal(createJobDto.salary()),
                Instant.now(),
                null
        );
        Job savedJob = jobRepository.save(Entity);
        return jobRepository.existsById(savedJob.getIdJob());
    }
    public JobDto getDataJob(long idJob){
        Job job = (Job) jobRepository.findByIdJob(idJob);
        if (job == null){return null;}
        return new JobDto(job.getIdJob(), job.getTitle(), job.getArea().getAreaJob(), job.getDescription(), job.getCompany().getName(), job.getCity(), job.getState(), job.getCompany().getEmail(), job.getSalary());
    }
    public ResponseEntity<?>  updateJob(UpdateJobDto updateJobDto, long idCompany, long idJob) {
        Job existingJob = (Job) jobRepository.findByIdJob(idJob);
        if (existingJob == null){
            return ResponseEntity.status(404).body(new ApiResponse("Job not found"));}

        if (existingJob.getCompany().getIdCompany() == idCompany){
            existingJob.setTitle(updateJobDto.title());
            existingJob.setArea(AreaJob.from(updateJobDto.area()));
            existingJob.setDescription(updateJobDto.description());
            existingJob.setState(updateJobDto.state());
            existingJob.setCity(updateJobDto.city());
            existingJob.setSalary(verifyNullableBigDecimal(updateJobDto.salary()));

            Job savedJob = jobRepository.save(existingJob);
            if (jobRepository.existsById(savedJob.getIdJob())){
                return ResponseEntity.status(200).body(new ApiResponse("Job updated"));
            }
        }
        return ResponseEntity.status(403).body(new ApiResponse("Forbidden"));
        
    }
    @Transactional
    public ResponseEntity<?> deleteJob(long idCompany, long idJob){
        Job existingJob = (Job) jobRepository.findByIdJob(idJob);
        if (existingJob == null){
            return ResponseEntity.status(404).body(new ApiResponse("Job not found"));}

        if (existingJob.getCompany().getIdCompany() == idCompany) {
            Integer result = jobRepository.deleteJobByIdJob(Math.toIntExact(idJob));
            companyRepository.flush(); // sync db
            if (result > 0){
                return ResponseEntity.status(200).body(new ApiResponse("Job deleted"));
            }
        }
        return ResponseEntity.status(403).body(new ApiResponse("Forbidden"));
    }
    public ResponseEntity<?> applyForJob(ApplicationDto applicationDto, Long idJob, Long idUser){
        Job existingJob = (Job) jobRepository.findByIdJob(idJob);
        if (existingJob == null){
            return ResponseEntity.status(404).body(new ApiResponse("Job not found"));}
        User user = userRepositoy.findByIdUser(idUser);
        Job job = jobRepository.findByIdJob(idJob);
        var Entity = new Application(
                job,
                user,
                applicationDto.name(),
                verifyNullable(applicationDto.email()),
                verifyNullable(applicationDto.phone()),
                applicationDto.experience(),
                applicationDto.education(),
                Instant.now()
        );
        Application savedApp= applicationRepository.save(Entity);
        if(applicationRepository.existsByIdApplication(savedApp.getIdApplication())){
            return ResponseEntity.status(200).body(new ApiResponse("Applied successfully"));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse("error while saving"));
        }
    }
    public ResponseEntity<?> searchUsersByApplication(long idCompany, long idJob){
        Job job = (Job) jobRepository.findByIdJob(idJob);
        if(job == null){return ResponseEntity.status(404).body(new ApiResponse("Job not found"));}
        if (job.getCompany().getIdCompany() != idCompany){
            return ResponseEntity.status(403).body(new ApiResponse("Forbidden"));
        }
        List<Application> listApp = applicationRepository.findByIdJobWithUser(idJob);
        List<UserJobDto> userJobDtos = listApp.stream()
                .map(app -> new UserJobDto(
                        app.getUser().getIdUser(),
                        app.getUser().getName(),
                        app.getUser().getEmail(),
                        app.getUser().getPhone(),
                        app.getUser().getExperience(),
                        app.getUser().getEducation()
                )).toList();
        return ResponseEntity.status(200).body(new UsersListByApplication(userJobDtos));
    }
    public ResponseEntity<?> sendFeedback(FeedbackDto feedbackDto, long companyId, long jobId){
        Job job = (Job) jobRepository.findByIdJob(jobId);
        Company company = (Company) companyRepository.findByIdCompany(companyId);
        User user = (User) userRepositoy.findByIdUser(feedbackDto.user_id());
        if (job == null){return ResponseEntity.status(404).body(new ApiResponse("Job not found"));}
        if (company == null){return ResponseEntity.status(404).body(new ApiResponse("company not found"));}
        if (user == null){return ResponseEntity.status(404).body(new ApiResponse("user not found"));}
        if (company.getRole() != Role.COMPANY){return ResponseEntity.status(403).body(new ApiResponse("Forbidden"));}
        Application application = (Application) applicationRepository.findApplicationFeedback(jobId, feedbackDto.user_id());
        if (application == null){return ResponseEntity.status(404).body(new ApiResponse("application not found"));}
        application.setFeedback(feedbackDto.message());
        Application appSaved = applicationRepository.save(application);
        if (applicationRepository.existsByIdApplication(appSaved.getIdApplication())) {
            return ResponseEntity.status(200).body(new ApiResponse("Feedback sent"));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse("error while saving feedback"));
        }
    }
    public ResponseEntity<?>getApplicationsByUserId(long idUser){
        List<Application> listApp = applicationRepository.findAllByUser_idUser(idUser);
        List<JobApplUser> listAppDto = listApp.stream()
                .map(app -> new JobApplUser(
                        app.getJob().getIdJob(),
                        app.getJob().getTitle(),
                        app.getJob().getArea().getAreaJob(),
                        app.getJob().getDescription(),
                        app.getJob().getCompany().getName(),
                        app.getJob().getCity(),
                        app.getJob().getState(),
                        app.getJob().getCompany().getEmail(),
                        app.getJob().getSalary(),
                        app.getFeedback()
                )).toList();
        return ResponseEntity.status(200).body(new AllApplJobsByUser(listAppDto));
    }
    public List<Job> searchJobsWithSpecs(FilterDto filterDto){
        List<Specification<Job>> specifications = new ArrayList<>();
        if (filterDto.title() != null && !filterDto.title().isEmpty()){
            specifications.add(JobSpecifications.likeTitle(filterDto.title()));
        }
        if (filterDto.area() != null && !filterDto.area().isEmpty()){
            specifications.add(JobSpecifications.likeArea(filterDto.area()));
        }
        if (filterDto.company() != null && !filterDto.company().isEmpty()){
            specifications.add(JobSpecifications.likeCompany(filterDto.company()));
        }
        if (filterDto.state() != null && !filterDto.state().isEmpty()){
            specifications.add(JobSpecifications.likeState(filterDto.state()));
        }
        if (filterDto.city() != null && !filterDto.city().isEmpty()){
            specifications.add(JobSpecifications.likeCity(filterDto.city()));
        }
        if (filterDto.salary_range() != null && filterDto.salary_range().max() != null){
            specifications.add(JobSpecifications.salaryMax(filterDto.salary_range().max()));
        }
        if (filterDto.salary_range() != null && filterDto.salary_range().min() != null){
            specifications.add(JobSpecifications.salaryMin(filterDto.salary_range().min()));
        }
        Specification<Job> finalSpec = Specification.allOf(specifications);
        return jobRepository.findAll(finalSpec);
    }
    public List<Job> searchJobsByCompanyWithSpecs(FilterDto filterDto, long idCompany){
        Company company = companyRepository.findByIdCompany(idCompany);
        List<Specification<Job>> specifications = new ArrayList<>();
        if (filterDto.title() != null && !filterDto.title().isEmpty()){
            specifications.add(JobSpecifications.likeTitle(filterDto.title()));
        }
        if (filterDto.area() != null && !filterDto.area().isEmpty()){
            specifications.add(JobSpecifications.likeArea(filterDto.area()));
        }
        if (filterDto.company() == null || !filterDto.company().isEmpty()){
            specifications.add(JobSpecifications.likeCompany(company.getName()));
        }
        if (filterDto.state() != null && !filterDto.state().isEmpty()){
            specifications.add(JobSpecifications.likeState(filterDto.state()));
        }
        if (filterDto.city() != null && !filterDto.city().isEmpty()){
            specifications.add(JobSpecifications.likeCity(filterDto.city()));
        }
        if (filterDto.salary_range() != null && filterDto.salary_range().max() != null){
            specifications.add(JobSpecifications.salaryMax(filterDto.salary_range().max()));
        }
        if (filterDto.salary_range() != null && filterDto.salary_range().min() != null){
            specifications.add(JobSpecifications.salaryMin(filterDto.salary_range().min()));
        }
        Specification<Job> finalSpec = Specification.allOf(specifications);
        return jobRepository.findAll(finalSpec);
    }

    public BigDecimal verifyNullableBigDecimal(BigDecimal value){
        if (value == null){
            return null;
        }
        return value;
    }
    public String verifyNullable(String value){
        if (value == null || value.trim().isEmpty()){
            return null;
        }
        return value;
    }
}
