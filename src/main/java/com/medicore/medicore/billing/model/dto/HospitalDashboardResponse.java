package com.medicore.medicore.billing.model.dto;

import java.util.List;

public record HospitalDashboardResponse(
        List<HospitalDoctorMonthlyDto> earnings
) {}