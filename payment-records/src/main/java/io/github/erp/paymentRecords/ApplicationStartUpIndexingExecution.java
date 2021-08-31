package io.github.erp.paymentRecords;

import io.github.erp.domain.Payment;
import io.github.erp.repository.PaymentRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUpIndexingExecution implements ApplicationListener<ApplicationReadyEvent> {

    private final PaymentRepository paymentRepository;
    private final ApplicationIndexingService<Payment> applicationIndexingService;

    public ApplicationStartUpIndexingExecution(PaymentRepository paymentRepository, ApplicationIndexingService<Payment> applicationIndexingService) {
        this.paymentRepository = paymentRepository;
        this.applicationIndexingService = applicationIndexingService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        applicationIndexingService.index(paymentRepository.findAll());
    }
}
