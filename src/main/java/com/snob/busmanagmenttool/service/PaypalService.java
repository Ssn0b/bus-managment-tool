package com.snob.busmanagmenttool.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PaypalService {
  @Value("${paypal.clientId}")
  private String clientId;
  @Value("${paypal.secret}")
  private String clientSecret;
  public Map<String, Object> createPayment(String sum){
    Map<String, Object> response = new HashMap<String, Object>();
    Amount amount = new Amount();
    amount.setCurrency("USD");
    amount.setTotal(sum);
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    List<Transaction> transactions = new ArrayList<Transaction>();
    transactions.add(transaction);

    Payer payer = new Payer();
    payer.setPaymentMethod("paypal");

    Payment payment = new Payment();
    payment.setIntent("sale");
    payment.setPayer(payer);
    payment.setTransactions(transactions);

    RedirectUrls redirectUrls = new RedirectUrls();
    redirectUrls.setCancelUrl("http://localhost:4200/cancel");
    redirectUrls.setReturnUrl("http://localhost:4200/complete-payment");
    payment.setRedirectUrls(redirectUrls);
    Payment createdPayment;
    try {
      String redirectUrl = "";
      APIContext context = new APIContext(clientId, clientSecret, "sandbox");
      createdPayment = payment.create(context);
      if(createdPayment!=null){
        List<Links> links = createdPayment.getLinks();
        for (Links link:links) {
          if(link.getRel().equals("approval_url")){
            redirectUrl = link.getHref();
            break;
          }
        }
        response.put("status", "success");
        response.put("redirect_url", redirectUrl);
      }
    } catch (PayPalRESTException e) {
      System.out.println("Error happened during payment creation!");
    }
    return response;
  }

  public ResponseEntity<String> completePayment(HttpServletRequest req){
    Map<String, Object> response = new HashMap<>();
    Payment payment = new Payment();
    payment.setId(req.getParameter("paymentId"));

    PaymentExecution paymentExecution = new PaymentExecution();
    paymentExecution.setPayerId(req.getParameter("PayerID"));
    try {
      APIContext context = new APIContext(clientId, clientSecret, "sandbox");
      Payment createdPayment = payment.execute(context, paymentExecution);
      if(createdPayment!=null){
        System.out.println(createdPayment.toJSON());
        response.put("status", "success");
        response.put("payment", createdPayment);
      }
    } catch (PayPalRESTException e) {
      System.err.println(e.getDetails());
    }
    return  ResponseEntity.ok(response.toString());
  }
}
