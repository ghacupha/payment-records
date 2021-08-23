package io.github.erp.repository;

/*-
 * Payment Records - Payment records is part of the ERP System
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
