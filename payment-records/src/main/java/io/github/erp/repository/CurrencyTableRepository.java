package io.github.erp.repository;

import io.github.erp.domain.CurrencyTable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CurrencyTable entity.
 */
@Repository
public interface CurrencyTableRepository extends JpaRepository<CurrencyTable, Long>, JpaSpecificationExecutor<CurrencyTable> {

    @Query(value = "select distinct currencyTable from CurrencyTable currencyTable left join fetch currencyTable.placeholders",
        countQuery = "select count(distinct currencyTable) from CurrencyTable currencyTable")
    Page<CurrencyTable> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct currencyTable from CurrencyTable currencyTable left join fetch currencyTable.placeholders")
    List<CurrencyTable> findAllWithEagerRelationships();

    @Query("select currencyTable from CurrencyTable currencyTable left join fetch currencyTable.placeholders where currencyTable.id =:id")
    Optional<CurrencyTable> findOneWithEagerRelationships(@Param("id") Long id);
}
