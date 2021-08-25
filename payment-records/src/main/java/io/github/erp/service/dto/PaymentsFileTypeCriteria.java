package io.github.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.erp.domain.enumeration.PaymentsFileMediumTypes;
import io.github.erp.domain.enumeration.PaymentsFileModelType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.erp.domain.PaymentsFileType} entity. This class is used
 * in {@link io.github.erp.web.rest.PaymentsFileTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments-file-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentsFileTypeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PaymentsFileMediumTypes
     */
    public static class PaymentsFileMediumTypesFilter extends Filter<PaymentsFileMediumTypes> {

        public PaymentsFileMediumTypesFilter() {
        }

        public PaymentsFileMediumTypesFilter(PaymentsFileMediumTypesFilter filter) {
            super(filter);
        }

        @Override
        public PaymentsFileMediumTypesFilter copy() {
            return new PaymentsFileMediumTypesFilter(this);
        }

    }
    /**
     * Class for filtering PaymentsFileModelType
     */
    public static class PaymentsFileModelTypeFilter extends Filter<PaymentsFileModelType> {

        public PaymentsFileModelTypeFilter() {
        }

        public PaymentsFileModelTypeFilter(PaymentsFileModelTypeFilter filter) {
            super(filter);
        }

        @Override
        public PaymentsFileModelTypeFilter copy() {
            return new PaymentsFileModelTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paymentsFileTypeName;

    private PaymentsFileMediumTypesFilter paymentsFileMediumType;

    private StringFilter description;

    private PaymentsFileModelTypeFilter paymentsfileType;

    private LongFilter placeholderId;

    public PaymentsFileTypeCriteria() {
    }

    public PaymentsFileTypeCriteria(PaymentsFileTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paymentsFileTypeName = other.paymentsFileTypeName == null ? null : other.paymentsFileTypeName.copy();
        this.paymentsFileMediumType = other.paymentsFileMediumType == null ? null : other.paymentsFileMediumType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.paymentsfileType = other.paymentsfileType == null ? null : other.paymentsfileType.copy();
        this.placeholderId = other.placeholderId == null ? null : other.placeholderId.copy();
    }

    @Override
    public PaymentsFileTypeCriteria copy() {
        return new PaymentsFileTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPaymentsFileTypeName() {
        return paymentsFileTypeName;
    }

    public void setPaymentsFileTypeName(StringFilter paymentsFileTypeName) {
        this.paymentsFileTypeName = paymentsFileTypeName;
    }

    public PaymentsFileMediumTypesFilter getPaymentsFileMediumType() {
        return paymentsFileMediumType;
    }

    public void setPaymentsFileMediumType(PaymentsFileMediumTypesFilter paymentsFileMediumType) {
        this.paymentsFileMediumType = paymentsFileMediumType;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public PaymentsFileModelTypeFilter getPaymentsfileType() {
        return paymentsfileType;
    }

    public void setPaymentsfileType(PaymentsFileModelTypeFilter paymentsfileType) {
        this.paymentsfileType = paymentsfileType;
    }

    public LongFilter getPlaceholderId() {
        return placeholderId;
    }

    public void setPlaceholderId(LongFilter placeholderId) {
        this.placeholderId = placeholderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentsFileTypeCriteria that = (PaymentsFileTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(paymentsFileTypeName, that.paymentsFileTypeName) &&
            Objects.equals(paymentsFileMediumType, that.paymentsFileMediumType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(paymentsfileType, that.paymentsfileType) &&
            Objects.equals(placeholderId, that.placeholderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        paymentsFileTypeName,
        paymentsFileMediumType,
        description,
        paymentsfileType,
        placeholderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentsFileTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (paymentsFileTypeName != null ? "paymentsFileTypeName=" + paymentsFileTypeName + ", " : "") +
                (paymentsFileMediumType != null ? "paymentsFileMediumType=" + paymentsFileMediumType + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (paymentsfileType != null ? "paymentsfileType=" + paymentsfileType + ", " : "") +
                (placeholderId != null ? "placeholderId=" + placeholderId + ", " : "") +
            "}";
    }

}
