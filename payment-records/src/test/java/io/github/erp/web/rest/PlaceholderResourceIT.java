package io.github.erp.web.rest;

import io.github.erp.PaymentRecordsApp;
import io.github.erp.domain.Placeholder;
import io.github.erp.domain.CurrencyTable;
import io.github.erp.domain.Payment;
import io.github.erp.domain.PaymentsFileType;
import io.github.erp.domain.PaymentsFileUpload;
import io.github.erp.repository.PlaceholderRepository;
import io.github.erp.repository.search.PlaceholderSearchRepository;
import io.github.erp.service.PlaceholderService;
import io.github.erp.service.dto.PlaceholderDTO;
import io.github.erp.service.mapper.PlaceholderMapper;
import io.github.erp.service.dto.PlaceholderCriteria;
import io.github.erp.service.PlaceholderQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlaceholderResource} REST controller.
 */
@SpringBootTest(classes = PaymentRecordsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlaceholderResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private PlaceholderRepository placeholderRepository;

    @Autowired
    private PlaceholderMapper placeholderMapper;

    @Autowired
    private PlaceholderService placeholderService;

    /**
     * This repository is mocked in the io.github.erp.repository.search test package.
     *
     * @see io.github.erp.repository.search.PlaceholderSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlaceholderSearchRepository mockPlaceholderSearchRepository;

    @Autowired
    private PlaceholderQueryService placeholderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaceholderMockMvc;

    private Placeholder placeholder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Placeholder createEntity(EntityManager em) {
        Placeholder placeholder = new Placeholder()
            .description(DEFAULT_DESCRIPTION)
            .token(DEFAULT_TOKEN);
        return placeholder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Placeholder createUpdatedEntity(EntityManager em) {
        Placeholder placeholder = new Placeholder()
            .description(UPDATED_DESCRIPTION)
            .token(UPDATED_TOKEN);
        return placeholder;
    }

    @BeforeEach
    public void initTest() {
        placeholder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaceholder() throws Exception {
        int databaseSizeBeforeCreate = placeholderRepository.findAll().size();
        // Create the Placeholder
        PlaceholderDTO placeholderDTO = placeholderMapper.toDto(placeholder);
        restPlaceholderMockMvc.perform(post("/api/placeholders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(placeholderDTO)))
            .andExpect(status().isCreated());

        // Validate the Placeholder in the database
        List<Placeholder> placeholderList = placeholderRepository.findAll();
        assertThat(placeholderList).hasSize(databaseSizeBeforeCreate + 1);
        Placeholder testPlaceholder = placeholderList.get(placeholderList.size() - 1);
        assertThat(testPlaceholder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlaceholder.getToken()).isEqualTo(DEFAULT_TOKEN);

        // Validate the Placeholder in Elasticsearch
        verify(mockPlaceholderSearchRepository, times(1)).save(testPlaceholder);
    }

    @Test
    @Transactional
    public void createPlaceholderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = placeholderRepository.findAll().size();

        // Create the Placeholder with an existing ID
        placeholder.setId(1L);
        PlaceholderDTO placeholderDTO = placeholderMapper.toDto(placeholder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaceholderMockMvc.perform(post("/api/placeholders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(placeholderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Placeholder in the database
        List<Placeholder> placeholderList = placeholderRepository.findAll();
        assertThat(placeholderList).hasSize(databaseSizeBeforeCreate);

        // Validate the Placeholder in Elasticsearch
        verify(mockPlaceholderSearchRepository, times(0)).save(placeholder);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeholderRepository.findAll().size();
        // set the field null
        placeholder.setDescription(null);

        // Create the Placeholder, which fails.
        PlaceholderDTO placeholderDTO = placeholderMapper.toDto(placeholder);


        restPlaceholderMockMvc.perform(post("/api/placeholders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(placeholderDTO)))
            .andExpect(status().isBadRequest());

        List<Placeholder> placeholderList = placeholderRepository.findAll();
        assertThat(placeholderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlaceholders() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList
        restPlaceholderMockMvc.perform(get("/api/placeholders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(placeholder.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)));
    }
    
    @Test
    @Transactional
    public void getPlaceholder() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get the placeholder
        restPlaceholderMockMvc.perform(get("/api/placeholders/{id}", placeholder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(placeholder.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN));
    }


    @Test
    @Transactional
    public void getPlaceholdersByIdFiltering() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        Long id = placeholder.getId();

        defaultPlaceholderShouldBeFound("id.equals=" + id);
        defaultPlaceholderShouldNotBeFound("id.notEquals=" + id);

        defaultPlaceholderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlaceholderShouldNotBeFound("id.greaterThan=" + id);

        defaultPlaceholderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlaceholderShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlaceholdersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where description equals to DEFAULT_DESCRIPTION
        defaultPlaceholderShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the placeholderList where description equals to UPDATED_DESCRIPTION
        defaultPlaceholderShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlaceholdersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where description not equals to DEFAULT_DESCRIPTION
        defaultPlaceholderShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the placeholderList where description not equals to UPDATED_DESCRIPTION
        defaultPlaceholderShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlaceholdersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPlaceholderShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the placeholderList where description equals to UPDATED_DESCRIPTION
        defaultPlaceholderShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlaceholdersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where description is not null
        defaultPlaceholderShouldBeFound("description.specified=true");

        // Get all the placeholderList where description is null
        defaultPlaceholderShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlaceholdersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where description contains DEFAULT_DESCRIPTION
        defaultPlaceholderShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the placeholderList where description contains UPDATED_DESCRIPTION
        defaultPlaceholderShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlaceholdersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where description does not contain DEFAULT_DESCRIPTION
        defaultPlaceholderShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the placeholderList where description does not contain UPDATED_DESCRIPTION
        defaultPlaceholderShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPlaceholdersByTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where token equals to DEFAULT_TOKEN
        defaultPlaceholderShouldBeFound("token.equals=" + DEFAULT_TOKEN);

        // Get all the placeholderList where token equals to UPDATED_TOKEN
        defaultPlaceholderShouldNotBeFound("token.equals=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void getAllPlaceholdersByTokenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where token not equals to DEFAULT_TOKEN
        defaultPlaceholderShouldNotBeFound("token.notEquals=" + DEFAULT_TOKEN);

        // Get all the placeholderList where token not equals to UPDATED_TOKEN
        defaultPlaceholderShouldBeFound("token.notEquals=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void getAllPlaceholdersByTokenIsInShouldWork() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where token in DEFAULT_TOKEN or UPDATED_TOKEN
        defaultPlaceholderShouldBeFound("token.in=" + DEFAULT_TOKEN + "," + UPDATED_TOKEN);

        // Get all the placeholderList where token equals to UPDATED_TOKEN
        defaultPlaceholderShouldNotBeFound("token.in=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void getAllPlaceholdersByTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where token is not null
        defaultPlaceholderShouldBeFound("token.specified=true");

        // Get all the placeholderList where token is null
        defaultPlaceholderShouldNotBeFound("token.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlaceholdersByTokenContainsSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where token contains DEFAULT_TOKEN
        defaultPlaceholderShouldBeFound("token.contains=" + DEFAULT_TOKEN);

        // Get all the placeholderList where token contains UPDATED_TOKEN
        defaultPlaceholderShouldNotBeFound("token.contains=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void getAllPlaceholdersByTokenNotContainsSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        // Get all the placeholderList where token does not contain DEFAULT_TOKEN
        defaultPlaceholderShouldNotBeFound("token.doesNotContain=" + DEFAULT_TOKEN);

        // Get all the placeholderList where token does not contain UPDATED_TOKEN
        defaultPlaceholderShouldBeFound("token.doesNotContain=" + UPDATED_TOKEN);
    }


    @Test
    @Transactional
    public void getAllPlaceholdersByCurrencyTableIsEqualToSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);
        CurrencyTable currencyTable = CurrencyTableResourceIT.createEntity(em);
        em.persist(currencyTable);
        em.flush();
        placeholder.addCurrencyTable(currencyTable);
        placeholderRepository.saveAndFlush(placeholder);
        Long currencyTableId = currencyTable.getId();

        // Get all the placeholderList where currencyTable equals to currencyTableId
        defaultPlaceholderShouldBeFound("currencyTableId.equals=" + currencyTableId);

        // Get all the placeholderList where currencyTable equals to currencyTableId + 1
        defaultPlaceholderShouldNotBeFound("currencyTableId.equals=" + (currencyTableId + 1));
    }


    @Test
    @Transactional
    public void getAllPlaceholdersByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);
        Payment payment = PaymentResourceIT.createEntity(em);
        em.persist(payment);
        em.flush();
        placeholder.addPayment(payment);
        placeholderRepository.saveAndFlush(placeholder);
        Long paymentId = payment.getId();

        // Get all the placeholderList where payment equals to paymentId
        defaultPlaceholderShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the placeholderList where payment equals to paymentId + 1
        defaultPlaceholderShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }


    @Test
    @Transactional
    public void getAllPlaceholdersByPaymentsFileTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);
        PaymentsFileType paymentsFileType = PaymentsFileTypeResourceIT.createEntity(em);
        em.persist(paymentsFileType);
        em.flush();
        placeholder.addPaymentsFileType(paymentsFileType);
        placeholderRepository.saveAndFlush(placeholder);
        Long paymentsFileTypeId = paymentsFileType.getId();

        // Get all the placeholderList where paymentsFileType equals to paymentsFileTypeId
        defaultPlaceholderShouldBeFound("paymentsFileTypeId.equals=" + paymentsFileTypeId);

        // Get all the placeholderList where paymentsFileType equals to paymentsFileTypeId + 1
        defaultPlaceholderShouldNotBeFound("paymentsFileTypeId.equals=" + (paymentsFileTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllPlaceholdersByPaymentsFileUploadIsEqualToSomething() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);
        PaymentsFileUpload paymentsFileUpload = PaymentsFileUploadResourceIT.createEntity(em);
        em.persist(paymentsFileUpload);
        em.flush();
        placeholder.addPaymentsFileUpload(paymentsFileUpload);
        placeholderRepository.saveAndFlush(placeholder);
        Long paymentsFileUploadId = paymentsFileUpload.getId();

        // Get all the placeholderList where paymentsFileUpload equals to paymentsFileUploadId
        defaultPlaceholderShouldBeFound("paymentsFileUploadId.equals=" + paymentsFileUploadId);

        // Get all the placeholderList where paymentsFileUpload equals to paymentsFileUploadId + 1
        defaultPlaceholderShouldNotBeFound("paymentsFileUploadId.equals=" + (paymentsFileUploadId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlaceholderShouldBeFound(String filter) throws Exception {
        restPlaceholderMockMvc.perform(get("/api/placeholders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(placeholder.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)));

        // Check, that the count call also returns 1
        restPlaceholderMockMvc.perform(get("/api/placeholders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlaceholderShouldNotBeFound(String filter) throws Exception {
        restPlaceholderMockMvc.perform(get("/api/placeholders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlaceholderMockMvc.perform(get("/api/placeholders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlaceholder() throws Exception {
        // Get the placeholder
        restPlaceholderMockMvc.perform(get("/api/placeholders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaceholder() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        int databaseSizeBeforeUpdate = placeholderRepository.findAll().size();

        // Update the placeholder
        Placeholder updatedPlaceholder = placeholderRepository.findById(placeholder.getId()).get();
        // Disconnect from session so that the updates on updatedPlaceholder are not directly saved in db
        em.detach(updatedPlaceholder);
        updatedPlaceholder
            .description(UPDATED_DESCRIPTION)
            .token(UPDATED_TOKEN);
        PlaceholderDTO placeholderDTO = placeholderMapper.toDto(updatedPlaceholder);

        restPlaceholderMockMvc.perform(put("/api/placeholders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(placeholderDTO)))
            .andExpect(status().isOk());

        // Validate the Placeholder in the database
        List<Placeholder> placeholderList = placeholderRepository.findAll();
        assertThat(placeholderList).hasSize(databaseSizeBeforeUpdate);
        Placeholder testPlaceholder = placeholderList.get(placeholderList.size() - 1);
        assertThat(testPlaceholder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlaceholder.getToken()).isEqualTo(UPDATED_TOKEN);

        // Validate the Placeholder in Elasticsearch
        verify(mockPlaceholderSearchRepository, times(1)).save(testPlaceholder);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaceholder() throws Exception {
        int databaseSizeBeforeUpdate = placeholderRepository.findAll().size();

        // Create the Placeholder
        PlaceholderDTO placeholderDTO = placeholderMapper.toDto(placeholder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaceholderMockMvc.perform(put("/api/placeholders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(placeholderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Placeholder in the database
        List<Placeholder> placeholderList = placeholderRepository.findAll();
        assertThat(placeholderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Placeholder in Elasticsearch
        verify(mockPlaceholderSearchRepository, times(0)).save(placeholder);
    }

    @Test
    @Transactional
    public void deletePlaceholder() throws Exception {
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);

        int databaseSizeBeforeDelete = placeholderRepository.findAll().size();

        // Delete the placeholder
        restPlaceholderMockMvc.perform(delete("/api/placeholders/{id}", placeholder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Placeholder> placeholderList = placeholderRepository.findAll();
        assertThat(placeholderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Placeholder in Elasticsearch
        verify(mockPlaceholderSearchRepository, times(1)).deleteById(placeholder.getId());
    }

    @Test
    @Transactional
    public void searchPlaceholder() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        placeholderRepository.saveAndFlush(placeholder);
        when(mockPlaceholderSearchRepository.search(queryStringQuery("id:" + placeholder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(placeholder), PageRequest.of(0, 1), 1));

        // Search the placeholder
        restPlaceholderMockMvc.perform(get("/api/_search/placeholders?query=id:" + placeholder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(placeholder.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)));
    }
}
