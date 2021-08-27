package io.github.erp.internal.service;

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
