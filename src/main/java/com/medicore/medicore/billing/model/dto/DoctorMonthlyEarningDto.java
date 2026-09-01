package com.medicore.medicore.billing.model.dto;

import com.medicore.medicore.billing.model.status.PayoutStatus;

import java.time.YearMonth;

public record DoctorMonthlyEarningDto(

        YearMonth month,

        Long appointments,

        Long revenueGenerated,

        Long netReceivable,

        PayoutStatus payoutStatus

) {}