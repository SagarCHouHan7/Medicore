package com.medicore.medicore.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionMedicineRepo extends JpaRepository<PrescriptionMedicine, Long> {
}
