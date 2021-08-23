package io.github.erp.web.rest;

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

import io.github.erp.domain.PaymentsFileType;
import io.github.erp.service.PaymentsFileTypeService;
import io.github.erp.web.rest.errors.BadRequestAlertException;
import io.github.erp.service.dto.PaymentsFileTypeCriteria;
import io.github.erp.service.PaymentsFileTypeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.erp.domain.PaymentsFileType}.
 */
@RestController
@RequestMapping("/api")
public class PaymentsFileTypeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsFileTypeResource.class);

    private static final String ENTITY_NAME = "paymentsPaymentsFileType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentsFileTypeService paymentsFileTypeService;

    private final PaymentsFileTypeQueryService paymentsFileTypeQueryService;

    public PaymentsFileTypeResource(PaymentsFileTypeService paymentsFileTypeService, PaymentsFileTypeQueryService paymentsFileTypeQueryService) {
        this.paymentsFileTypeService = paymentsFileTypeService;
        this.paymentsFileTypeQueryService = paymentsFileTypeQueryService;
    }

    /**
     * {@code POST  /payments-file-types} : Create a new paymentsFileType.
     *
     * @param paymentsFileType the paymentsFileType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentsFileType, or with status {@code 400 (Bad Request)} if the paymentsFileType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments-file-types")
    public ResponseEntity<PaymentsFileType> createPaymentsFileType(@Valid @RequestBody PaymentsFileType paymentsFileType) throws URISyntaxException {
        log.debug("REST request to save PaymentsFileType : {}", paymentsFileType);
        if (paymentsFileType.getId() != null) {
            throw new BadRequestAlertException("A new paymentsFileType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentsFileType result = paymentsFileTypeService.save(paymentsFileType);
        return ResponseEntity.created(new URI("/api/payments-file-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payments-file-types} : Updates an existing paymentsFileType.
     *
     * @param paymentsFileType the paymentsFileType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentsFileType,
     * or with status {@code 400 (Bad Request)} if the paymentsFileType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentsFileType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments-file-types")
    public ResponseEntity<PaymentsFileType> updatePaymentsFileType(@Valid @RequestBody PaymentsFileType paymentsFileType) throws URISyntaxException {
        log.debug("REST request to update PaymentsFileType : {}", paymentsFileType);
        if (paymentsFileType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentsFileType result = paymentsFileTypeService.save(paymentsFileType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentsFileType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payments-file-types} : get all the paymentsFileTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentsFileTypes in body.
     */
    @GetMapping("/payments-file-types")
    public ResponseEntity<List<PaymentsFileType>> getAllPaymentsFileTypes(PaymentsFileTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaymentsFileTypes by criteria: {}", criteria);
        Page<PaymentsFileType> page = paymentsFileTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payments-file-types/count} : count all the paymentsFileTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payments-file-types/count")
    public ResponseEntity<Long> countPaymentsFileTypes(PaymentsFileTypeCriteria criteria) {
        log.debug("REST request to count PaymentsFileTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentsFileTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payments-file-types/:id} : get the "id" paymentsFileType.
     *
     * @param id the id of the paymentsFileType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentsFileType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments-file-types/{id}")
    public ResponseEntity<PaymentsFileType> getPaymentsFileType(@PathVariable Long id) {
        log.debug("REST request to get PaymentsFileType : {}", id);
        Optional<PaymentsFileType> paymentsFileType = paymentsFileTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentsFileType);
    }

    /**
     * {@code DELETE  /payments-file-types/:id} : delete the "id" paymentsFileType.
     *
     * @param id the id of the paymentsFileType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments-file-types/{id}")
    public ResponseEntity<Void> deletePaymentsFileType(@PathVariable Long id) {
        log.debug("REST request to delete PaymentsFileType : {}", id);
        paymentsFileTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/payments-file-types?query=:query} : search for the paymentsFileType corresponding
     * to the query.
     *
     * @param query the query of the paymentsFileType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/payments-file-types")
    public ResponseEntity<List<PaymentsFileType>> searchPaymentsFileTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PaymentsFileTypes for query {}", query);
        Page<PaymentsFileType> page = paymentsFileTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
