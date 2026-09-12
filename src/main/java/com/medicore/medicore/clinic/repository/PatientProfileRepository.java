package com.medicore.medicore.clinic.repository;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {

    Optional<PatientProfile> findByUserId(Long userId);

    Optional<PatientProfile> findByUser(User user);
}
