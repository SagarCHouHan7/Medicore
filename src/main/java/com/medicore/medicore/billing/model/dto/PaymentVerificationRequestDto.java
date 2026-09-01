package com.medicore.medicore.billing.model.dto;

public record PaymentVerificationRequestDto(
        String razorpay_order_id,
        String razorpay_payment_id,
        String razorpay_signature
) {
}
