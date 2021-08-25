package io.github.erp.service.dto;

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

/**
 * Criteria class for the {@link io.github.erp.domain.Placeholder} entity. This class is used
 * in {@link io.github.erp.web.rest.PlaceholderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /placeholders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlaceholderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter token;

    private LongFilter currencyTableId;

    private LongFilter paymentId;

    private LongFilter paymentsFileTypeId;

    private LongFilter paymentsFileUploadId;

    public PlaceholderCriteria() {
    }

    public PlaceholderCriteria(PlaceholderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.token = other.token == null ? null : other.token.copy();
        this.currencyTableId = other.currencyTableId == null ? null : other.currencyTableId.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.paymentsFileTypeId = other.paymentsFileTypeId == null ? null : other.paymentsFileTypeId.copy();
        this.paymentsFileUploadId = other.paymentsFileUploadId == null ? null : other.paymentsFileUploadId.copy();
    }

    @Override
    public PlaceholderCriteria copy() {
        return new PlaceholderCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getToken() {
        return token;
    }

    public void setToken(StringFilter token) {
        this.token = token;
    }

    public LongFilter getCurrencyTableId() {
        return currencyTableId;
    }

    public void setCurrencyTableId(LongFilter currencyTableId) {
        this.currencyTableId = currencyTableId;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    public LongFilter getPaymentsFileTypeId() {
        return paymentsFileTypeId;
    }

    public void setPaymentsFileTypeId(LongFilter paymentsFileTypeId) {
        this.paymentsFileTypeId = paymentsFileTypeId;
    }

    public LongFilter getPaymentsFileUploadId() {
        return paymentsFileUploadId;
    }

    public void setPaymentsFileUploadId(LongFilter paymentsFileUploadId) {
        this.paymentsFileUploadId = paymentsFileUploadId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlaceholderCriteria that = (PlaceholderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(token, that.token) &&
            Objects.equals(currencyTableId, that.currencyTableId) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(paymentsFileTypeId, that.paymentsFileTypeId) &&
            Objects.equals(paymentsFileUploadId, that.paymentsFileUploadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        token,
        currencyTableId,
        paymentId,
        paymentsFileTypeId,
        paymentsFileUploadId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaceholderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (token != null ? "token=" + token + ", " : "") +
                (currencyTableId != null ? "currencyTableId=" + currencyTableId + ", " : "") +
                (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
                (paymentsFileTypeId != null ? "paymentsFileTypeId=" + paymentsFileTypeId + ", " : "") +
                (paymentsFileUploadId != null ? "paymentsFileUploadId=" + paymentsFileUploadId + ", " : "") +
            "}";
    }

}
