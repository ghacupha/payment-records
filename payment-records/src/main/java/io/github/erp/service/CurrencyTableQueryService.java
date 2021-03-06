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

import io.github.erp.domain.CurrencyTable;
import io.github.erp.domain.*; // for static metamodels
import io.github.erp.repository.CurrencyTableRepository;
import io.github.erp.repository.search.CurrencyTableSearchRepository;
import io.github.erp.service.dto.CurrencyTableCriteria;
import io.github.erp.service.dto.CurrencyTableDTO;
import io.github.erp.service.mapper.CurrencyTableMapper;

/**
 * Service for executing complex queries for {@link CurrencyTable} entities in the database.
 * The main input is a {@link CurrencyTableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CurrencyTableDTO} or a {@link Page} of {@link CurrencyTableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CurrencyTableQueryService extends QueryService<CurrencyTable> {

    private final Logger log = LoggerFactory.getLogger(CurrencyTableQueryService.class);

    private final CurrencyTableRepository currencyTableRepository;

    private final CurrencyTableMapper currencyTableMapper;

    private final CurrencyTableSearchRepository currencyTableSearchRepository;

    public CurrencyTableQueryService(CurrencyTableRepository currencyTableRepository, CurrencyTableMapper currencyTableMapper, CurrencyTableSearchRepository currencyTableSearchRepository) {
        this.currencyTableRepository = currencyTableRepository;
        this.currencyTableMapper = currencyTableMapper;
        this.currencyTableSearchRepository = currencyTableSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CurrencyTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CurrencyTableDTO> findByCriteria(CurrencyTableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CurrencyTable> specification = createSpecification(criteria);
        return currencyTableMapper.toDto(currencyTableRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CurrencyTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrencyTableDTO> findByCriteria(CurrencyTableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CurrencyTable> specification = createSpecification(criteria);
        return currencyTableRepository.findAll(specification, page)
            .map(currencyTableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CurrencyTableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CurrencyTable> specification = createSpecification(criteria);
        return currencyTableRepository.count(specification);
    }

    /**
     * Function to convert {@link CurrencyTableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CurrencyTable> createSpecification(CurrencyTableCriteria criteria) {
        Specification<CurrencyTable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CurrencyTable_.id));
            }
            if (criteria.getCurrencyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrencyCode(), CurrencyTable_.currencyCode));
            }
            if (criteria.getLocality() != null) {
                specification = specification.and(buildSpecification(criteria.getLocality(), CurrencyTable_.locality));
            }
            if (criteria.getCurrencyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrencyName(), CurrencyTable_.currencyName));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), CurrencyTable_.country));
            }
            if (criteria.getPlaceholderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlaceholderId(),
                    root -> root.join(CurrencyTable_.placeholders, JoinType.LEFT).get(Placeholder_.id)));
            }
        }
        return specification;
    }
}
