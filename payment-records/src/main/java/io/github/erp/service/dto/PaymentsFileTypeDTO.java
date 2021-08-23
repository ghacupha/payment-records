package io.github.erp.service.dto;

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

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Lob;
import io.github.erp.domain.enumeration.PaymentsFileMediumTypes;
import io.github.erp.domain.enumeration.PaymentsFileModelType;

/**
 * A DTO for the {@link io.github.erp.domain.PaymentsFileType} entity.
 */
public class PaymentsFileTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String paymentsFileTypeName;

    @NotNull
    private PaymentsFileMediumTypes paymentsFileMediumType;

    private String description;

    @Lob
    private byte[] fileTemplate;

    private String fileTemplateContentType;
    private PaymentsFileModelType paymentsfileType;

    private Set<PlaceholderDTO> placeholders = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentsFileTypeName() {
        return paymentsFileTypeName;
    }

    public void setPaymentsFileTypeName(String paymentsFileTypeName) {
        this.paymentsFileTypeName = paymentsFileTypeName;
    }

    public PaymentsFileMediumTypes getPaymentsFileMediumType() {
        return paymentsFileMediumType;
    }

    public void setPaymentsFileMediumType(PaymentsFileMediumTypes paymentsFileMediumType) {
        this.paymentsFileMediumType = paymentsFileMediumType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFileTemplate() {
        return fileTemplate;
    }

    public void setFileTemplate(byte[] fileTemplate) {
        this.fileTemplate = fileTemplate;
    }

    public String getFileTemplateContentType() {
        return fileTemplateContentType;
    }

    public void setFileTemplateContentType(String fileTemplateContentType) {
        this.fileTemplateContentType = fileTemplateContentType;
    }

    public PaymentsFileModelType getPaymentsfileType() {
        return paymentsfileType;
    }

    public void setPaymentsfileType(PaymentsFileModelType paymentsfileType) {
        this.paymentsfileType = paymentsfileType;
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
        if (!(o instanceof PaymentsFileTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentsFileTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentsFileTypeDTO{" +
            "id=" + getId() +
            ", paymentsFileTypeName='" + getPaymentsFileTypeName() + "'" +
            ", paymentsFileMediumType='" + getPaymentsFileMediumType() + "'" +
            ", description='" + getDescription() + "'" +
            ", fileTemplate='" + getFileTemplate() + "'" +
            ", paymentsfileType='" + getPaymentsfileType() + "'" +
            ", placeholders='" + getPlaceholders() + "'" +
            "}";
    }
}
