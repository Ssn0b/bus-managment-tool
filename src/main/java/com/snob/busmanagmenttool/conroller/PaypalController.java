package com.snob.busmanagmenttool.conroller;


import com.paypal.base.rest.PayPalRESTException;
import com.snob.busmanagmenttool.model.entity.paypal.PaymentRequest;
import com.snob.busmanagmenttool.service.PaypalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//@TODO НЕ ПРАЦюЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄЄ
@RestController
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
public class PaypalController {
    private final PaypalService paypalService;
    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) throws PayPalRESTException {
        // Create a PayPal payment and get the approval URL
        String approvalUrl = paypalService.createPayment(paymentRequest);
        return ResponseEntity.ok(approvalUrl);
    }

    @GetMapping("/execute-payment")
    public ResponseEntity<String> executePayment(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId) throws PayPalRESTException {
        // Execute the PayPal payment
        String result = paypalService.executePayment(paymentId, payerId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/cancel")
    public String handleCancel() {
        return "Payment canceled. Your order has not been processed.";
    }

    @GetMapping("/success")
    public String handleSuccess() {
        return "Payment successful. Your order has been processed.";
    }
}
