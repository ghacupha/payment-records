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
