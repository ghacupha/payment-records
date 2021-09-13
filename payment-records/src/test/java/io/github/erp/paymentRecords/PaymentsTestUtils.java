
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A place to hide lengthy setups and huge decorators
 */
public class PaymentsTestUtils {
    protected List<Payment> paymentsSaved = new ArrayList<>();
    protected List<Payment> paymentsToSave = new ArrayList<>();

    public PaymentsTestUtils() {

        Payment p1 = new Payment();
        Payment p2 = new Payment();
        Payment p3 = new Payment();
        Payment p4 = new Payment();
        Payment p5 = new Payment();
        Payment p6 = new Payment();
        Payment p7 = new Payment();
        Payment p8 = new Payment();

        p1.setId(1L);
        p2.setId(2L);
        p3.setId(3L);
        p4.setId(4L);
        p5.setId(5L);
        p6.setId(6L);
        p7.setId(7L);
        p8.setId(8L);

        paymentsSaved.add(p1);
        paymentsSaved.add(p2);
        paymentsSaved.add(p3);
        paymentsSaved.add(p4);

        paymentsToSave.add(p5);
        paymentsToSave.add(p6);
        paymentsToSave.add(p7);
        paymentsToSave.add(p8);
    }

    /**
     * Decorator created to do nothing except override with default all implementations
     * of the payments-search-repository, so that if desired a small implementation can
     * quickly be cooked up by overriding only the methods that are needed
     */
    class SearchRepoDecorator implements PaymentSearchRepository {

        @Override
        public <S extends Payment> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public boolean existsById(Long paymentId) {
            return false;
        }

        @Override
        public <S extends Payment> S index(S entity) {
            return null;
        }

        @Override
        public <S extends Payment> S indexWithoutRefresh(S entity) {
            return null;
        }

        @Override
        public Iterable<Payment> search(QueryBuilder query) {
            return null;
        }

        @Override
        public Page<Payment> search(QueryBuilder query, Pageable pageable) {
            return null;
        }

        @Override
        public Page<Payment> search(SearchQuery searchQuery) {
            return null;
        }

        @Override
        public Page<Payment> searchSimilar(Payment entity, String[] fields, Pageable pageable) {
            return null;
        }

        @Override
        public void refresh() {

        }

        @Override
        public Class<Payment> getEntityClass() {
            return null;
        }

        @Override
        public Iterable<Payment> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<Payment> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Payment> S save(S entity) {
            return null;
        }

        @Override
        public Optional<Payment> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public Iterable<Payment> findAll() {
            return null;
        }

        @Override
        public Iterable<Payment> findAllById(Iterable<Long> longs) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(Payment entity) {

        }

        @Override
        public void deleteAll(Iterable<? extends Payment> entities) {

        }

        @Override
        public void deleteAll() {

        }
    }
}
