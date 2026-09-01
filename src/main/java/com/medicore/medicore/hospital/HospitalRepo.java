package com.medicore.medicore.hospital;

import com.medicore.medicore.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HospitalRepo extends JpaRepository<HospitalProfile, Long> {

    Optional<HospitalProfile> findByHospitalAdministrator(User hospitalAdministrator);

}
