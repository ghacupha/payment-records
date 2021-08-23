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
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link io.github.erp.domain.Payment} entity.
 */
public class PaymentDTO implements Serializable {
    
    private Long id;

    private String paymentsCategory;

    private String transactionNumber;

    private LocalDate transactionDate;

    private String transactionCurrency;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal transactionAmount;

    private String beneficiary;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentsCategory() {
        return paymentsCategory;
    }

    public void setPaymentsCategory(String paymentsCategory) {
        this.paymentsCategory = paymentsCategory;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", paymentsCategory='" + getPaymentsCategory() + "'" +
            ", transactionNumber='" + getTransactionNumber() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", transactionCurrency='" + getTransactionCurrency() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", beneficiary='" + getBeneficiary() + "'" +
            "}";
    }
}
