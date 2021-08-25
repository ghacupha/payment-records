package io.github.erp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.github.erp.domain.enumeration.CurrencyLocality;

/**
 * A CurrencyTable.
 */
@Entity
@Table(name = "currency_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "currencytable")
public class CurrencyTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 3, max = 3)
    @Column(name = "currency_code", length = 3, unique = true)
    private String currencyCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "locality", nullable = false)
    private CurrencyLocality locality;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "country")
    private String country;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "currency_table_placeholder",
               joinColumns = @JoinColumn(name = "currency_table_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "placeholder_id", referencedColumnName = "id"))
    private Set<Placeholder> placeholders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public CurrencyTable currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CurrencyLocality getLocality() {
        return locality;
    }

    public CurrencyTable locality(CurrencyLocality locality) {
        this.locality = locality;
        return this;
    }

    public void setLocality(CurrencyLocality locality) {
        this.locality = locality;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public CurrencyTable currencyName(String currencyName) {
        this.currencyName = currencyName;
        return this;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCountry() {
        return country;
    }

    public CurrencyTable country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Placeholder> getPlaceholders() {
        return placeholders;
    }

    public CurrencyTable placeholders(Set<Placeholder> placeholders) {
        this.placeholders = placeholders;
        return this;
    }

    public CurrencyTable addPlaceholder(Placeholder placeholder) {
        this.placeholders.add(placeholder);
        placeholder.getCurrencyTables().add(this);
        return this;
    }

    public CurrencyTable removePlaceholder(Placeholder placeholder) {
        this.placeholders.remove(placeholder);
        placeholder.getCurrencyTables().remove(this);
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
        if (!(o instanceof CurrencyTable)) {
            return false;
        }
        return id != null && id.equals(((CurrencyTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyTable{" +
            "id=" + getId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", locality='" + getLocality() + "'" +
            ", currencyName='" + getCurrencyName() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
