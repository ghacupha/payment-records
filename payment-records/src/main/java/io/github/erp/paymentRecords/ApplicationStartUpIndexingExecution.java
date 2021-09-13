
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
