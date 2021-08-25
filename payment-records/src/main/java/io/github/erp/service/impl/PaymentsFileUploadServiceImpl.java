package io.github.erp.service.impl;

import io.github.erp.service.PaymentsFileUploadService;
import io.github.erp.domain.PaymentsFileUpload;
import io.github.erp.repository.PaymentsFileUploadRepository;
import io.github.erp.repository.search.PaymentsFileUploadSearchRepository;
import io.github.erp.service.dto.PaymentsFileUploadDTO;
import io.github.erp.service.mapper.PaymentsFileUploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaymentsFileUpload}.
 */
@Service
@Transactional
public class PaymentsFileUploadServiceImpl implements PaymentsFileUploadService {

    private final Logger log = LoggerFactory.getLogger(PaymentsFileUploadServiceImpl.class);

    private final PaymentsFileUploadRepository paymentsFileUploadRepository;

    private final PaymentsFileUploadMapper paymentsFileUploadMapper;

    private final PaymentsFileUploadSearchRepository paymentsFileUploadSearchRepository;

    public PaymentsFileUploadServiceImpl(PaymentsFileUploadRepository paymentsFileUploadRepository, PaymentsFileUploadMapper paymentsFileUploadMapper, PaymentsFileUploadSearchRepository paymentsFileUploadSearchRepository) {
        this.paymentsFileUploadRepository = paymentsFileUploadRepository;
        this.paymentsFileUploadMapper = paymentsFileUploadMapper;
        this.paymentsFileUploadSearchRepository = paymentsFileUploadSearchRepository;
    }

    @Override
    public PaymentsFileUploadDTO save(PaymentsFileUploadDTO paymentsFileUploadDTO) {
        log.debug("Request to save PaymentsFileUpload : {}", paymentsFileUploadDTO);
        PaymentsFileUpload paymentsFileUpload = paymentsFileUploadMapper.toEntity(paymentsFileUploadDTO);
        paymentsFileUpload = paymentsFileUploadRepository.save(paymentsFileUpload);
        PaymentsFileUploadDTO result = paymentsFileUploadMapper.toDto(paymentsFileUpload);
        paymentsFileUploadSearchRepository.save(paymentsFileUpload);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentsFileUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentsFileUploads");
        return paymentsFileUploadRepository.findAll(pageable)
            .map(paymentsFileUploadMapper::toDto);
    }


    public Page<PaymentsFileUploadDTO> findAllWithEagerRelationships(Pageable pageable) {
        return paymentsFileUploadRepository.findAllWithEagerRelationships(pageable).map(paymentsFileUploadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentsFileUploadDTO> findOne(Long id) {
        log.debug("Request to get PaymentsFileUpload : {}", id);
        return paymentsFileUploadRepository.findOneWithEagerRelationships(id)
            .map(paymentsFileUploadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentsFileUpload : {}", id);
        paymentsFileUploadRepository.deleteById(id);
        paymentsFileUploadSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentsFileUploadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentsFileUploads for query {}", query);
        return paymentsFileUploadSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentsFileUploadMapper::toDto);
    }
}
