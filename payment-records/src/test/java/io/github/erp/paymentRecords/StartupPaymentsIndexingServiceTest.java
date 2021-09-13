
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
import io.github.erp.repository.search.PaymentSearchRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StartupPaymentsIndexingServiceTest extends PaymentsTestUtils {

    private StartupPaymentsIndexingService indexingService;


    @BeforeEach
    void setUp() {
        indexingService = new StartupPaymentsIndexingService(new SearchRepository());

    }

    @Test
    void index() {

        List<Payment> startUpPayments = new ArrayList<>();
        startUpPayments.addAll(paymentsSaved);
        startUpPayments.addAll(paymentsToSave);

        List<Payment> indexedPayments = indexingService.index(startUpPayments);

        assertThat(paymentsToSave.containsAll(indexedPayments)).isTrue();
        assertThat(paymentsSaved.containsAll(indexedPayments)).isFalse();

    }


    class SearchRepository extends SearchRepoDecorator implements PaymentSearchRepository {

        @Override
        public boolean existsById(Long paymentId) {

            return paymentsSaved
                .stream()
                .map(Payment::getId)
                .anyMatch(id -> id.equals(paymentId));
        }

        @Override
        public <S extends Payment> Iterable<S> saveAll(Iterable<S> entities) {
            return (Iterable<S>) paymentsToSave;
        }
    }
}
