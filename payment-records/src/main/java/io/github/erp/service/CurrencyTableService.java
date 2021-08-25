package io.github.erp.service;

import io.github.erp.service.dto.CurrencyTableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.erp.domain.CurrencyTable}.
 */
public interface CurrencyTableService {

    /**
     * Save a currencyTable.
     *
     * @param currencyTableDTO the entity to save.
     * @return the persisted entity.
     */
    CurrencyTableDTO save(CurrencyTableDTO currencyTableDTO);

    /**
     * Get all the currencyTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurrencyTableDTO> findAll(Pageable pageable);

    /**
     * Get all the currencyTables with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CurrencyTableDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" currencyTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurrencyTableDTO> findOne(Long id);

    /**
     * Delete the "id" currencyTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the currencyTable corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurrencyTableDTO> search(String query, Pageable pageable);
}
