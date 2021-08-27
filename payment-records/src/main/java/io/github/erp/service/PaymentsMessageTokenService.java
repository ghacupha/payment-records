package io.github.erp.service;

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

import io.github.erp.service.dto.PaymentsMessageTokenDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.erp.domain.PaymentsMessageToken}.
 */
public interface PaymentsMessageTokenService {

    /**
     * Save a paymentsMessageToken.
     *
     * @param paymentsMessageTokenDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentsMessageTokenDTO save(PaymentsMessageTokenDTO paymentsMessageTokenDTO);

    /**
     * Get all the paymentsMessageTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentsMessageTokenDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paymentsMessageToken.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentsMessageTokenDTO> findOne(Long id);

    /**
     * Delete the "id" paymentsMessageToken.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the paymentsMessageToken corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentsMessageTokenDTO> search(String query, Pageable pageable);
}
