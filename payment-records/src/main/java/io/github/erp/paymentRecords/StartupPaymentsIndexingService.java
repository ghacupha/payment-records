
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
