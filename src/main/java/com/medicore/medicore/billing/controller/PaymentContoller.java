package com.medicore.medicore.billing.controller;

import com.medicore.medicore.billing.service.PaymentService;
import com.medicore.medicore.comman.dto.ApiResponse;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/patient/payment")
@RequiredArgsConstructor
public class PaymentContoller {

    private final PaymentService paymentService;

    @PostMapping("/create-order/{id}")
    public ResponseEntity<ApiResponse<?>> createOrder(@PathVariable("id") Long billId) throws RazorpayException {
        log.info("inside create order");
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "order created successfully",
                        paymentService.createOrder(billId))
        );

    }


    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String,String> data) throws Exception {
        return ResponseEntity.ok(paymentService.verify(data));
    }

}
