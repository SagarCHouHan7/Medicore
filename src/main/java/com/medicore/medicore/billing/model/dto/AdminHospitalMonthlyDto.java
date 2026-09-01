package com.medicore.medicore.billing.model.dto;

import com.medicore.medicore.billing.model.status.PayoutStatus;

import java.time.YearMonth;

public record AdminHospitalMonthlyDto(

        String hospitalName,

        YearMonth month,

        Long totalPatients,

        Long totalAppointments,

        Long totalRevenueGenerated,

        Long platformRevenue,

        Long amountPayableToHospital,

        PayoutStatus payoutStatus

) {}
