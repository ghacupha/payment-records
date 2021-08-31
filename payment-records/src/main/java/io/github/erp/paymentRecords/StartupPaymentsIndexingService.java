package io.github.erp.paymentRecords;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.github.erp.domain.Payment;
import io.github.erp.repository.search.PaymentSearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A service to index all payments during the application startup
 */
@Transactional
@Service
public class StartupPaymentsIndexingService implements ApplicationIndexingService<Payment> {

    private final PaymentSearchRepository paymentSearchRepository;

    public StartupPaymentsIndexingService(PaymentSearchRepository paymentSearchRepository) {
        this.paymentSearchRepository = paymentSearchRepository;
    }

    @Override
    public List<Payment> index(List<Payment> entities) {

        return Lists.newArrayList(paymentSearchRepository.saveAll(
            entities.stream()
                .filter(entity -> !paymentSearchRepository.existsById(entity.getId()))
                .collect(ImmutableList.toImmutableList())));
    }
}
