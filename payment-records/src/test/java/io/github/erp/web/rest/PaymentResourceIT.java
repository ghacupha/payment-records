package io.github.erp.web.rest;

import io.github.erp.PaymentRecordsApp;
import io.github.erp.domain.Payment;
import io.github.erp.domain.Placeholder;
import io.github.erp.domain.PaymentLabel;
import io.github.erp.repository.PaymentRepository;
import io.github.erp.repository.search.PaymentSearchRepository;
import io.github.erp.service.PaymentService;
import io.github.erp.service.dto.PaymentDTO;
import io.github.erp.service.mapper.PaymentMapper;
import io.github.erp.service.dto.PaymentCriteria;
import io.github.erp.service.PaymentQueryService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@SpringBootTest(classes = PaymentRecordsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentResourceIT {

    private static final String DEFAULT_PAYMENTS_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENTS_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TRANSACTION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TRANSACTION_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_CURRENCY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TRANSACTION_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal SMALLER_TRANSACTION_AMOUNT = new BigDecimal(0 - 1);

    private static final String DEFAULT_BENEFICIARY = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY = "BBBBBBBBBB";

    @Autowired
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentRepository paymentRepositoryMock;

    @Autowired
    private PaymentMapper paymentMapper;

    @Mock
    private PaymentService paymentServiceMock;

    @Autowired
    private PaymentService paymentService;

    /**
     * This repository is mocked in the io.github.erp.repository.search test package.
     *
     * @see io.github.erp.repository.search.PaymentSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentSearchRepository mockPaymentSearchRepository;

    @Autowired
    private PaymentQueryService paymentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .paymentsCategory(DEFAULT_PAYMENTS_CATEGORY)
            .transactionNumber(DEFAULT_TRANSACTION_NUMBER)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .transactionCurrency(DEFAULT_TRANSACTION_CURRENCY)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .beneficiary(DEFAULT_BENEFICIARY);
        return payment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .paymentsCategory(UPDATED_PAYMENTS_CATEGORY)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionCurrency(UPDATED_TRANSACTION_CURRENCY)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .beneficiary(UPDATED_BENEFICIARY);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getPaymentsCategory()).isEqualTo(DEFAULT_PAYMENTS_CATEGORY);
        assertThat(testPayment.getTransactionNumber()).isEqualTo(DEFAULT_TRANSACTION_NUMBER);
        assertThat(testPayment.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testPayment.getTransactionCurrency()).isEqualTo(DEFAULT_TRANSACTION_CURRENCY);
        assertThat(testPayment.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testPayment.getBeneficiary()).isEqualTo(DEFAULT_BENEFICIARY);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(1)).save(testPayment);
    }

    @Test
    @Transactional
    public void createPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment with an existing ID
        payment.setId(1L);
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(0)).save(payment);
    }


    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setTransactionAmount(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);


        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentsCategory").value(hasItem(DEFAULT_PAYMENTS_CATEGORY)))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionCurrency").value(hasItem(DEFAULT_TRANSACTION_CURRENCY)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].beneficiary").value(hasItem(DEFAULT_BENEFICIARY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPaymentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(paymentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentMockMvc.perform(get("/api/payments?eagerload=true"))
            .andExpect(status().isOk());

        verify(paymentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPaymentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(paymentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentMockMvc.perform(get("/api/payments?eagerload=true"))
            .andExpect(status().isOk());

        verify(paymentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.paymentsCategory").value(DEFAULT_PAYMENTS_CATEGORY))
            .andExpect(jsonPath("$.transactionNumber").value(DEFAULT_TRANSACTION_NUMBER))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.transactionCurrency").value(DEFAULT_TRANSACTION_CURRENCY))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.beneficiary").value(DEFAULT_BENEFICIARY));
    }


    @Test
    @Transactional
    public void getPaymentsByIdFiltering() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        Long id = payment.getId();

        defaultPaymentShouldBeFound("id.equals=" + id);
        defaultPaymentShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentsByPaymentsCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentsCategory equals to DEFAULT_PAYMENTS_CATEGORY
        defaultPaymentShouldBeFound("paymentsCategory.equals=" + DEFAULT_PAYMENTS_CATEGORY);

        // Get all the paymentList where paymentsCategory equals to UPDATED_PAYMENTS_CATEGORY
        defaultPaymentShouldNotBeFound("paymentsCategory.equals=" + UPDATED_PAYMENTS_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentsCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentsCategory not equals to DEFAULT_PAYMENTS_CATEGORY
        defaultPaymentShouldNotBeFound("paymentsCategory.notEquals=" + DEFAULT_PAYMENTS_CATEGORY);

        // Get all the paymentList where paymentsCategory not equals to UPDATED_PAYMENTS_CATEGORY
        defaultPaymentShouldBeFound("paymentsCategory.notEquals=" + UPDATED_PAYMENTS_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentsCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentsCategory in DEFAULT_PAYMENTS_CATEGORY or UPDATED_PAYMENTS_CATEGORY
        defaultPaymentShouldBeFound("paymentsCategory.in=" + DEFAULT_PAYMENTS_CATEGORY + "," + UPDATED_PAYMENTS_CATEGORY);

        // Get all the paymentList where paymentsCategory equals to UPDATED_PAYMENTS_CATEGORY
        defaultPaymentShouldNotBeFound("paymentsCategory.in=" + UPDATED_PAYMENTS_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentsCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentsCategory is not null
        defaultPaymentShouldBeFound("paymentsCategory.specified=true");

        // Get all the paymentList where paymentsCategory is null
        defaultPaymentShouldNotBeFound("paymentsCategory.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByPaymentsCategoryContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentsCategory contains DEFAULT_PAYMENTS_CATEGORY
        defaultPaymentShouldBeFound("paymentsCategory.contains=" + DEFAULT_PAYMENTS_CATEGORY);

        // Get all the paymentList where paymentsCategory contains UPDATED_PAYMENTS_CATEGORY
        defaultPaymentShouldNotBeFound("paymentsCategory.contains=" + UPDATED_PAYMENTS_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentsCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentsCategory does not contain DEFAULT_PAYMENTS_CATEGORY
        defaultPaymentShouldNotBeFound("paymentsCategory.doesNotContain=" + DEFAULT_PAYMENTS_CATEGORY);

        // Get all the paymentList where paymentsCategory does not contain UPDATED_PAYMENTS_CATEGORY
        defaultPaymentShouldBeFound("paymentsCategory.doesNotContain=" + UPDATED_PAYMENTS_CATEGORY);
    }


    @Test
    @Transactional
    public void getAllPaymentsByTransactionNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionNumber equals to DEFAULT_TRANSACTION_NUMBER
        defaultPaymentShouldBeFound("transactionNumber.equals=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the paymentList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultPaymentShouldNotBeFound("transactionNumber.equals=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionNumber not equals to DEFAULT_TRANSACTION_NUMBER
        defaultPaymentShouldNotBeFound("transactionNumber.notEquals=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the paymentList where transactionNumber not equals to UPDATED_TRANSACTION_NUMBER
        defaultPaymentShouldBeFound("transactionNumber.notEquals=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionNumber in DEFAULT_TRANSACTION_NUMBER or UPDATED_TRANSACTION_NUMBER
        defaultPaymentShouldBeFound("transactionNumber.in=" + DEFAULT_TRANSACTION_NUMBER + "," + UPDATED_TRANSACTION_NUMBER);

        // Get all the paymentList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultPaymentShouldNotBeFound("transactionNumber.in=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionNumber is not null
        defaultPaymentShouldBeFound("transactionNumber.specified=true");

        // Get all the paymentList where transactionNumber is null
        defaultPaymentShouldNotBeFound("transactionNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByTransactionNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionNumber contains DEFAULT_TRANSACTION_NUMBER
        defaultPaymentShouldBeFound("transactionNumber.contains=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the paymentList where transactionNumber contains UPDATED_TRANSACTION_NUMBER
        defaultPaymentShouldNotBeFound("transactionNumber.contains=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionNumber does not contain DEFAULT_TRANSACTION_NUMBER
        defaultPaymentShouldNotBeFound("transactionNumber.doesNotContain=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the paymentList where transactionNumber does not contain UPDATED_TRANSACTION_NUMBER
        defaultPaymentShouldBeFound("transactionNumber.doesNotContain=" + UPDATED_TRANSACTION_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPaymentsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultPaymentShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the paymentList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultPaymentShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionDate not equals to DEFAULT_TRANSACTION_DATE
        defaultPaymentShouldNotBeFound("transactionDate.notEquals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the paymentList where transactionDate not equals to UPDATED_TRANSACTION_DATE
        defaultPaymentShouldBeFound("transactionDate.notEquals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultPaymentShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the paymentList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultPaymentShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionDate is not null
        defaultPaymentShouldBeFound("transactionDate.specified=true");

        // Get all the paymentList where transactionDate is null
        defaultPaymentShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionDate is greater than or equal to DEFAULT_TRANSACTION_DATE
        defaultPaymentShouldBeFound("transactionDate.greaterThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the paymentList where transactionDate is greater than or equal to UPDATED_TRANSACTION_DATE
        defaultPaymentShouldNotBeFound("transactionDate.greaterThanOrEqual=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionDate is less than or equal to DEFAULT_TRANSACTION_DATE
        defaultPaymentShouldBeFound("transactionDate.lessThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the paymentList where transactionDate is less than or equal to SMALLER_TRANSACTION_DATE
        defaultPaymentShouldNotBeFound("transactionDate.lessThanOrEqual=" + SMALLER_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionDate is less than DEFAULT_TRANSACTION_DATE
        defaultPaymentShouldNotBeFound("transactionDate.lessThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the paymentList where transactionDate is less than UPDATED_TRANSACTION_DATE
        defaultPaymentShouldBeFound("transactionDate.lessThan=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionDate is greater than DEFAULT_TRANSACTION_DATE
        defaultPaymentShouldNotBeFound("transactionDate.greaterThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the paymentList where transactionDate is greater than SMALLER_TRANSACTION_DATE
        defaultPaymentShouldBeFound("transactionDate.greaterThan=" + SMALLER_TRANSACTION_DATE);
    }


    @Test
    @Transactional
    public void getAllPaymentsByTransactionCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionCurrency equals to DEFAULT_TRANSACTION_CURRENCY
        defaultPaymentShouldBeFound("transactionCurrency.equals=" + DEFAULT_TRANSACTION_CURRENCY);

        // Get all the paymentList where transactionCurrency equals to UPDATED_TRANSACTION_CURRENCY
        defaultPaymentShouldNotBeFound("transactionCurrency.equals=" + UPDATED_TRANSACTION_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionCurrencyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionCurrency not equals to DEFAULT_TRANSACTION_CURRENCY
        defaultPaymentShouldNotBeFound("transactionCurrency.notEquals=" + DEFAULT_TRANSACTION_CURRENCY);

        // Get all the paymentList where transactionCurrency not equals to UPDATED_TRANSACTION_CURRENCY
        defaultPaymentShouldBeFound("transactionCurrency.notEquals=" + UPDATED_TRANSACTION_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionCurrency in DEFAULT_TRANSACTION_CURRENCY or UPDATED_TRANSACTION_CURRENCY
        defaultPaymentShouldBeFound("transactionCurrency.in=" + DEFAULT_TRANSACTION_CURRENCY + "," + UPDATED_TRANSACTION_CURRENCY);

        // Get all the paymentList where transactionCurrency equals to UPDATED_TRANSACTION_CURRENCY
        defaultPaymentShouldNotBeFound("transactionCurrency.in=" + UPDATED_TRANSACTION_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionCurrency is not null
        defaultPaymentShouldBeFound("transactionCurrency.specified=true");

        // Get all the paymentList where transactionCurrency is null
        defaultPaymentShouldNotBeFound("transactionCurrency.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByTransactionCurrencyContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionCurrency contains DEFAULT_TRANSACTION_CURRENCY
        defaultPaymentShouldBeFound("transactionCurrency.contains=" + DEFAULT_TRANSACTION_CURRENCY);

        // Get all the paymentList where transactionCurrency contains UPDATED_TRANSACTION_CURRENCY
        defaultPaymentShouldNotBeFound("transactionCurrency.contains=" + UPDATED_TRANSACTION_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionCurrencyNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionCurrency does not contain DEFAULT_TRANSACTION_CURRENCY
        defaultPaymentShouldNotBeFound("transactionCurrency.doesNotContain=" + DEFAULT_TRANSACTION_CURRENCY);

        // Get all the paymentList where transactionCurrency does not contain UPDATED_TRANSACTION_CURRENCY
        defaultPaymentShouldBeFound("transactionCurrency.doesNotContain=" + UPDATED_TRANSACTION_CURRENCY);
    }


    @Test
    @Transactional
    public void getAllPaymentsByTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionAmount equals to DEFAULT_TRANSACTION_AMOUNT
        defaultPaymentShouldBeFound("transactionAmount.equals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the paymentList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultPaymentShouldNotBeFound("transactionAmount.equals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionAmount not equals to DEFAULT_TRANSACTION_AMOUNT
        defaultPaymentShouldNotBeFound("transactionAmount.notEquals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the paymentList where transactionAmount not equals to UPDATED_TRANSACTION_AMOUNT
        defaultPaymentShouldBeFound("transactionAmount.notEquals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionAmount in DEFAULT_TRANSACTION_AMOUNT or UPDATED_TRANSACTION_AMOUNT
        defaultPaymentShouldBeFound("transactionAmount.in=" + DEFAULT_TRANSACTION_AMOUNT + "," + UPDATED_TRANSACTION_AMOUNT);

        // Get all the paymentList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultPaymentShouldNotBeFound("transactionAmount.in=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionAmount is not null
        defaultPaymentShouldBeFound("transactionAmount.specified=true");

        // Get all the paymentList where transactionAmount is null
        defaultPaymentShouldNotBeFound("transactionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionAmount is greater than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultPaymentShouldBeFound("transactionAmount.greaterThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the paymentList where transactionAmount is greater than or equal to UPDATED_TRANSACTION_AMOUNT
        defaultPaymentShouldNotBeFound("transactionAmount.greaterThanOrEqual=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionAmount is less than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultPaymentShouldBeFound("transactionAmount.lessThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the paymentList where transactionAmount is less than or equal to SMALLER_TRANSACTION_AMOUNT
        defaultPaymentShouldNotBeFound("transactionAmount.lessThanOrEqual=" + SMALLER_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionAmount is less than DEFAULT_TRANSACTION_AMOUNT
        defaultPaymentShouldNotBeFound("transactionAmount.lessThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the paymentList where transactionAmount is less than UPDATED_TRANSACTION_AMOUNT
        defaultPaymentShouldBeFound("transactionAmount.lessThan=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTransactionAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where transactionAmount is greater than DEFAULT_TRANSACTION_AMOUNT
        defaultPaymentShouldNotBeFound("transactionAmount.greaterThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the paymentList where transactionAmount is greater than SMALLER_TRANSACTION_AMOUNT
        defaultPaymentShouldBeFound("transactionAmount.greaterThan=" + SMALLER_TRANSACTION_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllPaymentsByBeneficiaryIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where beneficiary equals to DEFAULT_BENEFICIARY
        defaultPaymentShouldBeFound("beneficiary.equals=" + DEFAULT_BENEFICIARY);

        // Get all the paymentList where beneficiary equals to UPDATED_BENEFICIARY
        defaultPaymentShouldNotBeFound("beneficiary.equals=" + UPDATED_BENEFICIARY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByBeneficiaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where beneficiary not equals to DEFAULT_BENEFICIARY
        defaultPaymentShouldNotBeFound("beneficiary.notEquals=" + DEFAULT_BENEFICIARY);

        // Get all the paymentList where beneficiary not equals to UPDATED_BENEFICIARY
        defaultPaymentShouldBeFound("beneficiary.notEquals=" + UPDATED_BENEFICIARY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByBeneficiaryIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where beneficiary in DEFAULT_BENEFICIARY or UPDATED_BENEFICIARY
        defaultPaymentShouldBeFound("beneficiary.in=" + DEFAULT_BENEFICIARY + "," + UPDATED_BENEFICIARY);

        // Get all the paymentList where beneficiary equals to UPDATED_BENEFICIARY
        defaultPaymentShouldNotBeFound("beneficiary.in=" + UPDATED_BENEFICIARY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByBeneficiaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where beneficiary is not null
        defaultPaymentShouldBeFound("beneficiary.specified=true");

        // Get all the paymentList where beneficiary is null
        defaultPaymentShouldNotBeFound("beneficiary.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByBeneficiaryContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where beneficiary contains DEFAULT_BENEFICIARY
        defaultPaymentShouldBeFound("beneficiary.contains=" + DEFAULT_BENEFICIARY);

        // Get all the paymentList where beneficiary contains UPDATED_BENEFICIARY
        defaultPaymentShouldNotBeFound("beneficiary.contains=" + UPDATED_BENEFICIARY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByBeneficiaryNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where beneficiary does not contain DEFAULT_BENEFICIARY
        defaultPaymentShouldNotBeFound("beneficiary.doesNotContain=" + DEFAULT_BENEFICIARY);

        // Get all the paymentList where beneficiary does not contain UPDATED_BENEFICIARY
        defaultPaymentShouldBeFound("beneficiary.doesNotContain=" + UPDATED_BENEFICIARY);
    }


    @Test
    @Transactional
    public void getAllPaymentsByPlaceholderIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        Placeholder placeholder = PlaceholderResourceIT.createEntity(em);
        em.persist(placeholder);
        em.flush();
        payment.addPlaceholder(placeholder);
        paymentRepository.saveAndFlush(payment);
        Long placeholderId = placeholder.getId();

        // Get all the paymentList where placeholder equals to placeholderId
        defaultPaymentShouldBeFound("placeholderId.equals=" + placeholderId);

        // Get all the paymentList where placeholder equals to placeholderId + 1
        defaultPaymentShouldNotBeFound("placeholderId.equals=" + (placeholderId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByPaymentLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        PaymentLabel paymentLabel = PaymentLabelResourceIT.createEntity(em);
        em.persist(paymentLabel);
        em.flush();
        payment.addPaymentLabel(paymentLabel);
        paymentRepository.saveAndFlush(payment);
        Long paymentLabelId = paymentLabel.getId();

        // Get all the paymentList where paymentLabel equals to paymentLabelId
        defaultPaymentShouldBeFound("paymentLabelId.equals=" + paymentLabelId);

        // Get all the paymentList where paymentLabel equals to paymentLabelId + 1
        defaultPaymentShouldNotBeFound("paymentLabelId.equals=" + (paymentLabelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentShouldBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentsCategory").value(hasItem(DEFAULT_PAYMENTS_CATEGORY)))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionCurrency").value(hasItem(DEFAULT_TRANSACTION_CURRENCY)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].beneficiary").value(hasItem(DEFAULT_BENEFICIARY)));

        // Check, that the count call also returns 1
        restPaymentMockMvc.perform(get("/api/payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentShouldNotBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentMockMvc.perform(get("/api/payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .paymentsCategory(UPDATED_PAYMENTS_CATEGORY)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionCurrency(UPDATED_TRANSACTION_CURRENCY)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .beneficiary(UPDATED_BENEFICIARY);
        PaymentDTO paymentDTO = paymentMapper.toDto(updatedPayment);

        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getPaymentsCategory()).isEqualTo(UPDATED_PAYMENTS_CATEGORY);
        assertThat(testPayment.getTransactionNumber()).isEqualTo(UPDATED_TRANSACTION_NUMBER);
        assertThat(testPayment.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testPayment.getTransactionCurrency()).isEqualTo(UPDATED_TRANSACTION_CURRENCY);
        assertThat(testPayment.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testPayment.getBeneficiary()).isEqualTo(UPDATED_BENEFICIARY);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(1)).save(testPayment);
    }

    @Test
    @Transactional
    public void updateNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(0)).save(payment);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(1)).deleteById(payment.getId());
    }

    @Test
    @Transactional
    public void searchPayment() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        when(mockPaymentSearchRepository.search(queryStringQuery("id:" + payment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(payment), PageRequest.of(0, 1), 1));

        // Search the payment
        restPaymentMockMvc.perform(get("/api/_search/payments?query=id:" + payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentsCategory").value(hasItem(DEFAULT_PAYMENTS_CATEGORY)))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionCurrency").value(hasItem(DEFAULT_TRANSACTION_CURRENCY)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].beneficiary").value(hasItem(DEFAULT_BENEFICIARY)));
    }
}
