package io.github.erp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import io.github.erp.domain.enumeration.PaymentsFileMediumTypes;

import io.github.erp.domain.enumeration.PaymentsFileModelType;

/**
 * A PaymentsFileType.
 */
@Entity
@Table(name = "payments_file_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paymentsfiletype")
public class PaymentsFileType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "payments_file_type_name", nullable = false, unique = true)
    private String paymentsFileTypeName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payments_file_medium_type", nullable = false)
    private PaymentsFileMediumTypes paymentsFileMediumType;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "file_template")
    private byte[] fileTemplate;

    @Column(name = "file_template_content_type")
    private String fileTemplateContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentsfile_type")
    private PaymentsFileModelType paymentsfileType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentsFileTypeName() {
        return paymentsFileTypeName;
    }

    public PaymentsFileType paymentsFileTypeName(String paymentsFileTypeName) {
        this.paymentsFileTypeName = paymentsFileTypeName;
        return this;
    }

    public void setPaymentsFileTypeName(String paymentsFileTypeName) {
        this.paymentsFileTypeName = paymentsFileTypeName;
    }

    public PaymentsFileMediumTypes getPaymentsFileMediumType() {
        return paymentsFileMediumType;
    }

    public PaymentsFileType paymentsFileMediumType(PaymentsFileMediumTypes paymentsFileMediumType) {
        this.paymentsFileMediumType = paymentsFileMediumType;
        return this;
    }

    public void setPaymentsFileMediumType(PaymentsFileMediumTypes paymentsFileMediumType) {
        this.paymentsFileMediumType = paymentsFileMediumType;
    }

    public String getDescription() {
        return description;
    }

    public PaymentsFileType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFileTemplate() {
        return fileTemplate;
    }

    public PaymentsFileType fileTemplate(byte[] fileTemplate) {
        this.fileTemplate = fileTemplate;
        return this;
    }

    public void setFileTemplate(byte[] fileTemplate) {
        this.fileTemplate = fileTemplate;
    }

    public String getFileTemplateContentType() {
        return fileTemplateContentType;
    }

    public PaymentsFileType fileTemplateContentType(String fileTemplateContentType) {
        this.fileTemplateContentType = fileTemplateContentType;
        return this;
    }

    public void setFileTemplateContentType(String fileTemplateContentType) {
        this.fileTemplateContentType = fileTemplateContentType;
    }

    public PaymentsFileModelType getPaymentsfileType() {
        return paymentsfileType;
    }

    public PaymentsFileType paymentsfileType(PaymentsFileModelType paymentsfileType) {
        this.paymentsfileType = paymentsfileType;
        return this;
    }

    public void setPaymentsfileType(PaymentsFileModelType paymentsfileType) {
        this.paymentsfileType = paymentsfileType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentsFileType)) {
            return false;
        }
        return id != null && id.equals(((PaymentsFileType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentsFileType{" +
            "id=" + getId() +
            ", paymentsFileTypeName='" + getPaymentsFileTypeName() + "'" +
            ", paymentsFileMediumType='" + getPaymentsFileMediumType() + "'" +
            ", description='" + getDescription() + "'" +
            ", fileTemplate='" + getFileTemplate() + "'" +
            ", fileTemplateContentType='" + getFileTemplateContentType() + "'" +
            ", paymentsfileType='" + getPaymentsfileType() + "'" +
            "}";
    }
}
