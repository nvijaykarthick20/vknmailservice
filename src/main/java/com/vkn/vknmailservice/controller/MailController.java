package com.vkn.vknmailservice.controller;

import com.vkn.vknmailservice.service.MailService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/send-mail")
public class MailController {

    private final MailService mailService;

    @GetMapping("/spending-tracker-summary")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void spendingTrackerSummary(@DateTimeFormat(pattern = "yyyy-MM") @RequestParam(required = false) String monthYear){
        if(StringUtils.isBlank(monthYear)){
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            monthYear = currentDate.format(formatter);
        }
        mailService.spendingTrackerSummaryMail(monthYear);

    }
}
