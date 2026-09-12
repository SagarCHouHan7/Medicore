package com.medicore.medicore.clinic.repository;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.HospitalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HospitalRepo extends JpaRepository<HospitalProfile, Long> {

    Optional<HospitalProfile> findByHospitalAdministrator(User hospitalAdministrator);

}
