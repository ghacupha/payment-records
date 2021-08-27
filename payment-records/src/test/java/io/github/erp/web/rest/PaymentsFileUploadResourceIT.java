package io.github.erp.web.rest;

import io.github.erp.PaymentRecordsApp;
import io.github.erp.domain.PaymentsFileUpload;
import io.github.erp.domain.Placeholder;
import io.github.erp.repository.PaymentsFileUploadRepository;
import io.github.erp.repository.search.PaymentsFileUploadSearchRepository;
import io.github.erp.service.PaymentsFileUploadService;
import io.github.erp.service.dto.PaymentsFileUploadDTO;
import io.github.erp.service.mapper.PaymentsFileUploadMapper;
import io.github.erp.service.dto.PaymentsFileUploadCriteria;
import io.github.erp.service.PaymentsFileUploadQueryService;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
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
 * Integration tests for the {@link PaymentsFileUploadResource} REST controller.
 */
@SpringBootTest(classes = PaymentRecordsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentsFileUploadResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PERIOD_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PERIOD_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_PERIOD_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PERIOD_TO = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_PAYMENTS_FILE_TYPE_ID = 1L;
    private static final Long UPDATED_PAYMENTS_FILE_TYPE_ID = 2L;
    private static final Long SMALLER_PAYMENTS_FILE_TYPE_ID = 1L - 1L;

    private static final byte[] DEFAULT_DATA_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_UPLOAD_SUCCESSFUL = false;
    private static final Boolean UPDATED_UPLOAD_SUCCESSFUL = true;

    private static final Boolean DEFAULT_UPLOAD_PROCESSED = false;
    private static final Boolean UPDATED_UPLOAD_PROCESSED = true;

    private static final String DEFAULT_UPLOAD_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_TOKEN = "BBBBBBBBBB";

    @Autowired
    private PaymentsFileUploadRepository paymentsFileUploadRepository;

    @Mock
    private PaymentsFileUploadRepository paymentsFileUploadRepositoryMock;

    @Autowired
    private PaymentsFileUploadMapper paymentsFileUploadMapper;

    @Mock
    private PaymentsFileUploadService paymentsFileUploadServiceMock;

    @Autowired
    private PaymentsFileUploadService paymentsFileUploadService;

    /**
     * This repository is mocked in the io.github.erp.repository.search test package.
     *
     * @see io.github.erp.repository.search.PaymentsFileUploadSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentsFileUploadSearchRepository mockPaymentsFileUploadSearchRepository;

    @Autowired
    private PaymentsFileUploadQueryService paymentsFileUploadQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentsFileUploadMockMvc;

    private PaymentsFileUpload paymentsFileUpload;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentsFileUpload createEntity(EntityManager em) {
        PaymentsFileUpload paymentsFileUpload = new PaymentsFileUpload()
            .description(DEFAULT_DESCRIPTION)
            .fileName(DEFAULT_FILE_NAME)
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .paymentsFileTypeId(DEFAULT_PAYMENTS_FILE_TYPE_ID)
            .dataFile(DEFAULT_DATA_FILE)
            .dataFileContentType(DEFAULT_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED)
            .uploadToken(DEFAULT_UPLOAD_TOKEN);
        return paymentsFileUpload;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentsFileUpload createUpdatedEntity(EntityManager em) {
        PaymentsFileUpload paymentsFileUpload = new PaymentsFileUpload()
            .description(UPDATED_DESCRIPTION)
            .fileName(UPDATED_FILE_NAME)
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .paymentsFileTypeId(UPDATED_PAYMENTS_FILE_TYPE_ID)
            .dataFile(UPDATED_DATA_FILE)
            .dataFileContentType(UPDATED_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .uploadToken(UPDATED_UPLOAD_TOKEN);
        return paymentsFileUpload;
    }

    @BeforeEach
    public void initTest() {
        paymentsFileUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentsFileUpload() throws Exception {
        int databaseSizeBeforeCreate = paymentsFileUploadRepository.findAll().size();
        // Create the PaymentsFileUpload
        PaymentsFileUploadDTO paymentsFileUploadDTO = paymentsFileUploadMapper.toDto(paymentsFileUpload);
        restPaymentsFileUploadMockMvc.perform(post("/api/payments-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileUploadDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentsFileUpload in the database
        List<PaymentsFileUpload> paymentsFileUploadList = paymentsFileUploadRepository.findAll();
        assertThat(paymentsFileUploadList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentsFileUpload testPaymentsFileUpload = paymentsFileUploadList.get(paymentsFileUploadList.size() - 1);
        assertThat(testPaymentsFileUpload.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentsFileUpload.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testPaymentsFileUpload.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testPaymentsFileUpload.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testPaymentsFileUpload.getPaymentsFileTypeId()).isEqualTo(DEFAULT_PAYMENTS_FILE_TYPE_ID);
        assertThat(testPaymentsFileUpload.getDataFile()).isEqualTo(DEFAULT_DATA_FILE);
        assertThat(testPaymentsFileUpload.getDataFileContentType()).isEqualTo(DEFAULT_DATA_FILE_CONTENT_TYPE);
        assertThat(testPaymentsFileUpload.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testPaymentsFileUpload.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
        assertThat(testPaymentsFileUpload.getUploadToken()).isEqualTo(DEFAULT_UPLOAD_TOKEN);

        // Validate the PaymentsFileUpload in Elasticsearch
        verify(mockPaymentsFileUploadSearchRepository, times(1)).save(testPaymentsFileUpload);
    }

    @Test
    @Transactional
    public void createPaymentsFileUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentsFileUploadRepository.findAll().size();

        // Create the PaymentsFileUpload with an existing ID
        paymentsFileUpload.setId(1L);
        PaymentsFileUploadDTO paymentsFileUploadDTO = paymentsFileUploadMapper.toDto(paymentsFileUpload);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsFileUploadMockMvc.perform(post("/api/payments-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentsFileUpload in the database
        List<PaymentsFileUpload> paymentsFileUploadList = paymentsFileUploadRepository.findAll();
        assertThat(paymentsFileUploadList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentsFileUpload in Elasticsearch
        verify(mockPaymentsFileUploadSearchRepository, times(0)).save(paymentsFileUpload);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsFileUploadRepository.findAll().size();
        // set the field null
        paymentsFileUpload.setDescription(null);

        // Create the PaymentsFileUpload, which fails.
        PaymentsFileUploadDTO paymentsFileUploadDTO = paymentsFileUploadMapper.toDto(paymentsFileUpload);


        restPaymentsFileUploadMockMvc.perform(post("/api/payments-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentsFileUpload> paymentsFileUploadList = paymentsFileUploadRepository.findAll();
        assertThat(paymentsFileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsFileUploadRepository.findAll().size();
        // set the field null
        paymentsFileUpload.setFileName(null);

        // Create the PaymentsFileUpload, which fails.
        PaymentsFileUploadDTO paymentsFileUploadDTO = paymentsFileUploadMapper.toDto(paymentsFileUpload);


        restPaymentsFileUploadMockMvc.perform(post("/api/payments-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentsFileUpload> paymentsFileUploadList = paymentsFileUploadRepository.findAll();
        assertThat(paymentsFileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentsFileTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsFileUploadRepository.findAll().size();
        // set the field null
        paymentsFileUpload.setPaymentsFileTypeId(null);

        // Create the PaymentsFileUpload, which fails.
        PaymentsFileUploadDTO paymentsFileUploadDTO = paymentsFileUploadMapper.toDto(paymentsFileUpload);


        restPaymentsFileUploadMockMvc.perform(post("/api/payments-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentsFileUpload> paymentsFileUploadList = paymentsFileUploadRepository.findAll();
        assertThat(paymentsFileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploads() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList
        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsFileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].paymentsFileTypeId").value(hasItem(DEFAULT_PAYMENTS_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPaymentsFileUploadsWithEagerRelationshipsIsEnabled() throws Exception {
        when(paymentsFileUploadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads?eagerload=true"))
            .andExpect(status().isOk());

        verify(paymentsFileUploadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPaymentsFileUploadsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(paymentsFileUploadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads?eagerload=true"))
            .andExpect(status().isOk());

        verify(paymentsFileUploadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPaymentsFileUpload() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get the paymentsFileUpload
        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads/{id}", paymentsFileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentsFileUpload.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.paymentsFileTypeId").value(DEFAULT_PAYMENTS_FILE_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.dataFileContentType").value(DEFAULT_DATA_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataFile").value(Base64Utils.encodeToString(DEFAULT_DATA_FILE)))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.uploadToken").value(DEFAULT_UPLOAD_TOKEN));
    }


    @Test
    @Transactional
    public void getPaymentsFileUploadsByIdFiltering() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        Long id = paymentsFileUpload.getId();

        defaultPaymentsFileUploadShouldBeFound("id.equals=" + id);
        defaultPaymentsFileUploadShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentsFileUploadShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentsFileUploadShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentsFileUploadShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentsFileUploadShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where description equals to DEFAULT_DESCRIPTION
        defaultPaymentsFileUploadShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsFileUploadList where description equals to UPDATED_DESCRIPTION
        defaultPaymentsFileUploadShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where description not equals to DEFAULT_DESCRIPTION
        defaultPaymentsFileUploadShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsFileUploadList where description not equals to UPDATED_DESCRIPTION
        defaultPaymentsFileUploadShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPaymentsFileUploadShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the paymentsFileUploadList where description equals to UPDATED_DESCRIPTION
        defaultPaymentsFileUploadShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where description is not null
        defaultPaymentsFileUploadShouldBeFound("description.specified=true");

        // Get all the paymentsFileUploadList where description is null
        defaultPaymentsFileUploadShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsFileUploadsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where description contains DEFAULT_DESCRIPTION
        defaultPaymentsFileUploadShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsFileUploadList where description contains UPDATED_DESCRIPTION
        defaultPaymentsFileUploadShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where description does not contain DEFAULT_DESCRIPTION
        defaultPaymentsFileUploadShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsFileUploadList where description does not contain UPDATED_DESCRIPTION
        defaultPaymentsFileUploadShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where fileName equals to DEFAULT_FILE_NAME
        defaultPaymentsFileUploadShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the paymentsFileUploadList where fileName equals to UPDATED_FILE_NAME
        defaultPaymentsFileUploadShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where fileName not equals to DEFAULT_FILE_NAME
        defaultPaymentsFileUploadShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the paymentsFileUploadList where fileName not equals to UPDATED_FILE_NAME
        defaultPaymentsFileUploadShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultPaymentsFileUploadShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the paymentsFileUploadList where fileName equals to UPDATED_FILE_NAME
        defaultPaymentsFileUploadShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where fileName is not null
        defaultPaymentsFileUploadShouldBeFound("fileName.specified=true");

        // Get all the paymentsFileUploadList where fileName is null
        defaultPaymentsFileUploadShouldNotBeFound("fileName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsFileUploadsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where fileName contains DEFAULT_FILE_NAME
        defaultPaymentsFileUploadShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the paymentsFileUploadList where fileName contains UPDATED_FILE_NAME
        defaultPaymentsFileUploadShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where fileName does not contain DEFAULT_FILE_NAME
        defaultPaymentsFileUploadShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the paymentsFileUploadList where fileName does not contain UPDATED_FILE_NAME
        defaultPaymentsFileUploadShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultPaymentsFileUploadShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the paymentsFileUploadList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultPaymentsFileUploadShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodFrom not equals to DEFAULT_PERIOD_FROM
        defaultPaymentsFileUploadShouldNotBeFound("periodFrom.notEquals=" + DEFAULT_PERIOD_FROM);

        // Get all the paymentsFileUploadList where periodFrom not equals to UPDATED_PERIOD_FROM
        defaultPaymentsFileUploadShouldBeFound("periodFrom.notEquals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultPaymentsFileUploadShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the paymentsFileUploadList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultPaymentsFileUploadShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodFrom is not null
        defaultPaymentsFileUploadShouldBeFound("periodFrom.specified=true");

        // Get all the paymentsFileUploadList where periodFrom is null
        defaultPaymentsFileUploadShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodFrom is greater than or equal to DEFAULT_PERIOD_FROM
        defaultPaymentsFileUploadShouldBeFound("periodFrom.greaterThanOrEqual=" + DEFAULT_PERIOD_FROM);

        // Get all the paymentsFileUploadList where periodFrom is greater than or equal to UPDATED_PERIOD_FROM
        defaultPaymentsFileUploadShouldNotBeFound("periodFrom.greaterThanOrEqual=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodFrom is less than or equal to DEFAULT_PERIOD_FROM
        defaultPaymentsFileUploadShouldBeFound("periodFrom.lessThanOrEqual=" + DEFAULT_PERIOD_FROM);

        // Get all the paymentsFileUploadList where periodFrom is less than or equal to SMALLER_PERIOD_FROM
        defaultPaymentsFileUploadShouldNotBeFound("periodFrom.lessThanOrEqual=" + SMALLER_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodFrom is less than DEFAULT_PERIOD_FROM
        defaultPaymentsFileUploadShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the paymentsFileUploadList where periodFrom is less than UPDATED_PERIOD_FROM
        defaultPaymentsFileUploadShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodFrom is greater than DEFAULT_PERIOD_FROM
        defaultPaymentsFileUploadShouldNotBeFound("periodFrom.greaterThan=" + DEFAULT_PERIOD_FROM);

        // Get all the paymentsFileUploadList where periodFrom is greater than SMALLER_PERIOD_FROM
        defaultPaymentsFileUploadShouldBeFound("periodFrom.greaterThan=" + SMALLER_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodTo equals to DEFAULT_PERIOD_TO
        defaultPaymentsFileUploadShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the paymentsFileUploadList where periodTo equals to UPDATED_PERIOD_TO
        defaultPaymentsFileUploadShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodTo not equals to DEFAULT_PERIOD_TO
        defaultPaymentsFileUploadShouldNotBeFound("periodTo.notEquals=" + DEFAULT_PERIOD_TO);

        // Get all the paymentsFileUploadList where periodTo not equals to UPDATED_PERIOD_TO
        defaultPaymentsFileUploadShouldBeFound("periodTo.notEquals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultPaymentsFileUploadShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the paymentsFileUploadList where periodTo equals to UPDATED_PERIOD_TO
        defaultPaymentsFileUploadShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodTo is not null
        defaultPaymentsFileUploadShouldBeFound("periodTo.specified=true");

        // Get all the paymentsFileUploadList where periodTo is null
        defaultPaymentsFileUploadShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodTo is greater than or equal to DEFAULT_PERIOD_TO
        defaultPaymentsFileUploadShouldBeFound("periodTo.greaterThanOrEqual=" + DEFAULT_PERIOD_TO);

        // Get all the paymentsFileUploadList where periodTo is greater than or equal to UPDATED_PERIOD_TO
        defaultPaymentsFileUploadShouldNotBeFound("periodTo.greaterThanOrEqual=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodTo is less than or equal to DEFAULT_PERIOD_TO
        defaultPaymentsFileUploadShouldBeFound("periodTo.lessThanOrEqual=" + DEFAULT_PERIOD_TO);

        // Get all the paymentsFileUploadList where periodTo is less than or equal to SMALLER_PERIOD_TO
        defaultPaymentsFileUploadShouldNotBeFound("periodTo.lessThanOrEqual=" + SMALLER_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodTo is less than DEFAULT_PERIOD_TO
        defaultPaymentsFileUploadShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the paymentsFileUploadList where periodTo is less than UPDATED_PERIOD_TO
        defaultPaymentsFileUploadShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPeriodToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where periodTo is greater than DEFAULT_PERIOD_TO
        defaultPaymentsFileUploadShouldNotBeFound("periodTo.greaterThan=" + DEFAULT_PERIOD_TO);

        // Get all the paymentsFileUploadList where periodTo is greater than SMALLER_PERIOD_TO
        defaultPaymentsFileUploadShouldBeFound("periodTo.greaterThan=" + SMALLER_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPaymentsFileTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where paymentsFileTypeId equals to DEFAULT_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldBeFound("paymentsFileTypeId.equals=" + DEFAULT_PAYMENTS_FILE_TYPE_ID);

        // Get all the paymentsFileUploadList where paymentsFileTypeId equals to UPDATED_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldNotBeFound("paymentsFileTypeId.equals=" + UPDATED_PAYMENTS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPaymentsFileTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where paymentsFileTypeId not equals to DEFAULT_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldNotBeFound("paymentsFileTypeId.notEquals=" + DEFAULT_PAYMENTS_FILE_TYPE_ID);

        // Get all the paymentsFileUploadList where paymentsFileTypeId not equals to UPDATED_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldBeFound("paymentsFileTypeId.notEquals=" + UPDATED_PAYMENTS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPaymentsFileTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where paymentsFileTypeId in DEFAULT_PAYMENTS_FILE_TYPE_ID or UPDATED_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldBeFound("paymentsFileTypeId.in=" + DEFAULT_PAYMENTS_FILE_TYPE_ID + "," + UPDATED_PAYMENTS_FILE_TYPE_ID);

        // Get all the paymentsFileUploadList where paymentsFileTypeId equals to UPDATED_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldNotBeFound("paymentsFileTypeId.in=" + UPDATED_PAYMENTS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPaymentsFileTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is not null
        defaultPaymentsFileUploadShouldBeFound("paymentsFileTypeId.specified=true");

        // Get all the paymentsFileUploadList where paymentsFileTypeId is null
        defaultPaymentsFileUploadShouldNotBeFound("paymentsFileTypeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPaymentsFileTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is greater than or equal to DEFAULT_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldBeFound("paymentsFileTypeId.greaterThanOrEqual=" + DEFAULT_PAYMENTS_FILE_TYPE_ID);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is greater than or equal to UPDATED_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldNotBeFound("paymentsFileTypeId.greaterThanOrEqual=" + UPDATED_PAYMENTS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPaymentsFileTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is less than or equal to DEFAULT_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldBeFound("paymentsFileTypeId.lessThanOrEqual=" + DEFAULT_PAYMENTS_FILE_TYPE_ID);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is less than or equal to SMALLER_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldNotBeFound("paymentsFileTypeId.lessThanOrEqual=" + SMALLER_PAYMENTS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPaymentsFileTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is less than DEFAULT_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldNotBeFound("paymentsFileTypeId.lessThan=" + DEFAULT_PAYMENTS_FILE_TYPE_ID);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is less than UPDATED_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldBeFound("paymentsFileTypeId.lessThan=" + UPDATED_PAYMENTS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPaymentsFileTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is greater than DEFAULT_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldNotBeFound("paymentsFileTypeId.greaterThan=" + DEFAULT_PAYMENTS_FILE_TYPE_ID);

        // Get all the paymentsFileUploadList where paymentsFileTypeId is greater than SMALLER_PAYMENTS_FILE_TYPE_ID
        defaultPaymentsFileUploadShouldBeFound("paymentsFileTypeId.greaterThan=" + SMALLER_PAYMENTS_FILE_TYPE_ID);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultPaymentsFileUploadShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the paymentsFileUploadList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultPaymentsFileUploadShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadSuccessfulIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadSuccessful not equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultPaymentsFileUploadShouldNotBeFound("uploadSuccessful.notEquals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the paymentsFileUploadList where uploadSuccessful not equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultPaymentsFileUploadShouldBeFound("uploadSuccessful.notEquals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultPaymentsFileUploadShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the paymentsFileUploadList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultPaymentsFileUploadShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadSuccessful is not null
        defaultPaymentsFileUploadShouldBeFound("uploadSuccessful.specified=true");

        // Get all the paymentsFileUploadList where uploadSuccessful is null
        defaultPaymentsFileUploadShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultPaymentsFileUploadShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the paymentsFileUploadList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultPaymentsFileUploadShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadProcessed not equals to DEFAULT_UPLOAD_PROCESSED
        defaultPaymentsFileUploadShouldNotBeFound("uploadProcessed.notEquals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the paymentsFileUploadList where uploadProcessed not equals to UPDATED_UPLOAD_PROCESSED
        defaultPaymentsFileUploadShouldBeFound("uploadProcessed.notEquals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultPaymentsFileUploadShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the paymentsFileUploadList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultPaymentsFileUploadShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadProcessed is not null
        defaultPaymentsFileUploadShouldBeFound("uploadProcessed.specified=true");

        // Get all the paymentsFileUploadList where uploadProcessed is null
        defaultPaymentsFileUploadShouldNotBeFound("uploadProcessed.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadToken equals to DEFAULT_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldBeFound("uploadToken.equals=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the paymentsFileUploadList where uploadToken equals to UPDATED_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldNotBeFound("uploadToken.equals=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadTokenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadToken not equals to DEFAULT_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldNotBeFound("uploadToken.notEquals=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the paymentsFileUploadList where uploadToken not equals to UPDATED_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldBeFound("uploadToken.notEquals=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadTokenIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadToken in DEFAULT_UPLOAD_TOKEN or UPDATED_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldBeFound("uploadToken.in=" + DEFAULT_UPLOAD_TOKEN + "," + UPDATED_UPLOAD_TOKEN);

        // Get all the paymentsFileUploadList where uploadToken equals to UPDATED_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldNotBeFound("uploadToken.in=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadToken is not null
        defaultPaymentsFileUploadShouldBeFound("uploadToken.specified=true");

        // Get all the paymentsFileUploadList where uploadToken is null
        defaultPaymentsFileUploadShouldNotBeFound("uploadToken.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadTokenContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadToken contains DEFAULT_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldBeFound("uploadToken.contains=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the paymentsFileUploadList where uploadToken contains UPDATED_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldNotBeFound("uploadToken.contains=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByUploadTokenNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        // Get all the paymentsFileUploadList where uploadToken does not contain DEFAULT_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldNotBeFound("uploadToken.doesNotContain=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the paymentsFileUploadList where uploadToken does not contain UPDATED_UPLOAD_TOKEN
        defaultPaymentsFileUploadShouldBeFound("uploadToken.doesNotContain=" + UPDATED_UPLOAD_TOKEN);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileUploadsByPlaceholderIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);
        Placeholder placeholder = PlaceholderResourceIT.createEntity(em);
        em.persist(placeholder);
        em.flush();
        paymentsFileUpload.addPlaceholder(placeholder);
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);
        Long placeholderId = placeholder.getId();

        // Get all the paymentsFileUploadList where placeholder equals to placeholderId
        defaultPaymentsFileUploadShouldBeFound("placeholderId.equals=" + placeholderId);

        // Get all the paymentsFileUploadList where placeholder equals to placeholderId + 1
        defaultPaymentsFileUploadShouldNotBeFound("placeholderId.equals=" + (placeholderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentsFileUploadShouldBeFound(String filter) throws Exception {
        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsFileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].paymentsFileTypeId").value(hasItem(DEFAULT_PAYMENTS_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN)));

        // Check, that the count call also returns 1
        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentsFileUploadShouldNotBeFound(String filter) throws Exception {
        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentsFileUpload() throws Exception {
        // Get the paymentsFileUpload
        restPaymentsFileUploadMockMvc.perform(get("/api/payments-file-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentsFileUpload() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        int databaseSizeBeforeUpdate = paymentsFileUploadRepository.findAll().size();

        // Update the paymentsFileUpload
        PaymentsFileUpload updatedPaymentsFileUpload = paymentsFileUploadRepository.findById(paymentsFileUpload.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentsFileUpload are not directly saved in db
        em.detach(updatedPaymentsFileUpload);
        updatedPaymentsFileUpload
            .description(UPDATED_DESCRIPTION)
            .fileName(UPDATED_FILE_NAME)
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .paymentsFileTypeId(UPDATED_PAYMENTS_FILE_TYPE_ID)
            .dataFile(UPDATED_DATA_FILE)
            .dataFileContentType(UPDATED_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .uploadToken(UPDATED_UPLOAD_TOKEN);
        PaymentsFileUploadDTO paymentsFileUploadDTO = paymentsFileUploadMapper.toDto(updatedPaymentsFileUpload);

        restPaymentsFileUploadMockMvc.perform(put("/api/payments-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileUploadDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentsFileUpload in the database
        List<PaymentsFileUpload> paymentsFileUploadList = paymentsFileUploadRepository.findAll();
        assertThat(paymentsFileUploadList).hasSize(databaseSizeBeforeUpdate);
        PaymentsFileUpload testPaymentsFileUpload = paymentsFileUploadList.get(paymentsFileUploadList.size() - 1);
        assertThat(testPaymentsFileUpload.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentsFileUpload.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testPaymentsFileUpload.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testPaymentsFileUpload.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testPaymentsFileUpload.getPaymentsFileTypeId()).isEqualTo(UPDATED_PAYMENTS_FILE_TYPE_ID);
        assertThat(testPaymentsFileUpload.getDataFile()).isEqualTo(UPDATED_DATA_FILE);
        assertThat(testPaymentsFileUpload.getDataFileContentType()).isEqualTo(UPDATED_DATA_FILE_CONTENT_TYPE);
        assertThat(testPaymentsFileUpload.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testPaymentsFileUpload.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
        assertThat(testPaymentsFileUpload.getUploadToken()).isEqualTo(UPDATED_UPLOAD_TOKEN);

        // Validate the PaymentsFileUpload in Elasticsearch
        verify(mockPaymentsFileUploadSearchRepository, times(1)).save(testPaymentsFileUpload);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentsFileUpload() throws Exception {
        int databaseSizeBeforeUpdate = paymentsFileUploadRepository.findAll().size();

        // Create the PaymentsFileUpload
        PaymentsFileUploadDTO paymentsFileUploadDTO = paymentsFileUploadMapper.toDto(paymentsFileUpload);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsFileUploadMockMvc.perform(put("/api/payments-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentsFileUpload in the database
        List<PaymentsFileUpload> paymentsFileUploadList = paymentsFileUploadRepository.findAll();
        assertThat(paymentsFileUploadList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentsFileUpload in Elasticsearch
        verify(mockPaymentsFileUploadSearchRepository, times(0)).save(paymentsFileUpload);
    }

    @Test
    @Transactional
    public void deletePaymentsFileUpload() throws Exception {
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);

        int databaseSizeBeforeDelete = paymentsFileUploadRepository.findAll().size();

        // Delete the paymentsFileUpload
        restPaymentsFileUploadMockMvc.perform(delete("/api/payments-file-uploads/{id}", paymentsFileUpload.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentsFileUpload> paymentsFileUploadList = paymentsFileUploadRepository.findAll();
        assertThat(paymentsFileUploadList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentsFileUpload in Elasticsearch
        verify(mockPaymentsFileUploadSearchRepository, times(1)).deleteById(paymentsFileUpload.getId());
    }

    @Test
    @Transactional
    public void searchPaymentsFileUpload() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        paymentsFileUploadRepository.saveAndFlush(paymentsFileUpload);
        when(mockPaymentsFileUploadSearchRepository.search(queryStringQuery("id:" + paymentsFileUpload.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentsFileUpload), PageRequest.of(0, 1), 1));

        // Search the paymentsFileUpload
        restPaymentsFileUploadMockMvc.perform(get("/api/_search/payments-file-uploads?query=id:" + paymentsFileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsFileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].paymentsFileTypeId").value(hasItem(DEFAULT_PAYMENTS_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN)));
    }
}
