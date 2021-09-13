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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.erp.domain.PaymentLabel} entity. This class is used
 * in {@link io.github.erp.web.rest.PaymentLabelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-labels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentLabelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter comments;

    private LocalDateFilter schedule;

    private LongFilter paymentId;

    public PaymentLabelCriteria() {
    }

    public PaymentLabelCriteria(PaymentLabelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.schedule = other.schedule == null ? null : other.schedule.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
    }

    @Override
    public PaymentLabelCriteria copy() {
        return new PaymentLabelCriteria(this);
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

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public LocalDateFilter getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateFilter schedule) {
        this.schedule = schedule;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentLabelCriteria that = (PaymentLabelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(schedule, that.schedule) &&
            Objects.equals(paymentId, that.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        comments,
        schedule,
        paymentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentLabelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (schedule != null ? "schedule=" + schedule + ", " : "") +
                (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            "}";
    }

}
