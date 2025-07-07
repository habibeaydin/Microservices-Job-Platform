package com.sau.jobservice.repository;

import com.sau.jobservice.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByEmployerId(Long employerId);
}

