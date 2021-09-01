package io.github.erp.paymentRecords;

import io.github.erp.domain.Payment;
import io.github.erp.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ApplicationStartUpIndexingExecution implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartUpIndexingExecution.class);
    private final PaymentRepository paymentRepository;
    private final ApplicationIndexingService<Payment> applicationIndexingService;

    public ApplicationStartUpIndexingExecution(PaymentRepository paymentRepository, ApplicationIndexingService<Payment> applicationIndexingService) {
        this.paymentRepository = paymentRepository;
        this.applicationIndexingService = applicationIndexingService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Optional<List<Payment>> storedEntities = Optional.of(paymentRepository.findAll());
        storedEntities.ifPresent(entities -> {
            log.info("Initiating startup index hydration with {} entities", entities.size());
            applicationIndexingService.index(entities);
            log.info("Search index initiated and ready for queries...");
        });
    }
}
