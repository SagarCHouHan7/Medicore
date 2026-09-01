package com.medicore.medicore.billing.model.dto;

import java.util.List;

public record AdminDashboardResponse(
        List<AdminHospitalMonthlyDto> earnings
) {}