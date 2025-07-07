package com.sau.authservice.feign;

import com.sau.authservice.feign.dto.UserCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-profile-service", path = "/api/users")
public interface UserClient {

    @PostMapping("/create")
    void createUser(@RequestBody UserCreateRequest request);
}
