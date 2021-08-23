package io.github.erp.web.rest;

import io.github.erp.service.PaymentsFileUploadService;
import io.github.erp.web.rest.errors.BadRequestAlertException;
import io.github.erp.service.dto.PaymentsFileUploadDTO;
import io.github.erp.service.dto.PaymentsFileUploadCriteria;
import io.github.erp.service.PaymentsFileUploadQueryService;

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
 * REST controller for managing {@link io.github.erp.domain.PaymentsFileUpload}.
 */
@RestController
@RequestMapping("/api")
public class PaymentsFileUploadResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsFileUploadResource.class);

    private static final String ENTITY_NAME = "paymentsPaymentsFileUpload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentsFileUploadService paymentsFileUploadService;

    private final PaymentsFileUploadQueryService paymentsFileUploadQueryService;

    public PaymentsFileUploadResource(PaymentsFileUploadService paymentsFileUploadService, PaymentsFileUploadQueryService paymentsFileUploadQueryService) {
        this.paymentsFileUploadService = paymentsFileUploadService;
        this.paymentsFileUploadQueryService = paymentsFileUploadQueryService;
    }

    /**
     * {@code POST  /payments-file-uploads} : Create a new paymentsFileUpload.
     *
     * @param paymentsFileUploadDTO the paymentsFileUploadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentsFileUploadDTO, or with status {@code 400 (Bad Request)} if the paymentsFileUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments-file-uploads")
    public ResponseEntity<PaymentsFileUploadDTO> createPaymentsFileUpload(@Valid @RequestBody PaymentsFileUploadDTO paymentsFileUploadDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentsFileUpload : {}", paymentsFileUploadDTO);
        if (paymentsFileUploadDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentsFileUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentsFileUploadDTO result = paymentsFileUploadService.save(paymentsFileUploadDTO);
        return ResponseEntity.created(new URI("/api/payments-file-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payments-file-uploads} : Updates an existing paymentsFileUpload.
     *
     * @param paymentsFileUploadDTO the paymentsFileUploadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentsFileUploadDTO,
     * or with status {@code 400 (Bad Request)} if the paymentsFileUploadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentsFileUploadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments-file-uploads")
    public ResponseEntity<PaymentsFileUploadDTO> updatePaymentsFileUpload(@Valid @RequestBody PaymentsFileUploadDTO paymentsFileUploadDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentsFileUpload : {}", paymentsFileUploadDTO);
        if (paymentsFileUploadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentsFileUploadDTO result = paymentsFileUploadService.save(paymentsFileUploadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentsFileUploadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payments-file-uploads} : get all the paymentsFileUploads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentsFileUploads in body.
     */
    @GetMapping("/payments-file-uploads")
    public ResponseEntity<List<PaymentsFileUploadDTO>> getAllPaymentsFileUploads(PaymentsFileUploadCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaymentsFileUploads by criteria: {}", criteria);
        Page<PaymentsFileUploadDTO> page = paymentsFileUploadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payments-file-uploads/count} : count all the paymentsFileUploads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payments-file-uploads/count")
    public ResponseEntity<Long> countPaymentsFileUploads(PaymentsFileUploadCriteria criteria) {
        log.debug("REST request to count PaymentsFileUploads by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentsFileUploadQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payments-file-uploads/:id} : get the "id" paymentsFileUpload.
     *
     * @param id the id of the paymentsFileUploadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentsFileUploadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments-file-uploads/{id}")
    public ResponseEntity<PaymentsFileUploadDTO> getPaymentsFileUpload(@PathVariable Long id) {
        log.debug("REST request to get PaymentsFileUpload : {}", id);
        Optional<PaymentsFileUploadDTO> paymentsFileUploadDTO = paymentsFileUploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentsFileUploadDTO);
    }

    /**
     * {@code DELETE  /payments-file-uploads/:id} : delete the "id" paymentsFileUpload.
     *
     * @param id the id of the paymentsFileUploadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments-file-uploads/{id}")
    public ResponseEntity<Void> deletePaymentsFileUpload(@PathVariable Long id) {
        log.debug("REST request to delete PaymentsFileUpload : {}", id);
        paymentsFileUploadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/payments-file-uploads?query=:query} : search for the paymentsFileUpload corresponding
     * to the query.
     *
     * @param query the query of the paymentsFileUpload search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/payments-file-uploads")
    public ResponseEntity<List<PaymentsFileUploadDTO>> searchPaymentsFileUploads(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PaymentsFileUploads for query {}", query);
        Page<PaymentsFileUploadDTO> page = paymentsFileUploadService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
