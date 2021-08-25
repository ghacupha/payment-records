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

import io.github.erp.domain.PaymentsFileUpload;
import io.github.erp.domain.*; // for static metamodels
import io.github.erp.repository.PaymentsFileUploadRepository;
import io.github.erp.repository.search.PaymentsFileUploadSearchRepository;
import io.github.erp.service.dto.PaymentsFileUploadCriteria;
import io.github.erp.service.dto.PaymentsFileUploadDTO;
import io.github.erp.service.mapper.PaymentsFileUploadMapper;

/**
 * Service for executing complex queries for {@link PaymentsFileUpload} entities in the database.
 * The main input is a {@link PaymentsFileUploadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentsFileUploadDTO} or a {@link Page} of {@link PaymentsFileUploadDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentsFileUploadQueryService extends QueryService<PaymentsFileUpload> {

    private final Logger log = LoggerFactory.getLogger(PaymentsFileUploadQueryService.class);

    private final PaymentsFileUploadRepository paymentsFileUploadRepository;

    private final PaymentsFileUploadMapper paymentsFileUploadMapper;

    private final PaymentsFileUploadSearchRepository paymentsFileUploadSearchRepository;

    public PaymentsFileUploadQueryService(PaymentsFileUploadRepository paymentsFileUploadRepository, PaymentsFileUploadMapper paymentsFileUploadMapper, PaymentsFileUploadSearchRepository paymentsFileUploadSearchRepository) {
        this.paymentsFileUploadRepository = paymentsFileUploadRepository;
        this.paymentsFileUploadMapper = paymentsFileUploadMapper;
        this.paymentsFileUploadSearchRepository = paymentsFileUploadSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentsFileUploadDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentsFileUploadDTO> findByCriteria(PaymentsFileUploadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentsFileUpload> specification = createSpecification(criteria);
        return paymentsFileUploadMapper.toDto(paymentsFileUploadRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentsFileUploadDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsFileUploadDTO> findByCriteria(PaymentsFileUploadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentsFileUpload> specification = createSpecification(criteria);
        return paymentsFileUploadRepository.findAll(specification, page)
            .map(paymentsFileUploadMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentsFileUploadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentsFileUpload> specification = createSpecification(criteria);
        return paymentsFileUploadRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentsFileUploadCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentsFileUpload> createSpecification(PaymentsFileUploadCriteria criteria) {
        Specification<PaymentsFileUpload> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentsFileUpload_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PaymentsFileUpload_.description));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), PaymentsFileUpload_.fileName));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), PaymentsFileUpload_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), PaymentsFileUpload_.periodTo));
            }
            if (criteria.getPaymentsFileTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentsFileTypeId(), PaymentsFileUpload_.paymentsFileTypeId));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), PaymentsFileUpload_.uploadSuccessful));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), PaymentsFileUpload_.uploadProcessed));
            }
            if (criteria.getUploadToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUploadToken(), PaymentsFileUpload_.uploadToken));
            }
            if (criteria.getPlaceholderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlaceholderId(),
                    root -> root.join(PaymentsFileUpload_.placeholders, JoinType.LEFT).get(Placeholder_.id)));
            }
        }
        return specification;
    }
}
