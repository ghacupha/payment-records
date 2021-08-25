package io.github.erp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "payments_category")
    private String paymentsCategory;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "transaction_currency")
    private String transactionCurrency;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "transaction_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal transactionAmount;

    @Column(name = "beneficiary")
    private String beneficiary;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "payment_placeholder",
               joinColumns = @JoinColumn(name = "payment_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "placeholder_id", referencedColumnName = "id"))
    private Set<Placeholder> placeholders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentsCategory() {
        return paymentsCategory;
    }

    public Payment paymentsCategory(String paymentsCategory) {
        this.paymentsCategory = paymentsCategory;
        return this;
    }

    public void setPaymentsCategory(String paymentsCategory) {
        this.paymentsCategory = paymentsCategory;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public Payment transactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
        return this;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Payment transactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public Payment transactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
        return this;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public Payment transactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public Payment beneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
        return this;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Set<Placeholder> getPlaceholders() {
        return placeholders;
    }

    public Payment placeholders(Set<Placeholder> placeholders) {
        this.placeholders = placeholders;
        return this;
    }

    public Payment addPlaceholder(Placeholder placeholder) {
        this.placeholders.add(placeholder);
        placeholder.getPayments().add(this);
        return this;
    }

    public Payment removePlaceholder(Placeholder placeholder) {
        this.placeholders.remove(placeholder);
        placeholder.getPayments().remove(this);
        return this;
    }

    public void setPlaceholders(Set<Placeholder> placeholders) {
        this.placeholders = placeholders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
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
