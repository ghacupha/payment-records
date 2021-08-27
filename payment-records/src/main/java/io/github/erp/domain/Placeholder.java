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
import java.util.HashSet;
import java.util.Set;

/**
 * A Placeholder.
 */
@Entity
@Table(name = "placeholder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "placeholder")
public class Placeholder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false, unique = true)
    private String description;

    
    @Column(name = "token", unique = true)
    private String token;

    @ManyToMany(mappedBy = "placeholders")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<CurrencyTable> currencyTables = new HashSet<>();

    @ManyToMany(mappedBy = "placeholders")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Payment> payments = new HashSet<>();

    @ManyToMany(mappedBy = "placeholders")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<PaymentsFileType> paymentsFileTypes = new HashSet<>();

    @ManyToMany(mappedBy = "placeholders")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<PaymentsFileUpload> paymentsFileUploads = new HashSet<>();

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

    public Placeholder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public Placeholder token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<CurrencyTable> getCurrencyTables() {
        return currencyTables;
    }

    public Placeholder currencyTables(Set<CurrencyTable> currencyTables) {
        this.currencyTables = currencyTables;
        return this;
    }

    public Placeholder addCurrencyTable(CurrencyTable currencyTable) {
        this.currencyTables.add(currencyTable);
        currencyTable.getPlaceholders().add(this);
        return this;
    }

    public Placeholder removeCurrencyTable(CurrencyTable currencyTable) {
        this.currencyTables.remove(currencyTable);
        currencyTable.getPlaceholders().remove(this);
        return this;
    }

    public void setCurrencyTables(Set<CurrencyTable> currencyTables) {
        this.currencyTables = currencyTables;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Placeholder payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Placeholder addPayment(Payment payment) {
        this.payments.add(payment);
        payment.getPlaceholders().add(this);
        return this;
    }

    public Placeholder removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.getPlaceholders().remove(this);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<PaymentsFileType> getPaymentsFileTypes() {
        return paymentsFileTypes;
    }

    public Placeholder paymentsFileTypes(Set<PaymentsFileType> paymentsFileTypes) {
        this.paymentsFileTypes = paymentsFileTypes;
        return this;
    }

    public Placeholder addPaymentsFileType(PaymentsFileType paymentsFileType) {
        this.paymentsFileTypes.add(paymentsFileType);
        paymentsFileType.getPlaceholders().add(this);
        return this;
    }

    public Placeholder removePaymentsFileType(PaymentsFileType paymentsFileType) {
        this.paymentsFileTypes.remove(paymentsFileType);
        paymentsFileType.getPlaceholders().remove(this);
        return this;
    }

    public void setPaymentsFileTypes(Set<PaymentsFileType> paymentsFileTypes) {
        this.paymentsFileTypes = paymentsFileTypes;
    }

    public Set<PaymentsFileUpload> getPaymentsFileUploads() {
        return paymentsFileUploads;
    }

    public Placeholder paymentsFileUploads(Set<PaymentsFileUpload> paymentsFileUploads) {
        this.paymentsFileUploads = paymentsFileUploads;
        return this;
    }

    public Placeholder addPaymentsFileUpload(PaymentsFileUpload paymentsFileUpload) {
        this.paymentsFileUploads.add(paymentsFileUpload);
        paymentsFileUpload.getPlaceholders().add(this);
        return this;
    }

    public Placeholder removePaymentsFileUpload(PaymentsFileUpload paymentsFileUpload) {
        this.paymentsFileUploads.remove(paymentsFileUpload);
        paymentsFileUpload.getPlaceholders().remove(this);
        return this;
    }

    public void setPaymentsFileUploads(Set<PaymentsFileUpload> paymentsFileUploads) {
        this.paymentsFileUploads = paymentsFileUploads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Placeholder)) {
            return false;
        }
        return id != null && id.equals(((Placeholder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Placeholder{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
