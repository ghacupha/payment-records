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

import io.github.erp.domain.Payment;
import io.github.erp.domain.*; // for static metamodels
import io.github.erp.repository.PaymentRepository;
import io.github.erp.repository.search.PaymentSearchRepository;
import io.github.erp.service.dto.PaymentCriteria;
import io.github.erp.service.dto.PaymentDTO;
import io.github.erp.service.mapper.PaymentMapper;

/**
 * Service for executing complex queries for {@link Payment} entities in the database.
 * The main input is a {@link PaymentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentDTO} or a {@link Page} of {@link PaymentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentQueryService extends QueryService<Payment> {

    private final Logger log = LoggerFactory.getLogger(PaymentQueryService.class);

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final PaymentSearchRepository paymentSearchRepository;

    public PaymentQueryService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, PaymentSearchRepository paymentSearchRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.paymentSearchRepository = paymentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentDTO> findByCriteria(PaymentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentMapper.toDto(paymentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentDTO> findByCriteria(PaymentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentRepository.findAll(specification, page)
            .map(paymentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Payment> createSpecification(PaymentCriteria criteria) {
        Specification<Payment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Payment_.id));
            }
            if (criteria.getPaymentsCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentsCategory(), Payment_.paymentsCategory));
            }
            if (criteria.getTransactionNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionNumber(), Payment_.transactionNumber));
            }
            if (criteria.getTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionDate(), Payment_.transactionDate));
            }
            if (criteria.getTransactionCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionCurrency(), Payment_.transactionCurrency));
            }
            if (criteria.getTransactionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionAmount(), Payment_.transactionAmount));
            }
            if (criteria.getBeneficiary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBeneficiary(), Payment_.beneficiary));
            }
            if (criteria.getPlaceholderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlaceholderId(),
                    root -> root.join(Payment_.placeholders, JoinType.LEFT).get(Placeholder_.id)));
            }
            if (criteria.getPaymentLabelId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentLabelId(),
                    root -> root.join(Payment_.paymentLabels, JoinType.LEFT).get(PaymentLabel_.id)));
            }
        }
        return specification;
    }
}
