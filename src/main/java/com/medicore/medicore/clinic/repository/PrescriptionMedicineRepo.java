package com.medicore.medicore.clinic.repository;

import com.medicore.medicore.clinic.model.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionMedicineRepo extends JpaRepository<PrescriptionMedicine, Long> {
}
