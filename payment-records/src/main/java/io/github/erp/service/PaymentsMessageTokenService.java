package io.github.erp.service;

import io.github.erp.service.dto.PaymentsMessageTokenDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.erp.domain.PaymentsMessageToken}.
 */
public interface PaymentsMessageTokenService {

    /**
     * Save a paymentsMessageToken.
     *
     * @param paymentsMessageTokenDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentsMessageTokenDTO save(PaymentsMessageTokenDTO paymentsMessageTokenDTO);

    /**
     * Get all the paymentsMessageTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentsMessageTokenDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paymentsMessageToken.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentsMessageTokenDTO> findOne(Long id);

    /**
     * Delete the "id" paymentsMessageToken.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the paymentsMessageToken corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentsMessageTokenDTO> search(String query, Pageable pageable);
}
