package io.github.erp.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A PaymentLabel.
 */
@Entity
@Table(name = "payment_label")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paymentlabel")
public class PaymentLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false, unique = true)
    private String description;

    @Column(name = "comments")
    private String comments;

    @Column(name = "schedule")
    private LocalDate schedule;

    @ManyToMany(mappedBy = "paymentLabels")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Payment> payments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public PaymentLabel description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public PaymentLabel comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getSchedule() {
        return schedule;
    }

    public PaymentLabel schedule(LocalDate schedule) {
        this.schedule = schedule;
        return this;
    }

    public void setSchedule(LocalDate schedule) {
        this.schedule = schedule;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public PaymentLabel payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public PaymentLabel addPayment(Payment payment) {
        this.payments.add(payment);
        payment.getPaymentLabels().add(this);
        return this;
    }

    public PaymentLabel removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.getPaymentLabels().remove(this);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentLabel)) {
            return false;
        }
        return id != null && id.equals(((PaymentLabel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentLabel{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", comments='" + getComments() + "'" +
            ", schedule='" + getSchedule() + "'" +
            "}";
    }
}
