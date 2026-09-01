package com.medicore.medicore.doctor;

public enum ApprovalStatus {
    WAITING_FOR_APPROVAL("Waiting for Approval"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String displayName;

    ApprovalStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}