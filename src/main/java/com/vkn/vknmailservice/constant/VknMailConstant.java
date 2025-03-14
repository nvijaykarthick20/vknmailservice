package com.vkn.vknmailservice.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VknMailConstant {

    @Value("${mail.from}")
    public String mailFrom;

    public static final String SPENDING_TRACKER_SUMMARY_SUBJECT = "Your Spending Tracker Summary for the Month: {0}";

    public static final String SPENDING_TRACKER_SUMMARY_BALANCE = "<p><span class=\"highlight\">Balance:</span> <span style=\"color: @@color@@\">@@amount@@</span></p>\n";

    public static final String  SPENDING_TRACKER_SUMMARY_BODY_TYPE_HTML = "    <p><span class=\"highlight\">@@type@@:</span> @@amount@@</p>\n"+
            "    <ul>\n" +
            "@@categories@@" +
            "    </ul>\n";

    public static final String SPENDING_TRACKER_SUMMARY_BODY_CATEGORY_HTML = "  <li><span class=\"highlight\">@@category@@:</span> @@amount@@</li>\n";

    public static String SPENDING_TRACKER_SUMMARY_BODY_TRANSACTION_HTML ="<h3>\uD83D\uDC64 @@user@@</h3>\n" +
            "@@types_summary@@";

    public static String SPENDING_TRACKER_SUMMARY_BODY_MAIN = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Page Title</title>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Spending Tracker Report</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            margin: 20px;\n" +
            "            padding: 20px;\n" +
            "            background-color: #f4f4f4;\n" +
            "        }\n" +
            "        .container {\n" +
            "            background-color: white;\n" +
            "            padding: 20px;\n" +
            "            border-radius: 8px;\n" +
            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
            "            max-width: 600px;\n" +
            "            margin: auto;\n" +
            "        }\n" +
            "        h2 {\n" +
            "            color: #2E86C1;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        ul {\n" +
            "            list-style-type: none;\n" +
            "            padding: 0;\n" +
            "        }\n" +
            "        ul li {\n" +
            "            padding: 5px 0;\n" +
            "        }\n" +
            "        .footer {\n" +
            "            margin-top: 20px;\n" +
            "            text-align: center;\n" +
            "            font-size: 14px;\n" +
            "            color: #666;\n" +
            "        }\n" +
            "        .highlight {\n" +
            "            font-weight: bold;\n" +
            "        }\n" +
            "        a {\n" +
            "            color: #007BFF;\n" +
            "            text-decoration: none;\n" +
            "        }\n" +
            "        .padding-text {\n" +
            "        \tpadding-right: 50px;\n" +
            "        }\n" +
            "        ul {\n" +
            "        \tlist-style-type: disc; /* Options: disc, circle, square, none */\n" +
            "            padding: 0 25px;\n" +
            "    \t}\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<div class=\"container\">\n" +
            "    <h2>\uD83D\uDCE9 Monthly Spending Report – @@month@@</h2>\n" +
            "\n" +
            "    <p>Dear @@alluser@@,</p>\n" +
            "    <p>Here is the <span class=\"highlight\">spending summary</span> in <span class=\"highlight\">@@month@@</span>:</p>\n" +
            "\n" +
            "@@transaction_summary@@"+
            "\n" +
            "    <p class=\"footer\">\n" +
            "        Best,<br>\n" +
            "        <b>VKN Finance Team</b>\n" +
            "    </p>\n" +
            "</div>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

}
