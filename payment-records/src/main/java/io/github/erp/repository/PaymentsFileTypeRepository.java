package io.github.erp.repository;

import io.github.erp.domain.PaymentsFileType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PaymentsFileType entity.
 */
@Repository
public interface PaymentsFileTypeRepository extends JpaRepository<PaymentsFileType, Long>, JpaSpecificationExecutor<PaymentsFileType> {

    @Query(value = "select distinct paymentsFileType from PaymentsFileType paymentsFileType left join fetch paymentsFileType.placeholders",
        countQuery = "select count(distinct paymentsFileType) from PaymentsFileType paymentsFileType")
    Page<PaymentsFileType> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct paymentsFileType from PaymentsFileType paymentsFileType left join fetch paymentsFileType.placeholders")
    List<PaymentsFileType> findAllWithEagerRelationships();

    @Query("select paymentsFileType from PaymentsFileType paymentsFileType left join fetch paymentsFileType.placeholders where paymentsFileType.id =:id")
    Optional<PaymentsFileType> findOneWithEagerRelationships(@Param("id") Long id);
}
