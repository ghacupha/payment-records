package io.github.erp.service;

import io.github.erp.domain.PaymentsFileType;
import io.github.erp.repository.PaymentsFileTypeRepository;
import io.github.erp.repository.search.PaymentsFileTypeSearchRepository;
import io.github.erp.service.dto.PaymentsFileTypeDTO;
import io.github.erp.service.mapper.PaymentsFileTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaymentsFileType}.
 */
@Service
@Transactional
public class PaymentsFileTypeService {

    private final Logger log = LoggerFactory.getLogger(PaymentsFileTypeService.class);

    private final PaymentsFileTypeRepository paymentsFileTypeRepository;

    private final PaymentsFileTypeMapper paymentsFileTypeMapper;

    private final PaymentsFileTypeSearchRepository paymentsFileTypeSearchRepository;

    public PaymentsFileTypeService(PaymentsFileTypeRepository paymentsFileTypeRepository, PaymentsFileTypeMapper paymentsFileTypeMapper, PaymentsFileTypeSearchRepository paymentsFileTypeSearchRepository) {
        this.paymentsFileTypeRepository = paymentsFileTypeRepository;
        this.paymentsFileTypeMapper = paymentsFileTypeMapper;
        this.paymentsFileTypeSearchRepository = paymentsFileTypeSearchRepository;
    }

    /**
     * Save a paymentsFileType.
     *
     * @param paymentsFileTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentsFileTypeDTO save(PaymentsFileTypeDTO paymentsFileTypeDTO) {
        log.debug("Request to save PaymentsFileType : {}", paymentsFileTypeDTO);
        PaymentsFileType paymentsFileType = paymentsFileTypeMapper.toEntity(paymentsFileTypeDTO);
        paymentsFileType = paymentsFileTypeRepository.save(paymentsFileType);
        PaymentsFileTypeDTO result = paymentsFileTypeMapper.toDto(paymentsFileType);
        paymentsFileTypeSearchRepository.save(paymentsFileType);
        return result;
    }

    /**
     * Get all the paymentsFileTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsFileTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentsFileTypes");
        return paymentsFileTypeRepository.findAll(pageable)
            .map(paymentsFileTypeMapper::toDto);
    }


    /**
     * Get all the paymentsFileTypes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PaymentsFileTypeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return paymentsFileTypeRepository.findAllWithEagerRelationships(pageable).map(paymentsFileTypeMapper::toDto);
    }

    /**
     * Get one paymentsFileType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentsFileTypeDTO> findOne(Long id) {
        log.debug("Request to get PaymentsFileType : {}", id);
        return paymentsFileTypeRepository.findOneWithEagerRelationships(id)
            .map(paymentsFileTypeMapper::toDto);
    }

    /**
     * Delete the paymentsFileType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentsFileType : {}", id);
        paymentsFileTypeRepository.deleteById(id);
        paymentsFileTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentsFileType corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsFileTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentsFileTypes for query {}", query);
        return paymentsFileTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentsFileTypeMapper::toDto);
    }
}
