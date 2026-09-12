package com.medicore.medicore.clinic.dto;

public record HospitalDashboardStats(
        int totalDoctors,
        int pendingVerifications,
        int totalAppointmentsThisMonth,
        long revenueThisMonthInPaise
) {
}
