package io.github.erp.repository;

import io.github.erp.domain.PaymentsFileUpload;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentsFileUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentsFileUploadRepository extends JpaRepository<PaymentsFileUpload, Long>, JpaSpecificationExecutor<PaymentsFileUpload> {
}
