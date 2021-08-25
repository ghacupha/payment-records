package io.github.erp.repository;

import io.github.erp.domain.PaymentsFileUpload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PaymentsFileUpload entity.
 */
@Repository
public interface PaymentsFileUploadRepository extends JpaRepository<PaymentsFileUpload, Long>, JpaSpecificationExecutor<PaymentsFileUpload> {

    @Query(value = "select distinct paymentsFileUpload from PaymentsFileUpload paymentsFileUpload left join fetch paymentsFileUpload.placeholders",
        countQuery = "select count(distinct paymentsFileUpload) from PaymentsFileUpload paymentsFileUpload")
    Page<PaymentsFileUpload> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct paymentsFileUpload from PaymentsFileUpload paymentsFileUpload left join fetch paymentsFileUpload.placeholders")
    List<PaymentsFileUpload> findAllWithEagerRelationships();

    @Query("select paymentsFileUpload from PaymentsFileUpload paymentsFileUpload left join fetch paymentsFileUpload.placeholders where paymentsFileUpload.id =:id")
    Optional<PaymentsFileUpload> findOneWithEagerRelationships(@Param("id") Long id);
}
