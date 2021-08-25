package io.github.erp.service;

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

import io.github.erp.domain.PaymentsFileType;
import io.github.erp.domain.*; // for static metamodels
import io.github.erp.repository.PaymentsFileTypeRepository;
import io.github.erp.repository.search.PaymentsFileTypeSearchRepository;
import io.github.erp.service.dto.PaymentsFileTypeCriteria;
import io.github.erp.service.dto.PaymentsFileTypeDTO;
import io.github.erp.service.mapper.PaymentsFileTypeMapper;

/**
 * Service for executing complex queries for {@link PaymentsFileType} entities in the database.
 * The main input is a {@link PaymentsFileTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentsFileTypeDTO} or a {@link Page} of {@link PaymentsFileTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentsFileTypeQueryService extends QueryService<PaymentsFileType> {

    private final Logger log = LoggerFactory.getLogger(PaymentsFileTypeQueryService.class);

    private final PaymentsFileTypeRepository paymentsFileTypeRepository;

    private final PaymentsFileTypeMapper paymentsFileTypeMapper;

    private final PaymentsFileTypeSearchRepository paymentsFileTypeSearchRepository;

    public PaymentsFileTypeQueryService(PaymentsFileTypeRepository paymentsFileTypeRepository, PaymentsFileTypeMapper paymentsFileTypeMapper, PaymentsFileTypeSearchRepository paymentsFileTypeSearchRepository) {
        this.paymentsFileTypeRepository = paymentsFileTypeRepository;
        this.paymentsFileTypeMapper = paymentsFileTypeMapper;
        this.paymentsFileTypeSearchRepository = paymentsFileTypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentsFileTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentsFileTypeDTO> findByCriteria(PaymentsFileTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentsFileType> specification = createSpecification(criteria);
        return paymentsFileTypeMapper.toDto(paymentsFileTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentsFileTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsFileTypeDTO> findByCriteria(PaymentsFileTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentsFileType> specification = createSpecification(criteria);
        return paymentsFileTypeRepository.findAll(specification, page)
            .map(paymentsFileTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentsFileTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentsFileType> specification = createSpecification(criteria);
        return paymentsFileTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentsFileTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentsFileType> createSpecification(PaymentsFileTypeCriteria criteria) {
        Specification<PaymentsFileType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentsFileType_.id));
            }
            if (criteria.getPaymentsFileTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentsFileTypeName(), PaymentsFileType_.paymentsFileTypeName));
            }
            if (criteria.getPaymentsFileMediumType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentsFileMediumType(), PaymentsFileType_.paymentsFileMediumType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PaymentsFileType_.description));
            }
            if (criteria.getPaymentsfileType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentsfileType(), PaymentsFileType_.paymentsfileType));
            }
            if (criteria.getPlaceholderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlaceholderId(),
                    root -> root.join(PaymentsFileType_.placeholders, JoinType.LEFT).get(Placeholder_.id)));
            }
        }
        return specification;
    }
}
