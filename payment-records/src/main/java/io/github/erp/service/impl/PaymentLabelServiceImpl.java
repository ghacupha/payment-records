package io.github.erp.service.impl;

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

import io.github.erp.service.PaymentLabelService;
import io.github.erp.domain.PaymentLabel;
import io.github.erp.repository.PaymentLabelRepository;
import io.github.erp.repository.search.PaymentLabelSearchRepository;
import io.github.erp.service.dto.PaymentLabelDTO;
import io.github.erp.service.mapper.PaymentLabelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaymentLabel}.
 */
@Service
@Transactional
public class PaymentLabelServiceImpl implements PaymentLabelService {

    private final Logger log = LoggerFactory.getLogger(PaymentLabelServiceImpl.class);

    private final PaymentLabelRepository paymentLabelRepository;

    private final PaymentLabelMapper paymentLabelMapper;

    private final PaymentLabelSearchRepository paymentLabelSearchRepository;

    public PaymentLabelServiceImpl(PaymentLabelRepository paymentLabelRepository, PaymentLabelMapper paymentLabelMapper, PaymentLabelSearchRepository paymentLabelSearchRepository) {
        this.paymentLabelRepository = paymentLabelRepository;
        this.paymentLabelMapper = paymentLabelMapper;
        this.paymentLabelSearchRepository = paymentLabelSearchRepository;
    }

    @Override
    public PaymentLabelDTO save(PaymentLabelDTO paymentLabelDTO) {
        log.debug("Request to save PaymentLabel : {}", paymentLabelDTO);
        PaymentLabel paymentLabel = paymentLabelMapper.toEntity(paymentLabelDTO);
        paymentLabel = paymentLabelRepository.save(paymentLabel);
        PaymentLabelDTO result = paymentLabelMapper.toDto(paymentLabel);
        paymentLabelSearchRepository.save(paymentLabel);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentLabelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentLabels");
        return paymentLabelRepository.findAll(pageable)
            .map(paymentLabelMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentLabelDTO> findOne(Long id) {
        log.debug("Request to get PaymentLabel : {}", id);
        return paymentLabelRepository.findById(id)
            .map(paymentLabelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentLabel : {}", id);
        paymentLabelRepository.deleteById(id);
        paymentLabelSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentLabelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentLabels for query {}", query);
        return paymentLabelSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentLabelMapper::toDto);
    }
}
