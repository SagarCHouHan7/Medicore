package com.medicore.medicore.billing.model.dto;

import java.time.YearMonth;

public record HospitalDoctorMonthlyDto(

        String doctorName,

        YearMonth month,

        Long totalPatients,

        Long totalAppointments,

        Long totalRevenueGenerated,

        Long hospitalShare,

        Long doctorShare

) {}
