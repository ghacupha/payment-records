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

/**
 * Criteria class for the {@link io.github.erp.domain.PaymentsMessageToken} entity. This class is used
 * in {@link io.github.erp.web.rest.PaymentsMessageTokenResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments-message-tokens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentsMessageTokenCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LongFilter timeSent;

    private StringFilter tokenValue;

    private BooleanFilter received;

    private BooleanFilter actioned;

    private BooleanFilter contentFullyEnqueued;

    public PaymentsMessageTokenCriteria() {
    }

    public PaymentsMessageTokenCriteria(PaymentsMessageTokenCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.timeSent = other.timeSent == null ? null : other.timeSent.copy();
        this.tokenValue = other.tokenValue == null ? null : other.tokenValue.copy();
        this.received = other.received == null ? null : other.received.copy();
        this.actioned = other.actioned == null ? null : other.actioned.copy();
        this.contentFullyEnqueued = other.contentFullyEnqueued == null ? null : other.contentFullyEnqueued.copy();
    }

    @Override
    public PaymentsMessageTokenCriteria copy() {
        return new PaymentsMessageTokenCriteria(this);
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

    public LongFilter getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LongFilter timeSent) {
        this.timeSent = timeSent;
    }

    public StringFilter getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(StringFilter tokenValue) {
        this.tokenValue = tokenValue;
    }

    public BooleanFilter getReceived() {
        return received;
    }

    public void setReceived(BooleanFilter received) {
        this.received = received;
    }

    public BooleanFilter getActioned() {
        return actioned;
    }

    public void setActioned(BooleanFilter actioned) {
        this.actioned = actioned;
    }

    public BooleanFilter getContentFullyEnqueued() {
        return contentFullyEnqueued;
    }

    public void setContentFullyEnqueued(BooleanFilter contentFullyEnqueued) {
        this.contentFullyEnqueued = contentFullyEnqueued;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentsMessageTokenCriteria that = (PaymentsMessageTokenCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(timeSent, that.timeSent) &&
            Objects.equals(tokenValue, that.tokenValue) &&
            Objects.equals(received, that.received) &&
            Objects.equals(actioned, that.actioned) &&
            Objects.equals(contentFullyEnqueued, that.contentFullyEnqueued);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        timeSent,
        tokenValue,
        received,
        actioned,
        contentFullyEnqueued
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentsMessageTokenCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (timeSent != null ? "timeSent=" + timeSent + ", " : "") +
                (tokenValue != null ? "tokenValue=" + tokenValue + ", " : "") +
                (received != null ? "received=" + received + ", " : "") +
                (actioned != null ? "actioned=" + actioned + ", " : "") +
                (contentFullyEnqueued != null ? "contentFullyEnqueued=" + contentFullyEnqueued + ", " : "") +
            "}";
    }

}
