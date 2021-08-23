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

import io.github.erp.domain.PaymentsFileType;
import io.github.erp.repository.PaymentsFileTypeRepository;
import io.github.erp.repository.search.PaymentsFileTypeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaymentsFileType}.
 */
@Service
@Transactional
public class PaymentsFileTypeService {

    private final Logger log = LoggerFactory.getLogger(PaymentsFileTypeService.class);

    private final PaymentsFileTypeRepository paymentsFileTypeRepository;

    private final PaymentsFileTypeSearchRepository paymentsFileTypeSearchRepository;

    public PaymentsFileTypeService(PaymentsFileTypeRepository paymentsFileTypeRepository, PaymentsFileTypeSearchRepository paymentsFileTypeSearchRepository) {
        this.paymentsFileTypeRepository = paymentsFileTypeRepository;
        this.paymentsFileTypeSearchRepository = paymentsFileTypeSearchRepository;
    }

    /**
     * Save a paymentsFileType.
     *
     * @param paymentsFileType the entity to save.
     * @return the persisted entity.
     */
    public PaymentsFileType save(PaymentsFileType paymentsFileType) {
        log.debug("Request to save PaymentsFileType : {}", paymentsFileType);
        PaymentsFileType result = paymentsFileTypeRepository.save(paymentsFileType);
        paymentsFileTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the paymentsFileTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsFileType> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentsFileTypes");
        return paymentsFileTypeRepository.findAll(pageable);
    }


    /**
     * Get one paymentsFileType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentsFileType> findOne(Long id) {
        log.debug("Request to get PaymentsFileType : {}", id);
        return paymentsFileTypeRepository.findById(id);
    }

    /**
     * Delete the paymentsFileType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentsFileType : {}", id);
        paymentsFileTypeRepository.deleteById(id);
        paymentsFileTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentsFileType corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsFileType> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentsFileTypes for query {}", query);
        return paymentsFileTypeSearchRepository.search(queryStringQuery(query), pageable);    }
}
