package io.github.erp.web.rest;

import io.github.erp.service.PaymentsMessageTokenService;
import io.github.erp.web.rest.errors.BadRequestAlertException;
import io.github.erp.service.dto.PaymentsMessageTokenDTO;
import io.github.erp.service.dto.PaymentsMessageTokenCriteria;
import io.github.erp.service.PaymentsMessageTokenQueryService;

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
 * REST controller for managing {@link io.github.erp.domain.PaymentsMessageToken}.
 */
@RestController
@RequestMapping("/api")
public class PaymentsMessageTokenResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsMessageTokenResource.class);

    private static final String ENTITY_NAME = "paymentsPaymentsMessageToken";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentsMessageTokenService paymentsMessageTokenService;

    private final PaymentsMessageTokenQueryService paymentsMessageTokenQueryService;

    public PaymentsMessageTokenResource(PaymentsMessageTokenService paymentsMessageTokenService, PaymentsMessageTokenQueryService paymentsMessageTokenQueryService) {
        this.paymentsMessageTokenService = paymentsMessageTokenService;
        this.paymentsMessageTokenQueryService = paymentsMessageTokenQueryService;
    }

    /**
     * {@code POST  /payments-message-tokens} : Create a new paymentsMessageToken.
     *
     * @param paymentsMessageTokenDTO the paymentsMessageTokenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentsMessageTokenDTO, or with status {@code 400 (Bad Request)} if the paymentsMessageToken has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments-message-tokens")
    public ResponseEntity<PaymentsMessageTokenDTO> createPaymentsMessageToken(@Valid @RequestBody PaymentsMessageTokenDTO paymentsMessageTokenDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentsMessageToken : {}", paymentsMessageTokenDTO);
        if (paymentsMessageTokenDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentsMessageToken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentsMessageTokenDTO result = paymentsMessageTokenService.save(paymentsMessageTokenDTO);
        return ResponseEntity.created(new URI("/api/payments-message-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payments-message-tokens} : Updates an existing paymentsMessageToken.
     *
     * @param paymentsMessageTokenDTO the paymentsMessageTokenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentsMessageTokenDTO,
     * or with status {@code 400 (Bad Request)} if the paymentsMessageTokenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentsMessageTokenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments-message-tokens")
    public ResponseEntity<PaymentsMessageTokenDTO> updatePaymentsMessageToken(@Valid @RequestBody PaymentsMessageTokenDTO paymentsMessageTokenDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentsMessageToken : {}", paymentsMessageTokenDTO);
        if (paymentsMessageTokenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentsMessageTokenDTO result = paymentsMessageTokenService.save(paymentsMessageTokenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentsMessageTokenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payments-message-tokens} : get all the paymentsMessageTokens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentsMessageTokens in body.
     */
    @GetMapping("/payments-message-tokens")
    public ResponseEntity<List<PaymentsMessageTokenDTO>> getAllPaymentsMessageTokens(PaymentsMessageTokenCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaymentsMessageTokens by criteria: {}", criteria);
        Page<PaymentsMessageTokenDTO> page = paymentsMessageTokenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payments-message-tokens/count} : count all the paymentsMessageTokens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payments-message-tokens/count")
    public ResponseEntity<Long> countPaymentsMessageTokens(PaymentsMessageTokenCriteria criteria) {
        log.debug("REST request to count PaymentsMessageTokens by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentsMessageTokenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payments-message-tokens/:id} : get the "id" paymentsMessageToken.
     *
     * @param id the id of the paymentsMessageTokenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentsMessageTokenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments-message-tokens/{id}")
    public ResponseEntity<PaymentsMessageTokenDTO> getPaymentsMessageToken(@PathVariable Long id) {
        log.debug("REST request to get PaymentsMessageToken : {}", id);
        Optional<PaymentsMessageTokenDTO> paymentsMessageTokenDTO = paymentsMessageTokenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentsMessageTokenDTO);
    }

    /**
     * {@code DELETE  /payments-message-tokens/:id} : delete the "id" paymentsMessageToken.
     *
     * @param id the id of the paymentsMessageTokenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments-message-tokens/{id}")
    public ResponseEntity<Void> deletePaymentsMessageToken(@PathVariable Long id) {
        log.debug("REST request to delete PaymentsMessageToken : {}", id);
        paymentsMessageTokenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/payments-message-tokens?query=:query} : search for the paymentsMessageToken corresponding
     * to the query.
     *
     * @param query the query of the paymentsMessageToken search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/payments-message-tokens")
    public ResponseEntity<List<PaymentsMessageTokenDTO>> searchPaymentsMessageTokens(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PaymentsMessageTokens for query {}", query);
        Page<PaymentsMessageTokenDTO> page = paymentsMessageTokenService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
