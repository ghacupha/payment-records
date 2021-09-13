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

import io.github.erp.PaymentRecordsApp;
import io.github.erp.domain.PaymentLabel;
import io.github.erp.domain.Payment;
import io.github.erp.repository.PaymentLabelRepository;
import io.github.erp.repository.search.PaymentLabelSearchRepository;
import io.github.erp.service.PaymentLabelService;
import io.github.erp.service.dto.PaymentLabelDTO;
import io.github.erp.service.mapper.PaymentLabelMapper;
import io.github.erp.service.dto.PaymentLabelCriteria;
import io.github.erp.service.PaymentLabelQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaymentLabelResource} REST controller.
 */
@SpringBootTest(classes = PaymentRecordsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentLabelResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SCHEDULE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SCHEDULE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PaymentLabelRepository paymentLabelRepository;

    @Autowired
    private PaymentLabelMapper paymentLabelMapper;

    @Autowired
    private PaymentLabelService paymentLabelService;

    /**
     * This repository is mocked in the io.github.erp.repository.search test package.
     *
     * @see io.github.erp.repository.search.PaymentLabelSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentLabelSearchRepository mockPaymentLabelSearchRepository;

    @Autowired
    private PaymentLabelQueryService paymentLabelQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentLabelMockMvc;

    private PaymentLabel paymentLabel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentLabel createEntity(EntityManager em) {
        PaymentLabel paymentLabel = new PaymentLabel()
            .description(DEFAULT_DESCRIPTION)
            .comments(DEFAULT_COMMENTS)
            .schedule(DEFAULT_SCHEDULE);
        return paymentLabel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentLabel createUpdatedEntity(EntityManager em) {
        PaymentLabel paymentLabel = new PaymentLabel()
            .description(UPDATED_DESCRIPTION)
            .comments(UPDATED_COMMENTS)
            .schedule(UPDATED_SCHEDULE);
        return paymentLabel;
    }

    @BeforeEach
    public void initTest() {
        paymentLabel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentLabel() throws Exception {
        int databaseSizeBeforeCreate = paymentLabelRepository.findAll().size();
        // Create the PaymentLabel
        PaymentLabelDTO paymentLabelDTO = paymentLabelMapper.toDto(paymentLabel);
        restPaymentLabelMockMvc.perform(post("/api/payment-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLabelDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentLabel in the database
        List<PaymentLabel> paymentLabelList = paymentLabelRepository.findAll();
        assertThat(paymentLabelList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentLabel testPaymentLabel = paymentLabelList.get(paymentLabelList.size() - 1);
        assertThat(testPaymentLabel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentLabel.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPaymentLabel.getSchedule()).isEqualTo(DEFAULT_SCHEDULE);

        // Validate the PaymentLabel in Elasticsearch
        verify(mockPaymentLabelSearchRepository, times(1)).save(testPaymentLabel);
    }

    @Test
    @Transactional
    public void createPaymentLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentLabelRepository.findAll().size();

        // Create the PaymentLabel with an existing ID
        paymentLabel.setId(1L);
        PaymentLabelDTO paymentLabelDTO = paymentLabelMapper.toDto(paymentLabel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentLabelMockMvc.perform(post("/api/payment-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentLabel in the database
        List<PaymentLabel> paymentLabelList = paymentLabelRepository.findAll();
        assertThat(paymentLabelList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentLabel in Elasticsearch
        verify(mockPaymentLabelSearchRepository, times(0)).save(paymentLabel);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentLabelRepository.findAll().size();
        // set the field null
        paymentLabel.setDescription(null);

        // Create the PaymentLabel, which fails.
        PaymentLabelDTO paymentLabelDTO = paymentLabelMapper.toDto(paymentLabel);


        restPaymentLabelMockMvc.perform(post("/api/payment-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLabelDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentLabel> paymentLabelList = paymentLabelRepository.findAll();
        assertThat(paymentLabelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentLabels() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList
        restPaymentLabelMockMvc.perform(get("/api/payment-labels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].schedule").value(hasItem(DEFAULT_SCHEDULE.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentLabel() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get the paymentLabel
        restPaymentLabelMockMvc.perform(get("/api/payment-labels/{id}", paymentLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentLabel.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.schedule").value(DEFAULT_SCHEDULE.toString()));
    }


    @Test
    @Transactional
    public void getPaymentLabelsByIdFiltering() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        Long id = paymentLabel.getId();

        defaultPaymentLabelShouldBeFound("id.equals=" + id);
        defaultPaymentLabelShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentLabelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentLabelShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentLabelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentLabelShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentLabelsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where description equals to DEFAULT_DESCRIPTION
        defaultPaymentLabelShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the paymentLabelList where description equals to UPDATED_DESCRIPTION
        defaultPaymentLabelShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where description not equals to DEFAULT_DESCRIPTION
        defaultPaymentLabelShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the paymentLabelList where description not equals to UPDATED_DESCRIPTION
        defaultPaymentLabelShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPaymentLabelShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the paymentLabelList where description equals to UPDATED_DESCRIPTION
        defaultPaymentLabelShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where description is not null
        defaultPaymentLabelShouldBeFound("description.specified=true");

        // Get all the paymentLabelList where description is null
        defaultPaymentLabelShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentLabelsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where description contains DEFAULT_DESCRIPTION
        defaultPaymentLabelShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the paymentLabelList where description contains UPDATED_DESCRIPTION
        defaultPaymentLabelShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where description does not contain DEFAULT_DESCRIPTION
        defaultPaymentLabelShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the paymentLabelList where description does not contain UPDATED_DESCRIPTION
        defaultPaymentLabelShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPaymentLabelsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where comments equals to DEFAULT_COMMENTS
        defaultPaymentLabelShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the paymentLabelList where comments equals to UPDATED_COMMENTS
        defaultPaymentLabelShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where comments not equals to DEFAULT_COMMENTS
        defaultPaymentLabelShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the paymentLabelList where comments not equals to UPDATED_COMMENTS
        defaultPaymentLabelShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultPaymentLabelShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the paymentLabelList where comments equals to UPDATED_COMMENTS
        defaultPaymentLabelShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where comments is not null
        defaultPaymentLabelShouldBeFound("comments.specified=true");

        // Get all the paymentLabelList where comments is null
        defaultPaymentLabelShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentLabelsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where comments contains DEFAULT_COMMENTS
        defaultPaymentLabelShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the paymentLabelList where comments contains UPDATED_COMMENTS
        defaultPaymentLabelShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where comments does not contain DEFAULT_COMMENTS
        defaultPaymentLabelShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the paymentLabelList where comments does not contain UPDATED_COMMENTS
        defaultPaymentLabelShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllPaymentLabelsByScheduleIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where schedule equals to DEFAULT_SCHEDULE
        defaultPaymentLabelShouldBeFound("schedule.equals=" + DEFAULT_SCHEDULE);

        // Get all the paymentLabelList where schedule equals to UPDATED_SCHEDULE
        defaultPaymentLabelShouldNotBeFound("schedule.equals=" + UPDATED_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByScheduleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where schedule not equals to DEFAULT_SCHEDULE
        defaultPaymentLabelShouldNotBeFound("schedule.notEquals=" + DEFAULT_SCHEDULE);

        // Get all the paymentLabelList where schedule not equals to UPDATED_SCHEDULE
        defaultPaymentLabelShouldBeFound("schedule.notEquals=" + UPDATED_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByScheduleIsInShouldWork() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where schedule in DEFAULT_SCHEDULE or UPDATED_SCHEDULE
        defaultPaymentLabelShouldBeFound("schedule.in=" + DEFAULT_SCHEDULE + "," + UPDATED_SCHEDULE);

        // Get all the paymentLabelList where schedule equals to UPDATED_SCHEDULE
        defaultPaymentLabelShouldNotBeFound("schedule.in=" + UPDATED_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByScheduleIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where schedule is not null
        defaultPaymentLabelShouldBeFound("schedule.specified=true");

        // Get all the paymentLabelList where schedule is null
        defaultPaymentLabelShouldNotBeFound("schedule.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByScheduleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where schedule is greater than or equal to DEFAULT_SCHEDULE
        defaultPaymentLabelShouldBeFound("schedule.greaterThanOrEqual=" + DEFAULT_SCHEDULE);

        // Get all the paymentLabelList where schedule is greater than or equal to UPDATED_SCHEDULE
        defaultPaymentLabelShouldNotBeFound("schedule.greaterThanOrEqual=" + UPDATED_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByScheduleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where schedule is less than or equal to DEFAULT_SCHEDULE
        defaultPaymentLabelShouldBeFound("schedule.lessThanOrEqual=" + DEFAULT_SCHEDULE);

        // Get all the paymentLabelList where schedule is less than or equal to SMALLER_SCHEDULE
        defaultPaymentLabelShouldNotBeFound("schedule.lessThanOrEqual=" + SMALLER_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByScheduleIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where schedule is less than DEFAULT_SCHEDULE
        defaultPaymentLabelShouldNotBeFound("schedule.lessThan=" + DEFAULT_SCHEDULE);

        // Get all the paymentLabelList where schedule is less than UPDATED_SCHEDULE
        defaultPaymentLabelShouldBeFound("schedule.lessThan=" + UPDATED_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllPaymentLabelsByScheduleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        // Get all the paymentLabelList where schedule is greater than DEFAULT_SCHEDULE
        defaultPaymentLabelShouldNotBeFound("schedule.greaterThan=" + DEFAULT_SCHEDULE);

        // Get all the paymentLabelList where schedule is greater than SMALLER_SCHEDULE
        defaultPaymentLabelShouldBeFound("schedule.greaterThan=" + SMALLER_SCHEDULE);
    }


    @Test
    @Transactional
    public void getAllPaymentLabelsByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);
        Payment payment = PaymentResourceIT.createEntity(em);
        em.persist(payment);
        em.flush();
        paymentLabel.addPayment(payment);
        paymentLabelRepository.saveAndFlush(paymentLabel);
        Long paymentId = payment.getId();

        // Get all the paymentLabelList where payment equals to paymentId
        defaultPaymentLabelShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the paymentLabelList where payment equals to paymentId + 1
        defaultPaymentLabelShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentLabelShouldBeFound(String filter) throws Exception {
        restPaymentLabelMockMvc.perform(get("/api/payment-labels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].schedule").value(hasItem(DEFAULT_SCHEDULE.toString())));

        // Check, that the count call also returns 1
        restPaymentLabelMockMvc.perform(get("/api/payment-labels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentLabelShouldNotBeFound(String filter) throws Exception {
        restPaymentLabelMockMvc.perform(get("/api/payment-labels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentLabelMockMvc.perform(get("/api/payment-labels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentLabel() throws Exception {
        // Get the paymentLabel
        restPaymentLabelMockMvc.perform(get("/api/payment-labels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentLabel() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        int databaseSizeBeforeUpdate = paymentLabelRepository.findAll().size();

        // Update the paymentLabel
        PaymentLabel updatedPaymentLabel = paymentLabelRepository.findById(paymentLabel.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentLabel are not directly saved in db
        em.detach(updatedPaymentLabel);
        updatedPaymentLabel
            .description(UPDATED_DESCRIPTION)
            .comments(UPDATED_COMMENTS)
            .schedule(UPDATED_SCHEDULE);
        PaymentLabelDTO paymentLabelDTO = paymentLabelMapper.toDto(updatedPaymentLabel);

        restPaymentLabelMockMvc.perform(put("/api/payment-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLabelDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentLabel in the database
        List<PaymentLabel> paymentLabelList = paymentLabelRepository.findAll();
        assertThat(paymentLabelList).hasSize(databaseSizeBeforeUpdate);
        PaymentLabel testPaymentLabel = paymentLabelList.get(paymentLabelList.size() - 1);
        assertThat(testPaymentLabel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentLabel.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPaymentLabel.getSchedule()).isEqualTo(UPDATED_SCHEDULE);

        // Validate the PaymentLabel in Elasticsearch
        verify(mockPaymentLabelSearchRepository, times(1)).save(testPaymentLabel);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentLabel() throws Exception {
        int databaseSizeBeforeUpdate = paymentLabelRepository.findAll().size();

        // Create the PaymentLabel
        PaymentLabelDTO paymentLabelDTO = paymentLabelMapper.toDto(paymentLabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentLabelMockMvc.perform(put("/api/payment-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentLabel in the database
        List<PaymentLabel> paymentLabelList = paymentLabelRepository.findAll();
        assertThat(paymentLabelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentLabel in Elasticsearch
        verify(mockPaymentLabelSearchRepository, times(0)).save(paymentLabel);
    }

    @Test
    @Transactional
    public void deletePaymentLabel() throws Exception {
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);

        int databaseSizeBeforeDelete = paymentLabelRepository.findAll().size();

        // Delete the paymentLabel
        restPaymentLabelMockMvc.perform(delete("/api/payment-labels/{id}", paymentLabel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentLabel> paymentLabelList = paymentLabelRepository.findAll();
        assertThat(paymentLabelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentLabel in Elasticsearch
        verify(mockPaymentLabelSearchRepository, times(1)).deleteById(paymentLabel.getId());
    }

    @Test
    @Transactional
    public void searchPaymentLabel() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        paymentLabelRepository.saveAndFlush(paymentLabel);
        when(mockPaymentLabelSearchRepository.search(queryStringQuery("id:" + paymentLabel.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentLabel), PageRequest.of(0, 1), 1));

        // Search the paymentLabel
        restPaymentLabelMockMvc.perform(get("/api/_search/payment-labels?query=id:" + paymentLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].schedule").value(hasItem(DEFAULT_SCHEDULE.toString())));
    }
}
