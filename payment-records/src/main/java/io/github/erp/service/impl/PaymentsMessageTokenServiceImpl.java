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

import io.github.erp.service.PaymentsMessageTokenService;
import io.github.erp.domain.PaymentsMessageToken;
import io.github.erp.repository.PaymentsMessageTokenRepository;
import io.github.erp.repository.search.PaymentsMessageTokenSearchRepository;
import io.github.erp.service.dto.PaymentsMessageTokenDTO;
import io.github.erp.service.mapper.PaymentsMessageTokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaymentsMessageToken}.
 */
@Service
@Transactional
public class PaymentsMessageTokenServiceImpl implements PaymentsMessageTokenService {

    private final Logger log = LoggerFactory.getLogger(PaymentsMessageTokenServiceImpl.class);

    private final PaymentsMessageTokenRepository paymentsMessageTokenRepository;

    private final PaymentsMessageTokenMapper paymentsMessageTokenMapper;

    private final PaymentsMessageTokenSearchRepository paymentsMessageTokenSearchRepository;

    public PaymentsMessageTokenServiceImpl(PaymentsMessageTokenRepository paymentsMessageTokenRepository, PaymentsMessageTokenMapper paymentsMessageTokenMapper, PaymentsMessageTokenSearchRepository paymentsMessageTokenSearchRepository) {
        this.paymentsMessageTokenRepository = paymentsMessageTokenRepository;
        this.paymentsMessageTokenMapper = paymentsMessageTokenMapper;
        this.paymentsMessageTokenSearchRepository = paymentsMessageTokenSearchRepository;
    }

    @Override
    public PaymentsMessageTokenDTO save(PaymentsMessageTokenDTO paymentsMessageTokenDTO) {
        log.debug("Request to save PaymentsMessageToken : {}", paymentsMessageTokenDTO);
        PaymentsMessageToken paymentsMessageToken = paymentsMessageTokenMapper.toEntity(paymentsMessageTokenDTO);
        paymentsMessageToken = paymentsMessageTokenRepository.save(paymentsMessageToken);
        PaymentsMessageTokenDTO result = paymentsMessageTokenMapper.toDto(paymentsMessageToken);
        paymentsMessageTokenSearchRepository.save(paymentsMessageToken);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentsMessageTokenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentsMessageTokens");
        return paymentsMessageTokenRepository.findAll(pageable)
            .map(paymentsMessageTokenMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentsMessageTokenDTO> findOne(Long id) {
        log.debug("Request to get PaymentsMessageToken : {}", id);
        return paymentsMessageTokenRepository.findById(id)
            .map(paymentsMessageTokenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentsMessageToken : {}", id);
        paymentsMessageTokenRepository.deleteById(id);
        paymentsMessageTokenSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentsMessageTokenDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentsMessageTokens for query {}", query);
        return paymentsMessageTokenSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentsMessageTokenMapper::toDto);
    }
}
