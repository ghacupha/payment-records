package io.github.erp.repository;

import io.github.erp.domain.PaymentsFileType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentsFileType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentsFileTypeRepository extends JpaRepository<PaymentsFileType, Long>, JpaSpecificationExecutor<PaymentsFileType> {
}
