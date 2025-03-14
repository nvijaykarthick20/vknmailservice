package com.vkn.vknmailservice.feign;

import com.vkn.vknevent_common.dto.SpendingSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "vknproducerevent-client", url = "${vknservice.eventproducer.endpoint}")
public interface VknEventFeignClient {

    @GetMapping(value = "/v1/spending-tracker/summary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<SpendingSummaryResponse> getSpendingTrackerSummary(@DateTimeFormat(pattern = "yyyy-MM") @RequestParam String monthYear);
}
