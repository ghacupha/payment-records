package io.github.erp.service;

import io.github.erp.service.dto.PaymentsFileUploadDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.erp.domain.PaymentsFileUpload}.
 */
public interface PaymentsFileUploadService {

    /**
     * Save a paymentsFileUpload.
     *
     * @param paymentsFileUploadDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentsFileUploadDTO save(PaymentsFileUploadDTO paymentsFileUploadDTO);

    /**
     * Get all the paymentsFileUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentsFileUploadDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paymentsFileUpload.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentsFileUploadDTO> findOne(Long id);

    /**
     * Delete the "id" paymentsFileUpload.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the paymentsFileUpload corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentsFileUploadDTO> search(String query, Pageable pageable);
}
