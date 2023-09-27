package com.snob.busmanagmenttool.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.util.HashMap;
import java.util.Map;
// @TODO НЕ ПРАЦюЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄ
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {
  @Value("${paypal.clientId}")
  private String clientId; // Your PayPal client ID

  @Value("${paypal.secret}")
  private String clientSecret; // Your PayPal secret
  @Bean
  public PayPalHttpClient payPalHttpClient() {
    PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
    return new PayPalHttpClient(environment);
  }
}
