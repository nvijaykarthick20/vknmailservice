package com.vkn.vknmailservice.service.impl;

import com.vkn.vknevent_common.dto.SpendingSummaryResponse;
import com.vkn.vknevent_common.utils.VknEventCommonUtils;
import com.vkn.vknmailservice.constant.VknMailConstant;
import com.vkn.vknmailservice.feign.VknEventFeignClient;
import com.vkn.vknmailservice.feign.VknMailFeignClient;
import com.vkn.vknmailservice.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vkn.vknmailservice.constant.VknMailConstant.*;


@Service
@Slf4j
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final VknMailConstant vknMailConstant;

    private final VknEventFeignClient vknServiceFeignClient;

    private final VknMailFeignClient vknMailFeignClient;

    private void sendEmail(String from, String[] to, String subject, String body) {
        log.info("From: {}, To: {}, Subject: {}", from, to, subject);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setText(body, true);
            mailSender.send(message);
            log.info("Mail sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void spendingTrackerSummaryMail(String monthYear) {
        List<SpendingSummaryResponse> spendingTrackerSummary = vknServiceFeignClient.getSpendingTrackerSummary(monthYear);
        log.info("Spending Tracker Summary : {}", spendingTrackerSummary);
        String spendingTrackerSummarySubject = MessageFormat.format(VknMailConstant.SPENDING_TRACKER_SUMMARY_SUBJECT, monthYear);
        String body = constructSpendingTrackerSummaryBody(monthYear, spendingTrackerSummary);
        final String[] spendingTrackerSummaryTo = vknMailFeignClient.getEmailsByRole("sendmail");
        sendEmail(vknMailConstant.mailFrom, spendingTrackerSummaryTo,
                spendingTrackerSummarySubject, body);
    }

    private String constructSpendingTrackerSummaryBody(String monthYear, List<SpendingSummaryResponse> spendingTrackerSummary){
        String bodyMain = SPENDING_TRACKER_SUMMARY_BODY_MAIN;
        if(spendingTrackerSummary != null && !spendingTrackerSummary.isEmpty()){
            String users = spendingTrackerSummary.stream().map(SpendingSummaryResponse::getTransactionBy).collect(Collectors.joining("/"));
            bodyMain = StringUtils.replace(bodyMain, "@@alluser@@", users);
            bodyMain = StringUtils.replace(bodyMain, "@@month@@", monthYear);
            List<String> transactionsHtml = new ArrayList<>();
            for(SpendingSummaryResponse resp: spendingTrackerSummary){
                String transactionHtml = SPENDING_TRACKER_SUMMARY_BODY_TRANSACTION_HTML;
                transactionHtml = StringUtils.replace(transactionHtml, "@@user@@", resp.getTransactionBy());
                List<String> typesHtml = new ArrayList<>();
                for(SpendingSummaryResponse.TypeSummaryDTO summaryDTO : resp.getTypeSummaries()){
                    String typeHtml = SPENDING_TRACKER_SUMMARY_BODY_TYPE_HTML;
                    typeHtml = StringUtils.replace(typeHtml, "@@type@@", summaryDTO.getType());
                    typeHtml = StringUtils.replace(typeHtml, "@@amount@@", VknEventCommonUtils.appendDollar(summaryDTO.getTotalAmount()));
                    List<String> categoriesHtml = new ArrayList<>();
                    for(SpendingSummaryResponse.TypeSummaryDTO.CategorySummaryDTO categorySummaryDTO: summaryDTO.getCategorySummaries()){
                        String categoryHtml = SPENDING_TRACKER_SUMMARY_BODY_CATEGORY_HTML;
                        categoryHtml = StringUtils.replace(categoryHtml, "@@category@@", categorySummaryDTO.getCategory());
                        categoryHtml = StringUtils.replace(categoryHtml, "@@amount@@", VknEventCommonUtils.appendDollar(categorySummaryDTO.getAmount()));
                        categoriesHtml.add(categoryHtml);
                    }
                    typeHtml = StringUtils.replace(typeHtml, "@@categories@@", StringUtils.join(categoriesHtml, " "));
                    typesHtml.add(typeHtml);
                }

                transactionHtml = StringUtils.replace(transactionHtml, "@@types_summary@@", StringUtils.join(typesHtml, " "));
                transactionsHtml.add(transactionHtml);
                String balanceHtml = SPENDING_TRACKER_SUMMARY_BALANCE.replace("@@amount@@", VknEventCommonUtils.appendDollar(resp.getTotalBalance()));
                if(resp.getTotalBalance() <= 0) {
                    transactionsHtml.add(balanceHtml.replace("@@color@@", "red"));
                }else{
                    transactionsHtml.add(balanceHtml.replace("@@color@@", "green"));
                }
            }
            bodyMain = StringUtils.replace(bodyMain, "@@transaction_summary@@", StringUtils.join(transactionsHtml, " "));
        }else{
            bodyMain="No Spending Tracker Summary";
        }
        return bodyMain;
    }
}
