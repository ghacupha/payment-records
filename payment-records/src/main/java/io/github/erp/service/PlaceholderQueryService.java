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

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.erp.domain.Placeholder;
import io.github.erp.domain.*; // for static metamodels
import io.github.erp.repository.PlaceholderRepository;
import io.github.erp.repository.search.PlaceholderSearchRepository;
import io.github.erp.service.dto.PlaceholderCriteria;
import io.github.erp.service.dto.PlaceholderDTO;
import io.github.erp.service.mapper.PlaceholderMapper;

/**
 * Service for executing complex queries for {@link Placeholder} entities in the database.
 * The main input is a {@link PlaceholderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlaceholderDTO} or a {@link Page} of {@link PlaceholderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlaceholderQueryService extends QueryService<Placeholder> {

    private final Logger log = LoggerFactory.getLogger(PlaceholderQueryService.class);

    private final PlaceholderRepository placeholderRepository;

    private final PlaceholderMapper placeholderMapper;

    private final PlaceholderSearchRepository placeholderSearchRepository;

    public PlaceholderQueryService(PlaceholderRepository placeholderRepository, PlaceholderMapper placeholderMapper, PlaceholderSearchRepository placeholderSearchRepository) {
        this.placeholderRepository = placeholderRepository;
        this.placeholderMapper = placeholderMapper;
        this.placeholderSearchRepository = placeholderSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PlaceholderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlaceholderDTO> findByCriteria(PlaceholderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Placeholder> specification = createSpecification(criteria);
        return placeholderMapper.toDto(placeholderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlaceholderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlaceholderDTO> findByCriteria(PlaceholderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Placeholder> specification = createSpecification(criteria);
        return placeholderRepository.findAll(specification, page)
            .map(placeholderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlaceholderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Placeholder> specification = createSpecification(criteria);
        return placeholderRepository.count(specification);
    }

    /**
     * Function to convert {@link PlaceholderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Placeholder> createSpecification(PlaceholderCriteria criteria) {
        Specification<Placeholder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Placeholder_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Placeholder_.description));
            }
            if (criteria.getToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToken(), Placeholder_.token));
            }
            if (criteria.getCurrencyTableId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyTableId(),
                    root -> root.join(Placeholder_.currencyTables, JoinType.LEFT).get(CurrencyTable_.id)));
            }
            if (criteria.getPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentId(),
                    root -> root.join(Placeholder_.payments, JoinType.LEFT).get(Payment_.id)));
            }
            if (criteria.getPaymentsFileTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentsFileTypeId(),
                    root -> root.join(Placeholder_.paymentsFileTypes, JoinType.LEFT).get(PaymentsFileType_.id)));
            }
            if (criteria.getPaymentsFileUploadId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentsFileUploadId(),
                    root -> root.join(Placeholder_.paymentsFileUploads, JoinType.LEFT).get(PaymentsFileUpload_.id)));
            }
        }
        return specification;
    }
}
