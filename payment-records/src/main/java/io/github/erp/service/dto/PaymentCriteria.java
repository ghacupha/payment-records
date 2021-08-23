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
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.erp.domain.Payment} entity. This class is used
 * in {@link io.github.erp.web.rest.PaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paymentsCategory;

    private StringFilter transactionNumber;

    private LocalDateFilter transactionDate;

    private StringFilter transactionCurrency;

    private BigDecimalFilter transactionAmount;

    private StringFilter beneficiary;

    public PaymentCriteria() {
    }

    public PaymentCriteria(PaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paymentsCategory = other.paymentsCategory == null ? null : other.paymentsCategory.copy();
        this.transactionNumber = other.transactionNumber == null ? null : other.transactionNumber.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.transactionCurrency = other.transactionCurrency == null ? null : other.transactionCurrency.copy();
        this.transactionAmount = other.transactionAmount == null ? null : other.transactionAmount.copy();
        this.beneficiary = other.beneficiary == null ? null : other.beneficiary.copy();
    }

    @Override
    public PaymentCriteria copy() {
        return new PaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPaymentsCategory() {
        return paymentsCategory;
    }

    public void setPaymentsCategory(StringFilter paymentsCategory) {
        this.paymentsCategory = paymentsCategory;
    }

    public StringFilter getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(StringFilter transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public LocalDateFilter getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateFilter transactionDate) {
        this.transactionDate = transactionDate;
    }

    public StringFilter getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(StringFilter transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public BigDecimalFilter getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimalFilter transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public StringFilter getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(StringFilter beneficiary) {
        this.beneficiary = beneficiary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentCriteria that = (PaymentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(paymentsCategory, that.paymentsCategory) &&
            Objects.equals(transactionNumber, that.transactionNumber) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(transactionCurrency, that.transactionCurrency) &&
            Objects.equals(transactionAmount, that.transactionAmount) &&
            Objects.equals(beneficiary, that.beneficiary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        paymentsCategory,
        transactionNumber,
        transactionDate,
        transactionCurrency,
        transactionAmount,
        beneficiary
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (paymentsCategory != null ? "paymentsCategory=" + paymentsCategory + ", " : "") +
                (transactionNumber != null ? "transactionNumber=" + transactionNumber + ", " : "") +
                (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
                (transactionCurrency != null ? "transactionCurrency=" + transactionCurrency + ", " : "") +
                (transactionAmount != null ? "transactionAmount=" + transactionAmount + ", " : "") +
                (beneficiary != null ? "beneficiary=" + beneficiary + ", " : "") +
            "}";
    }

}
