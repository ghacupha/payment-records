package io.github.erp.repository;

import io.github.erp.domain.PaymentsMessageToken;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentsMessageToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentsMessageTokenRepository extends JpaRepository<PaymentsMessageToken, Long>, JpaSpecificationExecutor<PaymentsMessageToken> {
}
