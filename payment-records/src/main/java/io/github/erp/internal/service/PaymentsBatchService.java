package io.github.erp.internal.service;

import io.github.erp.repository.PaymentRepository;
import io.github.erp.repository.search.PaymentSearchRepository;
import io.github.erp.service.dto.PaymentDTO;
import io.github.erp.service.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("paymentsBatchService")
public class PaymentsBatchService implements BatchService<PaymentDTO> {

    private final PaymentRepository paymentRepository;
    private final PaymentSearchRepository paymentSearchRepository;
    private final PaymentMapper paymentMapper;

    public PaymentsBatchService(PaymentRepository paymentRepository, PaymentSearchRepository paymentSearchRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentSearchRepository = paymentSearchRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentDTO> save(final List<PaymentDTO> entities) {
        return paymentMapper.toDto(paymentRepository.saveAll(paymentMapper.toEntity(entities)));
    }

    @Override
    public void index(final List<PaymentDTO> entities) {
        paymentSearchRepository.saveAll(paymentMapper.toEntity(entities));
    }
}
