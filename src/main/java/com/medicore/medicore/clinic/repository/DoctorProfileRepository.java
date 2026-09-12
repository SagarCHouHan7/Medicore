package com.medicore.medicore.clinic.repository;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.ApprovalStatus;
import com.medicore.medicore.clinic.model.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    Optional<DoctorProfile> findByUserId(Long userId);

    Optional<DoctorProfile> findByUser(User user);

    List<DoctorProfile> findUnverifiedDoctorsByHospitalProfileId(Long hospitalProfileId);

    List<DoctorProfile> findByHospitalProfileIdAndApprovalStatus(
            Long hospitalProfileId,
            ApprovalStatus approvalStatus
    );
}
