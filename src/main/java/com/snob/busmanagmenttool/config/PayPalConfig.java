package com.snob.busmanagmenttool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
//@TODO НЕ ПРАЦюЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄ

@Configuration
public class PayPalConfig {

    @Value("${paypal.mode}")
    private String mode; // Should be "sandbox" or "live" based on your environment

    @Value("${paypal.clientId}")
    private String clientId; // Your PayPal client ID

    @Value("${paypal.secret}")
    private String clientSecret; // Your PayPal secret

    public String getMode() {
        return mode;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}