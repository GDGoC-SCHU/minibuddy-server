package com.minibuddy.health;

import com.minibuddy.global.response.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping("/")
    public String health() {
        return "hello world";
    }

    @GetMapping("/ok")
    public SuccessResponse<?> ok() {
        return SuccessResponse.ok(new HealthDto("health", "health@health.com"));
    }
}
