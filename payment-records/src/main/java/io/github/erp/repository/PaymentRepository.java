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

import io.github.erp.domain.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Payment entity.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    @Query(value = "select distinct payment from Payment payment left join fetch payment.placeholders left join fetch payment.paymentLabels",
        countQuery = "select count(distinct payment) from Payment payment")
    Page<Payment> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct payment from Payment payment left join fetch payment.placeholders left join fetch payment.paymentLabels")
    List<Payment> findAllWithEagerRelationships();

    @Query("select payment from Payment payment left join fetch payment.placeholders left join fetch payment.paymentLabels where payment.id =:id")
    Optional<Payment> findOneWithEagerRelationships(@Param("id") Long id);
}
