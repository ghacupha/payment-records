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

import io.github.erp.domain.PaymentsMessageToken;
import io.github.erp.domain.*; // for static metamodels
import io.github.erp.repository.PaymentsMessageTokenRepository;
import io.github.erp.repository.search.PaymentsMessageTokenSearchRepository;
import io.github.erp.service.dto.PaymentsMessageTokenCriteria;
import io.github.erp.service.dto.PaymentsMessageTokenDTO;
import io.github.erp.service.mapper.PaymentsMessageTokenMapper;

/**
 * Service for executing complex queries for {@link PaymentsMessageToken} entities in the database.
 * The main input is a {@link PaymentsMessageTokenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentsMessageTokenDTO} or a {@link Page} of {@link PaymentsMessageTokenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentsMessageTokenQueryService extends QueryService<PaymentsMessageToken> {

    private final Logger log = LoggerFactory.getLogger(PaymentsMessageTokenQueryService.class);

    private final PaymentsMessageTokenRepository paymentsMessageTokenRepository;

    private final PaymentsMessageTokenMapper paymentsMessageTokenMapper;

    private final PaymentsMessageTokenSearchRepository paymentsMessageTokenSearchRepository;

    public PaymentsMessageTokenQueryService(PaymentsMessageTokenRepository paymentsMessageTokenRepository, PaymentsMessageTokenMapper paymentsMessageTokenMapper, PaymentsMessageTokenSearchRepository paymentsMessageTokenSearchRepository) {
        this.paymentsMessageTokenRepository = paymentsMessageTokenRepository;
        this.paymentsMessageTokenMapper = paymentsMessageTokenMapper;
        this.paymentsMessageTokenSearchRepository = paymentsMessageTokenSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentsMessageTokenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentsMessageTokenDTO> findByCriteria(PaymentsMessageTokenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentsMessageToken> specification = createSpecification(criteria);
        return paymentsMessageTokenMapper.toDto(paymentsMessageTokenRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentsMessageTokenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsMessageTokenDTO> findByCriteria(PaymentsMessageTokenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentsMessageToken> specification = createSpecification(criteria);
        return paymentsMessageTokenRepository.findAll(specification, page)
            .map(paymentsMessageTokenMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentsMessageTokenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentsMessageToken> specification = createSpecification(criteria);
        return paymentsMessageTokenRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentsMessageTokenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentsMessageToken> createSpecification(PaymentsMessageTokenCriteria criteria) {
        Specification<PaymentsMessageToken> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentsMessageToken_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PaymentsMessageToken_.description));
            }
            if (criteria.getTimeSent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeSent(), PaymentsMessageToken_.timeSent));
            }
            if (criteria.getTokenValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTokenValue(), PaymentsMessageToken_.tokenValue));
            }
            if (criteria.getReceived() != null) {
                specification = specification.and(buildSpecification(criteria.getReceived(), PaymentsMessageToken_.received));
            }
            if (criteria.getActioned() != null) {
                specification = specification.and(buildSpecification(criteria.getActioned(), PaymentsMessageToken_.actioned));
            }
            if (criteria.getContentFullyEnqueued() != null) {
                specification = specification.and(buildSpecification(criteria.getContentFullyEnqueued(), PaymentsMessageToken_.contentFullyEnqueued));
            }
        }
        return specification;
    }
}
