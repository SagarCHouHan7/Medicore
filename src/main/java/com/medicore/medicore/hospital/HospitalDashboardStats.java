package com.medicore.medicore.hospital;

public record HospitalDashboardStats(
        int totalDoctors,
        int pendingVerifications,
        int totalAppointmentsThisMonth,
        long revenueThisMonthInPaise
) {
}
