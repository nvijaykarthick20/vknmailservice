package com.vkn.vknmailservice.feign;

import com.vkn.vknevent_common.dto.SpendingSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "vknmailevent-client", url = "${vknservice.keycloak.endpoint}")
public interface VknMailFeignClient {

    @GetMapping(value = "/v1/user/email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String[] getEmailsByRole(@RequestParam String role);
}