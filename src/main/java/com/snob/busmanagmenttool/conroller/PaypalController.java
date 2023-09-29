package com.snob.busmanagmenttool.conroller;
import com.snob.busmanagmenttool.service.PaypalService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
public class PaypalController {
    private final PaypalService payPalService;
    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestParam("sum") String sum){
        return payPalService.createPayment(sum);
    }
    @PostMapping(value = "/complete/payment")
    public ResponseEntity<String> completePayment(HttpServletRequest request){
        return payPalService.completePayment(request);
    }
}
