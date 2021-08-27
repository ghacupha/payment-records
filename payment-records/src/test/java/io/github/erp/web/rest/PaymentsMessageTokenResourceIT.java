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
import io.github.erp.domain.PaymentsMessageToken;
import io.github.erp.repository.PaymentsMessageTokenRepository;
import io.github.erp.repository.search.PaymentsMessageTokenSearchRepository;
import io.github.erp.service.PaymentsMessageTokenService;
import io.github.erp.service.dto.PaymentsMessageTokenDTO;
import io.github.erp.service.mapper.PaymentsMessageTokenMapper;
import io.github.erp.service.dto.PaymentsMessageTokenCriteria;
import io.github.erp.service.PaymentsMessageTokenQueryService;

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
 * Integration tests for the {@link PaymentsMessageTokenResource} REST controller.
 */
@SpringBootTest(classes = PaymentRecordsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentsMessageTokenResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_TIME_SENT = 1L;
    private static final Long UPDATED_TIME_SENT = 2L;
    private static final Long SMALLER_TIME_SENT = 1L - 1L;

    private static final String DEFAULT_TOKEN_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECEIVED = false;
    private static final Boolean UPDATED_RECEIVED = true;

    private static final Boolean DEFAULT_ACTIONED = false;
    private static final Boolean UPDATED_ACTIONED = true;

    private static final Boolean DEFAULT_CONTENT_FULLY_ENQUEUED = false;
    private static final Boolean UPDATED_CONTENT_FULLY_ENQUEUED = true;

    @Autowired
    private PaymentsMessageTokenRepository paymentsMessageTokenRepository;

    @Autowired
    private PaymentsMessageTokenMapper paymentsMessageTokenMapper;

    @Autowired
    private PaymentsMessageTokenService paymentsMessageTokenService;

    /**
     * This repository is mocked in the io.github.erp.repository.search test package.
     *
     * @see io.github.erp.repository.search.PaymentsMessageTokenSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentsMessageTokenSearchRepository mockPaymentsMessageTokenSearchRepository;

    @Autowired
    private PaymentsMessageTokenQueryService paymentsMessageTokenQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentsMessageTokenMockMvc;

    private PaymentsMessageToken paymentsMessageToken;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentsMessageToken createEntity(EntityManager em) {
        PaymentsMessageToken paymentsMessageToken = new PaymentsMessageToken()
            .description(DEFAULT_DESCRIPTION)
            .timeSent(DEFAULT_TIME_SENT)
            .tokenValue(DEFAULT_TOKEN_VALUE)
            .received(DEFAULT_RECEIVED)
            .actioned(DEFAULT_ACTIONED)
            .contentFullyEnqueued(DEFAULT_CONTENT_FULLY_ENQUEUED);
        return paymentsMessageToken;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentsMessageToken createUpdatedEntity(EntityManager em) {
        PaymentsMessageToken paymentsMessageToken = new PaymentsMessageToken()
            .description(UPDATED_DESCRIPTION)
            .timeSent(UPDATED_TIME_SENT)
            .tokenValue(UPDATED_TOKEN_VALUE)
            .received(UPDATED_RECEIVED)
            .actioned(UPDATED_ACTIONED)
            .contentFullyEnqueued(UPDATED_CONTENT_FULLY_ENQUEUED);
        return paymentsMessageToken;
    }

    @BeforeEach
    public void initTest() {
        paymentsMessageToken = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentsMessageToken() throws Exception {
        int databaseSizeBeforeCreate = paymentsMessageTokenRepository.findAll().size();
        // Create the PaymentsMessageToken
        PaymentsMessageTokenDTO paymentsMessageTokenDTO = paymentsMessageTokenMapper.toDto(paymentsMessageToken);
        restPaymentsMessageTokenMockMvc.perform(post("/api/payments-message-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsMessageTokenDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentsMessageToken in the database
        List<PaymentsMessageToken> paymentsMessageTokenList = paymentsMessageTokenRepository.findAll();
        assertThat(paymentsMessageTokenList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentsMessageToken testPaymentsMessageToken = paymentsMessageTokenList.get(paymentsMessageTokenList.size() - 1);
        assertThat(testPaymentsMessageToken.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentsMessageToken.getTimeSent()).isEqualTo(DEFAULT_TIME_SENT);
        assertThat(testPaymentsMessageToken.getTokenValue()).isEqualTo(DEFAULT_TOKEN_VALUE);
        assertThat(testPaymentsMessageToken.isReceived()).isEqualTo(DEFAULT_RECEIVED);
        assertThat(testPaymentsMessageToken.isActioned()).isEqualTo(DEFAULT_ACTIONED);
        assertThat(testPaymentsMessageToken.isContentFullyEnqueued()).isEqualTo(DEFAULT_CONTENT_FULLY_ENQUEUED);

        // Validate the PaymentsMessageToken in Elasticsearch
        verify(mockPaymentsMessageTokenSearchRepository, times(1)).save(testPaymentsMessageToken);
    }

    @Test
    @Transactional
    public void createPaymentsMessageTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentsMessageTokenRepository.findAll().size();

        // Create the PaymentsMessageToken with an existing ID
        paymentsMessageToken.setId(1L);
        PaymentsMessageTokenDTO paymentsMessageTokenDTO = paymentsMessageTokenMapper.toDto(paymentsMessageToken);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsMessageTokenMockMvc.perform(post("/api/payments-message-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsMessageTokenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentsMessageToken in the database
        List<PaymentsMessageToken> paymentsMessageTokenList = paymentsMessageTokenRepository.findAll();
        assertThat(paymentsMessageTokenList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentsMessageToken in Elasticsearch
        verify(mockPaymentsMessageTokenSearchRepository, times(0)).save(paymentsMessageToken);
    }


    @Test
    @Transactional
    public void checkTimeSentIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsMessageTokenRepository.findAll().size();
        // set the field null
        paymentsMessageToken.setTimeSent(null);

        // Create the PaymentsMessageToken, which fails.
        PaymentsMessageTokenDTO paymentsMessageTokenDTO = paymentsMessageTokenMapper.toDto(paymentsMessageToken);


        restPaymentsMessageTokenMockMvc.perform(post("/api/payments-message-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsMessageTokenDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentsMessageToken> paymentsMessageTokenList = paymentsMessageTokenRepository.findAll();
        assertThat(paymentsMessageTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsMessageTokenRepository.findAll().size();
        // set the field null
        paymentsMessageToken.setTokenValue(null);

        // Create the PaymentsMessageToken, which fails.
        PaymentsMessageTokenDTO paymentsMessageTokenDTO = paymentsMessageTokenMapper.toDto(paymentsMessageToken);


        restPaymentsMessageTokenMockMvc.perform(post("/api/payments-message-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsMessageTokenDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentsMessageToken> paymentsMessageTokenList = paymentsMessageTokenRepository.findAll();
        assertThat(paymentsMessageTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokens() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList
        restPaymentsMessageTokenMockMvc.perform(get("/api/payments-message-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsMessageToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.intValue())))
            .andExpect(jsonPath("$.[*].tokenValue").value(hasItem(DEFAULT_TOKEN_VALUE)))
            .andExpect(jsonPath("$.[*].received").value(hasItem(DEFAULT_RECEIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].actioned").value(hasItem(DEFAULT_ACTIONED.booleanValue())))
            .andExpect(jsonPath("$.[*].contentFullyEnqueued").value(hasItem(DEFAULT_CONTENT_FULLY_ENQUEUED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPaymentsMessageToken() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get the paymentsMessageToken
        restPaymentsMessageTokenMockMvc.perform(get("/api/payments-message-tokens/{id}", paymentsMessageToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentsMessageToken.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.timeSent").value(DEFAULT_TIME_SENT.intValue()))
            .andExpect(jsonPath("$.tokenValue").value(DEFAULT_TOKEN_VALUE))
            .andExpect(jsonPath("$.received").value(DEFAULT_RECEIVED.booleanValue()))
            .andExpect(jsonPath("$.actioned").value(DEFAULT_ACTIONED.booleanValue()))
            .andExpect(jsonPath("$.contentFullyEnqueued").value(DEFAULT_CONTENT_FULLY_ENQUEUED.booleanValue()));
    }


    @Test
    @Transactional
    public void getPaymentsMessageTokensByIdFiltering() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        Long id = paymentsMessageToken.getId();

        defaultPaymentsMessageTokenShouldBeFound("id.equals=" + id);
        defaultPaymentsMessageTokenShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentsMessageTokenShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentsMessageTokenShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentsMessageTokenShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentsMessageTokenShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where description equals to DEFAULT_DESCRIPTION
        defaultPaymentsMessageTokenShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsMessageTokenList where description equals to UPDATED_DESCRIPTION
        defaultPaymentsMessageTokenShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where description not equals to DEFAULT_DESCRIPTION
        defaultPaymentsMessageTokenShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsMessageTokenList where description not equals to UPDATED_DESCRIPTION
        defaultPaymentsMessageTokenShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPaymentsMessageTokenShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the paymentsMessageTokenList where description equals to UPDATED_DESCRIPTION
        defaultPaymentsMessageTokenShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where description is not null
        defaultPaymentsMessageTokenShouldBeFound("description.specified=true");

        // Get all the paymentsMessageTokenList where description is null
        defaultPaymentsMessageTokenShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsMessageTokensByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where description contains DEFAULT_DESCRIPTION
        defaultPaymentsMessageTokenShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsMessageTokenList where description contains UPDATED_DESCRIPTION
        defaultPaymentsMessageTokenShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where description does not contain DEFAULT_DESCRIPTION
        defaultPaymentsMessageTokenShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsMessageTokenList where description does not contain UPDATED_DESCRIPTION
        defaultPaymentsMessageTokenShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTimeSentIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where timeSent equals to DEFAULT_TIME_SENT
        defaultPaymentsMessageTokenShouldBeFound("timeSent.equals=" + DEFAULT_TIME_SENT);

        // Get all the paymentsMessageTokenList where timeSent equals to UPDATED_TIME_SENT
        defaultPaymentsMessageTokenShouldNotBeFound("timeSent.equals=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTimeSentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where timeSent not equals to DEFAULT_TIME_SENT
        defaultPaymentsMessageTokenShouldNotBeFound("timeSent.notEquals=" + DEFAULT_TIME_SENT);

        // Get all the paymentsMessageTokenList where timeSent not equals to UPDATED_TIME_SENT
        defaultPaymentsMessageTokenShouldBeFound("timeSent.notEquals=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTimeSentIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where timeSent in DEFAULT_TIME_SENT or UPDATED_TIME_SENT
        defaultPaymentsMessageTokenShouldBeFound("timeSent.in=" + DEFAULT_TIME_SENT + "," + UPDATED_TIME_SENT);

        // Get all the paymentsMessageTokenList where timeSent equals to UPDATED_TIME_SENT
        defaultPaymentsMessageTokenShouldNotBeFound("timeSent.in=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTimeSentIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where timeSent is not null
        defaultPaymentsMessageTokenShouldBeFound("timeSent.specified=true");

        // Get all the paymentsMessageTokenList where timeSent is null
        defaultPaymentsMessageTokenShouldNotBeFound("timeSent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTimeSentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where timeSent is greater than or equal to DEFAULT_TIME_SENT
        defaultPaymentsMessageTokenShouldBeFound("timeSent.greaterThanOrEqual=" + DEFAULT_TIME_SENT);

        // Get all the paymentsMessageTokenList where timeSent is greater than or equal to UPDATED_TIME_SENT
        defaultPaymentsMessageTokenShouldNotBeFound("timeSent.greaterThanOrEqual=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTimeSentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where timeSent is less than or equal to DEFAULT_TIME_SENT
        defaultPaymentsMessageTokenShouldBeFound("timeSent.lessThanOrEqual=" + DEFAULT_TIME_SENT);

        // Get all the paymentsMessageTokenList where timeSent is less than or equal to SMALLER_TIME_SENT
        defaultPaymentsMessageTokenShouldNotBeFound("timeSent.lessThanOrEqual=" + SMALLER_TIME_SENT);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTimeSentIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where timeSent is less than DEFAULT_TIME_SENT
        defaultPaymentsMessageTokenShouldNotBeFound("timeSent.lessThan=" + DEFAULT_TIME_SENT);

        // Get all the paymentsMessageTokenList where timeSent is less than UPDATED_TIME_SENT
        defaultPaymentsMessageTokenShouldBeFound("timeSent.lessThan=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTimeSentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where timeSent is greater than DEFAULT_TIME_SENT
        defaultPaymentsMessageTokenShouldNotBeFound("timeSent.greaterThan=" + DEFAULT_TIME_SENT);

        // Get all the paymentsMessageTokenList where timeSent is greater than SMALLER_TIME_SENT
        defaultPaymentsMessageTokenShouldBeFound("timeSent.greaterThan=" + SMALLER_TIME_SENT);
    }


    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTokenValueIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where tokenValue equals to DEFAULT_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldBeFound("tokenValue.equals=" + DEFAULT_TOKEN_VALUE);

        // Get all the paymentsMessageTokenList where tokenValue equals to UPDATED_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldNotBeFound("tokenValue.equals=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTokenValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where tokenValue not equals to DEFAULT_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldNotBeFound("tokenValue.notEquals=" + DEFAULT_TOKEN_VALUE);

        // Get all the paymentsMessageTokenList where tokenValue not equals to UPDATED_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldBeFound("tokenValue.notEquals=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTokenValueIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where tokenValue in DEFAULT_TOKEN_VALUE or UPDATED_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldBeFound("tokenValue.in=" + DEFAULT_TOKEN_VALUE + "," + UPDATED_TOKEN_VALUE);

        // Get all the paymentsMessageTokenList where tokenValue equals to UPDATED_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldNotBeFound("tokenValue.in=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTokenValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where tokenValue is not null
        defaultPaymentsMessageTokenShouldBeFound("tokenValue.specified=true");

        // Get all the paymentsMessageTokenList where tokenValue is null
        defaultPaymentsMessageTokenShouldNotBeFound("tokenValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTokenValueContainsSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where tokenValue contains DEFAULT_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldBeFound("tokenValue.contains=" + DEFAULT_TOKEN_VALUE);

        // Get all the paymentsMessageTokenList where tokenValue contains UPDATED_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldNotBeFound("tokenValue.contains=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByTokenValueNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where tokenValue does not contain DEFAULT_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldNotBeFound("tokenValue.doesNotContain=" + DEFAULT_TOKEN_VALUE);

        // Get all the paymentsMessageTokenList where tokenValue does not contain UPDATED_TOKEN_VALUE
        defaultPaymentsMessageTokenShouldBeFound("tokenValue.doesNotContain=" + UPDATED_TOKEN_VALUE);
    }


    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByReceivedIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where received equals to DEFAULT_RECEIVED
        defaultPaymentsMessageTokenShouldBeFound("received.equals=" + DEFAULT_RECEIVED);

        // Get all the paymentsMessageTokenList where received equals to UPDATED_RECEIVED
        defaultPaymentsMessageTokenShouldNotBeFound("received.equals=" + UPDATED_RECEIVED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByReceivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where received not equals to DEFAULT_RECEIVED
        defaultPaymentsMessageTokenShouldNotBeFound("received.notEquals=" + DEFAULT_RECEIVED);

        // Get all the paymentsMessageTokenList where received not equals to UPDATED_RECEIVED
        defaultPaymentsMessageTokenShouldBeFound("received.notEquals=" + UPDATED_RECEIVED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByReceivedIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where received in DEFAULT_RECEIVED or UPDATED_RECEIVED
        defaultPaymentsMessageTokenShouldBeFound("received.in=" + DEFAULT_RECEIVED + "," + UPDATED_RECEIVED);

        // Get all the paymentsMessageTokenList where received equals to UPDATED_RECEIVED
        defaultPaymentsMessageTokenShouldNotBeFound("received.in=" + UPDATED_RECEIVED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByReceivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where received is not null
        defaultPaymentsMessageTokenShouldBeFound("received.specified=true");

        // Get all the paymentsMessageTokenList where received is null
        defaultPaymentsMessageTokenShouldNotBeFound("received.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByActionedIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where actioned equals to DEFAULT_ACTIONED
        defaultPaymentsMessageTokenShouldBeFound("actioned.equals=" + DEFAULT_ACTIONED);

        // Get all the paymentsMessageTokenList where actioned equals to UPDATED_ACTIONED
        defaultPaymentsMessageTokenShouldNotBeFound("actioned.equals=" + UPDATED_ACTIONED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByActionedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where actioned not equals to DEFAULT_ACTIONED
        defaultPaymentsMessageTokenShouldNotBeFound("actioned.notEquals=" + DEFAULT_ACTIONED);

        // Get all the paymentsMessageTokenList where actioned not equals to UPDATED_ACTIONED
        defaultPaymentsMessageTokenShouldBeFound("actioned.notEquals=" + UPDATED_ACTIONED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByActionedIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where actioned in DEFAULT_ACTIONED or UPDATED_ACTIONED
        defaultPaymentsMessageTokenShouldBeFound("actioned.in=" + DEFAULT_ACTIONED + "," + UPDATED_ACTIONED);

        // Get all the paymentsMessageTokenList where actioned equals to UPDATED_ACTIONED
        defaultPaymentsMessageTokenShouldNotBeFound("actioned.in=" + UPDATED_ACTIONED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByActionedIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where actioned is not null
        defaultPaymentsMessageTokenShouldBeFound("actioned.specified=true");

        // Get all the paymentsMessageTokenList where actioned is null
        defaultPaymentsMessageTokenShouldNotBeFound("actioned.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByContentFullyEnqueuedIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where contentFullyEnqueued equals to DEFAULT_CONTENT_FULLY_ENQUEUED
        defaultPaymentsMessageTokenShouldBeFound("contentFullyEnqueued.equals=" + DEFAULT_CONTENT_FULLY_ENQUEUED);

        // Get all the paymentsMessageTokenList where contentFullyEnqueued equals to UPDATED_CONTENT_FULLY_ENQUEUED
        defaultPaymentsMessageTokenShouldNotBeFound("contentFullyEnqueued.equals=" + UPDATED_CONTENT_FULLY_ENQUEUED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByContentFullyEnqueuedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where contentFullyEnqueued not equals to DEFAULT_CONTENT_FULLY_ENQUEUED
        defaultPaymentsMessageTokenShouldNotBeFound("contentFullyEnqueued.notEquals=" + DEFAULT_CONTENT_FULLY_ENQUEUED);

        // Get all the paymentsMessageTokenList where contentFullyEnqueued not equals to UPDATED_CONTENT_FULLY_ENQUEUED
        defaultPaymentsMessageTokenShouldBeFound("contentFullyEnqueued.notEquals=" + UPDATED_CONTENT_FULLY_ENQUEUED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByContentFullyEnqueuedIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where contentFullyEnqueued in DEFAULT_CONTENT_FULLY_ENQUEUED or UPDATED_CONTENT_FULLY_ENQUEUED
        defaultPaymentsMessageTokenShouldBeFound("contentFullyEnqueued.in=" + DEFAULT_CONTENT_FULLY_ENQUEUED + "," + UPDATED_CONTENT_FULLY_ENQUEUED);

        // Get all the paymentsMessageTokenList where contentFullyEnqueued equals to UPDATED_CONTENT_FULLY_ENQUEUED
        defaultPaymentsMessageTokenShouldNotBeFound("contentFullyEnqueued.in=" + UPDATED_CONTENT_FULLY_ENQUEUED);
    }

    @Test
    @Transactional
    public void getAllPaymentsMessageTokensByContentFullyEnqueuedIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        // Get all the paymentsMessageTokenList where contentFullyEnqueued is not null
        defaultPaymentsMessageTokenShouldBeFound("contentFullyEnqueued.specified=true");

        // Get all the paymentsMessageTokenList where contentFullyEnqueued is null
        defaultPaymentsMessageTokenShouldNotBeFound("contentFullyEnqueued.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentsMessageTokenShouldBeFound(String filter) throws Exception {
        restPaymentsMessageTokenMockMvc.perform(get("/api/payments-message-tokens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsMessageToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.intValue())))
            .andExpect(jsonPath("$.[*].tokenValue").value(hasItem(DEFAULT_TOKEN_VALUE)))
            .andExpect(jsonPath("$.[*].received").value(hasItem(DEFAULT_RECEIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].actioned").value(hasItem(DEFAULT_ACTIONED.booleanValue())))
            .andExpect(jsonPath("$.[*].contentFullyEnqueued").value(hasItem(DEFAULT_CONTENT_FULLY_ENQUEUED.booleanValue())));

        // Check, that the count call also returns 1
        restPaymentsMessageTokenMockMvc.perform(get("/api/payments-message-tokens/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentsMessageTokenShouldNotBeFound(String filter) throws Exception {
        restPaymentsMessageTokenMockMvc.perform(get("/api/payments-message-tokens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentsMessageTokenMockMvc.perform(get("/api/payments-message-tokens/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentsMessageToken() throws Exception {
        // Get the paymentsMessageToken
        restPaymentsMessageTokenMockMvc.perform(get("/api/payments-message-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentsMessageToken() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        int databaseSizeBeforeUpdate = paymentsMessageTokenRepository.findAll().size();

        // Update the paymentsMessageToken
        PaymentsMessageToken updatedPaymentsMessageToken = paymentsMessageTokenRepository.findById(paymentsMessageToken.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentsMessageToken are not directly saved in db
        em.detach(updatedPaymentsMessageToken);
        updatedPaymentsMessageToken
            .description(UPDATED_DESCRIPTION)
            .timeSent(UPDATED_TIME_SENT)
            .tokenValue(UPDATED_TOKEN_VALUE)
            .received(UPDATED_RECEIVED)
            .actioned(UPDATED_ACTIONED)
            .contentFullyEnqueued(UPDATED_CONTENT_FULLY_ENQUEUED);
        PaymentsMessageTokenDTO paymentsMessageTokenDTO = paymentsMessageTokenMapper.toDto(updatedPaymentsMessageToken);

        restPaymentsMessageTokenMockMvc.perform(put("/api/payments-message-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsMessageTokenDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentsMessageToken in the database
        List<PaymentsMessageToken> paymentsMessageTokenList = paymentsMessageTokenRepository.findAll();
        assertThat(paymentsMessageTokenList).hasSize(databaseSizeBeforeUpdate);
        PaymentsMessageToken testPaymentsMessageToken = paymentsMessageTokenList.get(paymentsMessageTokenList.size() - 1);
        assertThat(testPaymentsMessageToken.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentsMessageToken.getTimeSent()).isEqualTo(UPDATED_TIME_SENT);
        assertThat(testPaymentsMessageToken.getTokenValue()).isEqualTo(UPDATED_TOKEN_VALUE);
        assertThat(testPaymentsMessageToken.isReceived()).isEqualTo(UPDATED_RECEIVED);
        assertThat(testPaymentsMessageToken.isActioned()).isEqualTo(UPDATED_ACTIONED);
        assertThat(testPaymentsMessageToken.isContentFullyEnqueued()).isEqualTo(UPDATED_CONTENT_FULLY_ENQUEUED);

        // Validate the PaymentsMessageToken in Elasticsearch
        verify(mockPaymentsMessageTokenSearchRepository, times(1)).save(testPaymentsMessageToken);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentsMessageToken() throws Exception {
        int databaseSizeBeforeUpdate = paymentsMessageTokenRepository.findAll().size();

        // Create the PaymentsMessageToken
        PaymentsMessageTokenDTO paymentsMessageTokenDTO = paymentsMessageTokenMapper.toDto(paymentsMessageToken);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMessageTokenMockMvc.perform(put("/api/payments-message-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsMessageTokenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentsMessageToken in the database
        List<PaymentsMessageToken> paymentsMessageTokenList = paymentsMessageTokenRepository.findAll();
        assertThat(paymentsMessageTokenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentsMessageToken in Elasticsearch
        verify(mockPaymentsMessageTokenSearchRepository, times(0)).save(paymentsMessageToken);
    }

    @Test
    @Transactional
    public void deletePaymentsMessageToken() throws Exception {
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);

        int databaseSizeBeforeDelete = paymentsMessageTokenRepository.findAll().size();

        // Delete the paymentsMessageToken
        restPaymentsMessageTokenMockMvc.perform(delete("/api/payments-message-tokens/{id}", paymentsMessageToken.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentsMessageToken> paymentsMessageTokenList = paymentsMessageTokenRepository.findAll();
        assertThat(paymentsMessageTokenList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentsMessageToken in Elasticsearch
        verify(mockPaymentsMessageTokenSearchRepository, times(1)).deleteById(paymentsMessageToken.getId());
    }

    @Test
    @Transactional
    public void searchPaymentsMessageToken() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        paymentsMessageTokenRepository.saveAndFlush(paymentsMessageToken);
        when(mockPaymentsMessageTokenSearchRepository.search(queryStringQuery("id:" + paymentsMessageToken.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentsMessageToken), PageRequest.of(0, 1), 1));

        // Search the paymentsMessageToken
        restPaymentsMessageTokenMockMvc.perform(get("/api/_search/payments-message-tokens?query=id:" + paymentsMessageToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsMessageToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.intValue())))
            .andExpect(jsonPath("$.[*].tokenValue").value(hasItem(DEFAULT_TOKEN_VALUE)))
            .andExpect(jsonPath("$.[*].received").value(hasItem(DEFAULT_RECEIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].actioned").value(hasItem(DEFAULT_ACTIONED.booleanValue())))
            .andExpect(jsonPath("$.[*].contentFullyEnqueued").value(hasItem(DEFAULT_CONTENT_FULLY_ENQUEUED.booleanValue())));
    }
}
