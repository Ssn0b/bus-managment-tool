package com.snob.busmanagmenttool.service;

import com.google.common.collect.Lists;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.snob.busmanagmenttool.model.entity.paypal.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//@TODO НЕ ПРАЦюЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄ


@Service
@RequiredArgsConstructor
public class PaypalService {
    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.secret}")
    private String secret;

    @Value("${paypal.api.base-url}")
    private String apiUrl;

    public String createPayment(PaymentRequest paymentRequest) throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientId, secret, "sandbox"); // Use "sandbox" for testing

        // Create a payment object
        Payment payment = new Payment();
        payment.setIntent(paymentRequest.getIntent());

        Payer payer = new Payer();
        payer.setPaymentMethod(paymentRequest.getMethod());
        payment.setPayer(payer);

        Amount amount = new Amount();
        amount.setTotal(paymentRequest.getTotal().toString());
        amount.setCurrency(paymentRequest.getCurrency());

        Transaction transaction = new Transaction();
        transaction.setDescription(paymentRequest.getDescription());
        transaction.setAmount(amount); // Set the amount within the transaction

        Item item = new Item();
        item.setName(paymentRequest.getDescription());
        item.setQuantity("1");
        item.setPrice(paymentRequest.getTotal().toString());
        item.setCurrency(paymentRequest.getCurrency());
        item.setSku("SKU");

        ItemList itemList = new ItemList();
        itemList.setItems(Lists.newArrayList(item));
        transaction.setItemList(itemList);

        payment.setTransactions(Lists.newArrayList(transaction));

        Payment createdPayment = payment.create(apiContext);

        for (Links link : createdPayment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return link.getHref();
            }
        }

        return null;
    }

    public String executePayment(String paymentId, String payerId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientId, secret, "sandbox"); // Use "sandbox" for testing

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment();
        payment.setId(paymentId);

        Payment executedPayment = payment.execute(apiContext, paymentExecution);

        if (executedPayment.getState().equals("approved")) {
            return "Payment successful. Your order has been processed.";
        } else {
            return "Payment not approved.";
        }
    }
}
