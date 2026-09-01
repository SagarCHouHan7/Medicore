package com.medicore.medicore.billing.repository;

import com.medicore.medicore.billing.model.entity.Settlement;
import com.medicore.medicore.billing.model.status.SettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    List<Settlement> findAllByOrderBySettledAtDesc();

    List<Settlement> findByStatusOrderByCreatedAtDesc(SettlementStatus status);
}
