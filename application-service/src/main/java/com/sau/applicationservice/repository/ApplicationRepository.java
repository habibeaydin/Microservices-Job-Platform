package com.sau.applicationservice.repository;

import com.sau.applicationservice.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
    List<Application> findAllByCandidateId(Long candidateId);
    List<Application> findAllByJobIdIn(List<Long> jobIds);
    List<Application> findAllByJobId(Long jobId);
}

