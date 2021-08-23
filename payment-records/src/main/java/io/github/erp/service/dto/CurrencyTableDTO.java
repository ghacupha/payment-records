package io.github.erp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import io.github.erp.domain.enumeration.CurrencyLocality;

/**
 * A DTO for the {@link io.github.erp.domain.CurrencyTable} entity.
 */
public class CurrencyTableDTO implements Serializable {
    
    private Long id;

    @Size(min = 3, max = 3)
    private String currencyCode;

    @NotNull
    private CurrencyLocality locality;

    private String currencyName;

    private String country;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CurrencyLocality getLocality() {
        return locality;
    }

    public void setLocality(CurrencyLocality locality) {
        this.locality = locality;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyTableDTO)) {
            return false;
        }

        return id != null && id.equals(((CurrencyTableDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyTableDTO{" +
            "id=" + getId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", locality='" + getLocality() + "'" +
            ", currencyName='" + getCurrencyName() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
