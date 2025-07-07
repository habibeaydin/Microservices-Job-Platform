package com.sau.applicationservice.feign;

import com.sau.applicationservice.feign.dto.JobResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "job-service")
public interface JobClient {

    @GetMapping("/api/jobs/{id}")
    JobResponse getJobById(@PathVariable Long id);

    @GetMapping("/api/jobs/employer")
    List<JobResponse> getJobsByEmployer(@RequestHeader("X-Auth-User-Id") String employerId);
}

