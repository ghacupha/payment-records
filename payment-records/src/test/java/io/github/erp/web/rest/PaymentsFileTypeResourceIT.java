package io.github.erp.web.rest;

import io.github.erp.PaymentRecordsApp;
import io.github.erp.domain.PaymentsFileType;
import io.github.erp.domain.Placeholder;
import io.github.erp.repository.PaymentsFileTypeRepository;
import io.github.erp.repository.search.PaymentsFileTypeSearchRepository;
import io.github.erp.service.PaymentsFileTypeService;
import io.github.erp.service.dto.PaymentsFileTypeDTO;
import io.github.erp.service.mapper.PaymentsFileTypeMapper;
import io.github.erp.service.dto.PaymentsFileTypeCriteria;
import io.github.erp.service.PaymentsFileTypeQueryService;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.erp.domain.enumeration.PaymentsFileMediumTypes;
import io.github.erp.domain.enumeration.PaymentsFileModelType;
/**
 * Integration tests for the {@link PaymentsFileTypeResource} REST controller.
 */
@SpringBootTest(classes = PaymentRecordsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentsFileTypeResourceIT {

    private static final String DEFAULT_PAYMENTS_FILE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENTS_FILE_TYPE_NAME = "BBBBBBBBBB";

    private static final PaymentsFileMediumTypes DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE = PaymentsFileMediumTypes.EXCEL;
    private static final PaymentsFileMediumTypes UPDATED_PAYMENTS_FILE_MEDIUM_TYPE = PaymentsFileMediumTypes.EXCEL_XLS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final PaymentsFileModelType DEFAULT_PAYMENTSFILE_TYPE = PaymentsFileModelType.CURRENCY_LIST;
    private static final PaymentsFileModelType UPDATED_PAYMENTSFILE_TYPE = PaymentsFileModelType.PAYMENTS_DATA;

    @Autowired
    private PaymentsFileTypeRepository paymentsFileTypeRepository;

    @Mock
    private PaymentsFileTypeRepository paymentsFileTypeRepositoryMock;

    @Autowired
    private PaymentsFileTypeMapper paymentsFileTypeMapper;

    @Mock
    private PaymentsFileTypeService paymentsFileTypeServiceMock;

    @Autowired
    private PaymentsFileTypeService paymentsFileTypeService;

    /**
     * This repository is mocked in the io.github.erp.repository.search test package.
     *
     * @see io.github.erp.repository.search.PaymentsFileTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentsFileTypeSearchRepository mockPaymentsFileTypeSearchRepository;

    @Autowired
    private PaymentsFileTypeQueryService paymentsFileTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentsFileTypeMockMvc;

    private PaymentsFileType paymentsFileType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentsFileType createEntity(EntityManager em) {
        PaymentsFileType paymentsFileType = new PaymentsFileType()
            .paymentsFileTypeName(DEFAULT_PAYMENTS_FILE_TYPE_NAME)
            .paymentsFileMediumType(DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .fileTemplate(DEFAULT_FILE_TEMPLATE)
            .fileTemplateContentType(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE)
            .paymentsfileType(DEFAULT_PAYMENTSFILE_TYPE);
        return paymentsFileType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentsFileType createUpdatedEntity(EntityManager em) {
        PaymentsFileType paymentsFileType = new PaymentsFileType()
            .paymentsFileTypeName(UPDATED_PAYMENTS_FILE_TYPE_NAME)
            .paymentsFileMediumType(UPDATED_PAYMENTS_FILE_MEDIUM_TYPE)
            .description(UPDATED_DESCRIPTION)
            .fileTemplate(UPDATED_FILE_TEMPLATE)
            .fileTemplateContentType(UPDATED_FILE_TEMPLATE_CONTENT_TYPE)
            .paymentsfileType(UPDATED_PAYMENTSFILE_TYPE);
        return paymentsFileType;
    }

    @BeforeEach
    public void initTest() {
        paymentsFileType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentsFileType() throws Exception {
        int databaseSizeBeforeCreate = paymentsFileTypeRepository.findAll().size();
        // Create the PaymentsFileType
        PaymentsFileTypeDTO paymentsFileTypeDTO = paymentsFileTypeMapper.toDto(paymentsFileType);
        restPaymentsFileTypeMockMvc.perform(post("/api/payments-file-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentsFileType in the database
        List<PaymentsFileType> paymentsFileTypeList = paymentsFileTypeRepository.findAll();
        assertThat(paymentsFileTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentsFileType testPaymentsFileType = paymentsFileTypeList.get(paymentsFileTypeList.size() - 1);
        assertThat(testPaymentsFileType.getPaymentsFileTypeName()).isEqualTo(DEFAULT_PAYMENTS_FILE_TYPE_NAME);
        assertThat(testPaymentsFileType.getPaymentsFileMediumType()).isEqualTo(DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE);
        assertThat(testPaymentsFileType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentsFileType.getFileTemplate()).isEqualTo(DEFAULT_FILE_TEMPLATE);
        assertThat(testPaymentsFileType.getFileTemplateContentType()).isEqualTo(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE);
        assertThat(testPaymentsFileType.getPaymentsfileType()).isEqualTo(DEFAULT_PAYMENTSFILE_TYPE);

        // Validate the PaymentsFileType in Elasticsearch
        verify(mockPaymentsFileTypeSearchRepository, times(1)).save(testPaymentsFileType);
    }

    @Test
    @Transactional
    public void createPaymentsFileTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentsFileTypeRepository.findAll().size();

        // Create the PaymentsFileType with an existing ID
        paymentsFileType.setId(1L);
        PaymentsFileTypeDTO paymentsFileTypeDTO = paymentsFileTypeMapper.toDto(paymentsFileType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsFileTypeMockMvc.perform(post("/api/payments-file-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentsFileType in the database
        List<PaymentsFileType> paymentsFileTypeList = paymentsFileTypeRepository.findAll();
        assertThat(paymentsFileTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentsFileType in Elasticsearch
        verify(mockPaymentsFileTypeSearchRepository, times(0)).save(paymentsFileType);
    }


    @Test
    @Transactional
    public void checkPaymentsFileTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsFileTypeRepository.findAll().size();
        // set the field null
        paymentsFileType.setPaymentsFileTypeName(null);

        // Create the PaymentsFileType, which fails.
        PaymentsFileTypeDTO paymentsFileTypeDTO = paymentsFileTypeMapper.toDto(paymentsFileType);


        restPaymentsFileTypeMockMvc.perform(post("/api/payments-file-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentsFileType> paymentsFileTypeList = paymentsFileTypeRepository.findAll();
        assertThat(paymentsFileTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentsFileMediumTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsFileTypeRepository.findAll().size();
        // set the field null
        paymentsFileType.setPaymentsFileMediumType(null);

        // Create the PaymentsFileType, which fails.
        PaymentsFileTypeDTO paymentsFileTypeDTO = paymentsFileTypeMapper.toDto(paymentsFileType);


        restPaymentsFileTypeMockMvc.perform(post("/api/payments-file-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentsFileType> paymentsFileTypeList = paymentsFileTypeRepository.findAll();
        assertThat(paymentsFileTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypes() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList
        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsFileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentsFileTypeName").value(hasItem(DEFAULT_PAYMENTS_FILE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].paymentsFileMediumType").value(hasItem(DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileTemplateContentType").value(hasItem(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_TEMPLATE))))
            .andExpect(jsonPath("$.[*].paymentsfileType").value(hasItem(DEFAULT_PAYMENTSFILE_TYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPaymentsFileTypesWithEagerRelationshipsIsEnabled() throws Exception {
        when(paymentsFileTypeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types?eagerload=true"))
            .andExpect(status().isOk());

        verify(paymentsFileTypeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPaymentsFileTypesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(paymentsFileTypeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types?eagerload=true"))
            .andExpect(status().isOk());

        verify(paymentsFileTypeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPaymentsFileType() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get the paymentsFileType
        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types/{id}", paymentsFileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentsFileType.getId().intValue()))
            .andExpect(jsonPath("$.paymentsFileTypeName").value(DEFAULT_PAYMENTS_FILE_TYPE_NAME))
            .andExpect(jsonPath("$.paymentsFileMediumType").value(DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fileTemplateContentType").value(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileTemplate").value(Base64Utils.encodeToString(DEFAULT_FILE_TEMPLATE)))
            .andExpect(jsonPath("$.paymentsfileType").value(DEFAULT_PAYMENTSFILE_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getPaymentsFileTypesByIdFiltering() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        Long id = paymentsFileType.getId();

        defaultPaymentsFileTypeShouldBeFound("id.equals=" + id);
        defaultPaymentsFileTypeShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentsFileTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentsFileTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentsFileTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentsFileTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileTypeName equals to DEFAULT_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldBeFound("paymentsFileTypeName.equals=" + DEFAULT_PAYMENTS_FILE_TYPE_NAME);

        // Get all the paymentsFileTypeList where paymentsFileTypeName equals to UPDATED_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileTypeName.equals=" + UPDATED_PAYMENTS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileTypeName not equals to DEFAULT_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileTypeName.notEquals=" + DEFAULT_PAYMENTS_FILE_TYPE_NAME);

        // Get all the paymentsFileTypeList where paymentsFileTypeName not equals to UPDATED_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldBeFound("paymentsFileTypeName.notEquals=" + UPDATED_PAYMENTS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileTypeName in DEFAULT_PAYMENTS_FILE_TYPE_NAME or UPDATED_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldBeFound("paymentsFileTypeName.in=" + DEFAULT_PAYMENTS_FILE_TYPE_NAME + "," + UPDATED_PAYMENTS_FILE_TYPE_NAME);

        // Get all the paymentsFileTypeList where paymentsFileTypeName equals to UPDATED_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileTypeName.in=" + UPDATED_PAYMENTS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileTypeName is not null
        defaultPaymentsFileTypeShouldBeFound("paymentsFileTypeName.specified=true");

        // Get all the paymentsFileTypeList where paymentsFileTypeName is null
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileTypeNameContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileTypeName contains DEFAULT_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldBeFound("paymentsFileTypeName.contains=" + DEFAULT_PAYMENTS_FILE_TYPE_NAME);

        // Get all the paymentsFileTypeList where paymentsFileTypeName contains UPDATED_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileTypeName.contains=" + UPDATED_PAYMENTS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileTypeName does not contain DEFAULT_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileTypeName.doesNotContain=" + DEFAULT_PAYMENTS_FILE_TYPE_NAME);

        // Get all the paymentsFileTypeList where paymentsFileTypeName does not contain UPDATED_PAYMENTS_FILE_TYPE_NAME
        defaultPaymentsFileTypeShouldBeFound("paymentsFileTypeName.doesNotContain=" + UPDATED_PAYMENTS_FILE_TYPE_NAME);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileMediumTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileMediumType equals to DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE
        defaultPaymentsFileTypeShouldBeFound("paymentsFileMediumType.equals=" + DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE);

        // Get all the paymentsFileTypeList where paymentsFileMediumType equals to UPDATED_PAYMENTS_FILE_MEDIUM_TYPE
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileMediumType.equals=" + UPDATED_PAYMENTS_FILE_MEDIUM_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileMediumTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileMediumType not equals to DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileMediumType.notEquals=" + DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE);

        // Get all the paymentsFileTypeList where paymentsFileMediumType not equals to UPDATED_PAYMENTS_FILE_MEDIUM_TYPE
        defaultPaymentsFileTypeShouldBeFound("paymentsFileMediumType.notEquals=" + UPDATED_PAYMENTS_FILE_MEDIUM_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileMediumTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileMediumType in DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE or UPDATED_PAYMENTS_FILE_MEDIUM_TYPE
        defaultPaymentsFileTypeShouldBeFound("paymentsFileMediumType.in=" + DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE + "," + UPDATED_PAYMENTS_FILE_MEDIUM_TYPE);

        // Get all the paymentsFileTypeList where paymentsFileMediumType equals to UPDATED_PAYMENTS_FILE_MEDIUM_TYPE
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileMediumType.in=" + UPDATED_PAYMENTS_FILE_MEDIUM_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsFileMediumTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsFileMediumType is not null
        defaultPaymentsFileTypeShouldBeFound("paymentsFileMediumType.specified=true");

        // Get all the paymentsFileTypeList where paymentsFileMediumType is null
        defaultPaymentsFileTypeShouldNotBeFound("paymentsFileMediumType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where description equals to DEFAULT_DESCRIPTION
        defaultPaymentsFileTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsFileTypeList where description equals to UPDATED_DESCRIPTION
        defaultPaymentsFileTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultPaymentsFileTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsFileTypeList where description not equals to UPDATED_DESCRIPTION
        defaultPaymentsFileTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPaymentsFileTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the paymentsFileTypeList where description equals to UPDATED_DESCRIPTION
        defaultPaymentsFileTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where description is not null
        defaultPaymentsFileTypeShouldBeFound("description.specified=true");

        // Get all the paymentsFileTypeList where description is null
        defaultPaymentsFileTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsFileTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where description contains DEFAULT_DESCRIPTION
        defaultPaymentsFileTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsFileTypeList where description contains UPDATED_DESCRIPTION
        defaultPaymentsFileTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultPaymentsFileTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the paymentsFileTypeList where description does not contain UPDATED_DESCRIPTION
        defaultPaymentsFileTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsfileTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsfileType equals to DEFAULT_PAYMENTSFILE_TYPE
        defaultPaymentsFileTypeShouldBeFound("paymentsfileType.equals=" + DEFAULT_PAYMENTSFILE_TYPE);

        // Get all the paymentsFileTypeList where paymentsfileType equals to UPDATED_PAYMENTSFILE_TYPE
        defaultPaymentsFileTypeShouldNotBeFound("paymentsfileType.equals=" + UPDATED_PAYMENTSFILE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsfileTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsfileType not equals to DEFAULT_PAYMENTSFILE_TYPE
        defaultPaymentsFileTypeShouldNotBeFound("paymentsfileType.notEquals=" + DEFAULT_PAYMENTSFILE_TYPE);

        // Get all the paymentsFileTypeList where paymentsfileType not equals to UPDATED_PAYMENTSFILE_TYPE
        defaultPaymentsFileTypeShouldBeFound("paymentsfileType.notEquals=" + UPDATED_PAYMENTSFILE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsfileTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsfileType in DEFAULT_PAYMENTSFILE_TYPE or UPDATED_PAYMENTSFILE_TYPE
        defaultPaymentsFileTypeShouldBeFound("paymentsfileType.in=" + DEFAULT_PAYMENTSFILE_TYPE + "," + UPDATED_PAYMENTSFILE_TYPE);

        // Get all the paymentsFileTypeList where paymentsfileType equals to UPDATED_PAYMENTSFILE_TYPE
        defaultPaymentsFileTypeShouldNotBeFound("paymentsfileType.in=" + UPDATED_PAYMENTSFILE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPaymentsfileTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        // Get all the paymentsFileTypeList where paymentsfileType is not null
        defaultPaymentsFileTypeShouldBeFound("paymentsfileType.specified=true");

        // Get all the paymentsFileTypeList where paymentsfileType is null
        defaultPaymentsFileTypeShouldNotBeFound("paymentsfileType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsFileTypesByPlaceholderIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);
        Placeholder placeholder = PlaceholderResourceIT.createEntity(em);
        em.persist(placeholder);
        em.flush();
        paymentsFileType.addPlaceholder(placeholder);
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);
        Long placeholderId = placeholder.getId();

        // Get all the paymentsFileTypeList where placeholder equals to placeholderId
        defaultPaymentsFileTypeShouldBeFound("placeholderId.equals=" + placeholderId);

        // Get all the paymentsFileTypeList where placeholder equals to placeholderId + 1
        defaultPaymentsFileTypeShouldNotBeFound("placeholderId.equals=" + (placeholderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentsFileTypeShouldBeFound(String filter) throws Exception {
        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsFileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentsFileTypeName").value(hasItem(DEFAULT_PAYMENTS_FILE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].paymentsFileMediumType").value(hasItem(DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileTemplateContentType").value(hasItem(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_TEMPLATE))))
            .andExpect(jsonPath("$.[*].paymentsfileType").value(hasItem(DEFAULT_PAYMENTSFILE_TYPE.toString())));

        // Check, that the count call also returns 1
        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentsFileTypeShouldNotBeFound(String filter) throws Exception {
        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentsFileType() throws Exception {
        // Get the paymentsFileType
        restPaymentsFileTypeMockMvc.perform(get("/api/payments-file-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentsFileType() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        int databaseSizeBeforeUpdate = paymentsFileTypeRepository.findAll().size();

        // Update the paymentsFileType
        PaymentsFileType updatedPaymentsFileType = paymentsFileTypeRepository.findById(paymentsFileType.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentsFileType are not directly saved in db
        em.detach(updatedPaymentsFileType);
        updatedPaymentsFileType
            .paymentsFileTypeName(UPDATED_PAYMENTS_FILE_TYPE_NAME)
            .paymentsFileMediumType(UPDATED_PAYMENTS_FILE_MEDIUM_TYPE)
            .description(UPDATED_DESCRIPTION)
            .fileTemplate(UPDATED_FILE_TEMPLATE)
            .fileTemplateContentType(UPDATED_FILE_TEMPLATE_CONTENT_TYPE)
            .paymentsfileType(UPDATED_PAYMENTSFILE_TYPE);
        PaymentsFileTypeDTO paymentsFileTypeDTO = paymentsFileTypeMapper.toDto(updatedPaymentsFileType);

        restPaymentsFileTypeMockMvc.perform(put("/api/payments-file-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentsFileType in the database
        List<PaymentsFileType> paymentsFileTypeList = paymentsFileTypeRepository.findAll();
        assertThat(paymentsFileTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentsFileType testPaymentsFileType = paymentsFileTypeList.get(paymentsFileTypeList.size() - 1);
        assertThat(testPaymentsFileType.getPaymentsFileTypeName()).isEqualTo(UPDATED_PAYMENTS_FILE_TYPE_NAME);
        assertThat(testPaymentsFileType.getPaymentsFileMediumType()).isEqualTo(UPDATED_PAYMENTS_FILE_MEDIUM_TYPE);
        assertThat(testPaymentsFileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentsFileType.getFileTemplate()).isEqualTo(UPDATED_FILE_TEMPLATE);
        assertThat(testPaymentsFileType.getFileTemplateContentType()).isEqualTo(UPDATED_FILE_TEMPLATE_CONTENT_TYPE);
        assertThat(testPaymentsFileType.getPaymentsfileType()).isEqualTo(UPDATED_PAYMENTSFILE_TYPE);

        // Validate the PaymentsFileType in Elasticsearch
        verify(mockPaymentsFileTypeSearchRepository, times(1)).save(testPaymentsFileType);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentsFileType() throws Exception {
        int databaseSizeBeforeUpdate = paymentsFileTypeRepository.findAll().size();

        // Create the PaymentsFileType
        PaymentsFileTypeDTO paymentsFileTypeDTO = paymentsFileTypeMapper.toDto(paymentsFileType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsFileTypeMockMvc.perform(put("/api/payments-file-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentsFileTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentsFileType in the database
        List<PaymentsFileType> paymentsFileTypeList = paymentsFileTypeRepository.findAll();
        assertThat(paymentsFileTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentsFileType in Elasticsearch
        verify(mockPaymentsFileTypeSearchRepository, times(0)).save(paymentsFileType);
    }

    @Test
    @Transactional
    public void deletePaymentsFileType() throws Exception {
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);

        int databaseSizeBeforeDelete = paymentsFileTypeRepository.findAll().size();

        // Delete the paymentsFileType
        restPaymentsFileTypeMockMvc.perform(delete("/api/payments-file-types/{id}", paymentsFileType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentsFileType> paymentsFileTypeList = paymentsFileTypeRepository.findAll();
        assertThat(paymentsFileTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentsFileType in Elasticsearch
        verify(mockPaymentsFileTypeSearchRepository, times(1)).deleteById(paymentsFileType.getId());
    }

    @Test
    @Transactional
    public void searchPaymentsFileType() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        paymentsFileTypeRepository.saveAndFlush(paymentsFileType);
        when(mockPaymentsFileTypeSearchRepository.search(queryStringQuery("id:" + paymentsFileType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentsFileType), PageRequest.of(0, 1), 1));

        // Search the paymentsFileType
        restPaymentsFileTypeMockMvc.perform(get("/api/_search/payments-file-types?query=id:" + paymentsFileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentsFileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentsFileTypeName").value(hasItem(DEFAULT_PAYMENTS_FILE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].paymentsFileMediumType").value(hasItem(DEFAULT_PAYMENTS_FILE_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileTemplateContentType").value(hasItem(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_TEMPLATE))))
            .andExpect(jsonPath("$.[*].paymentsfileType").value(hasItem(DEFAULT_PAYMENTSFILE_TYPE.toString())));
    }
}
