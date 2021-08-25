package io.github.erp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.erp.domain.PaymentsFileUpload} entity.
 */
public class PaymentsFileUploadDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String fileName;

    private LocalDate periodFrom;

    private LocalDate periodTo;

    @NotNull
    private Long paymentsFileTypeId;

    
    @Lob
    private byte[] dataFile;

    private String dataFileContentType;
    private Boolean uploadSuccessful;

    private Boolean uploadProcessed;

    
    private String uploadToken;

    private Set<PlaceholderDTO> placeholders = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDate getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    public Long getPaymentsFileTypeId() {
        return paymentsFileTypeId;
    }

    public void setPaymentsFileTypeId(Long paymentsFileTypeId) {
        this.paymentsFileTypeId = paymentsFileTypeId;
    }

    public byte[] getDataFile() {
        return dataFile;
    }

    public void setDataFile(byte[] dataFile) {
        this.dataFile = dataFile;
    }

    public String getDataFileContentType() {
        return dataFileContentType;
    }

    public void setDataFileContentType(String dataFileContentType) {
        this.dataFileContentType = dataFileContentType;
    }

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Boolean isUploadProcessed() {
        return uploadProcessed;
    }

    public void setUploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public void setUploadToken(String uploadToken) {
        this.uploadToken = uploadToken;
    }

    public Set<PlaceholderDTO> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Set<PlaceholderDTO> placeholders) {
        this.placeholders = placeholders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentsFileUploadDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentsFileUploadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentsFileUploadDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", periodFrom='" + getPeriodFrom() + "'" +
            ", periodTo='" + getPeriodTo() + "'" +
            ", paymentsFileTypeId=" + getPaymentsFileTypeId() +
            ", dataFile='" + getDataFile() + "'" +
            ", uploadSuccessful='" + isUploadSuccessful() + "'" +
            ", uploadProcessed='" + isUploadProcessed() + "'" +
            ", uploadToken='" + getUploadToken() + "'" +
            ", placeholders='" + getPlaceholders() + "'" +
            "}";
    }
}
