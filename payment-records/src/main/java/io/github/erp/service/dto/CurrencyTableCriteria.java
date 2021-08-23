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
import io.github.erp.domain.enumeration.CurrencyLocality;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.erp.domain.CurrencyTable} entity. This class is used
 * in {@link io.github.erp.web.rest.CurrencyTableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currency-tables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyTableCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CurrencyLocality
     */
    public static class CurrencyLocalityFilter extends Filter<CurrencyLocality> {

        public CurrencyLocalityFilter() {
        }

        public CurrencyLocalityFilter(CurrencyLocalityFilter filter) {
            super(filter);
        }

        @Override
        public CurrencyLocalityFilter copy() {
            return new CurrencyLocalityFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter currencyCode;

    private CurrencyLocalityFilter locality;

    private StringFilter currencyName;

    private StringFilter country;

    public CurrencyTableCriteria() {
    }

    public CurrencyTableCriteria(CurrencyTableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.currencyCode = other.currencyCode == null ? null : other.currencyCode.copy();
        this.locality = other.locality == null ? null : other.locality.copy();
        this.currencyName = other.currencyName == null ? null : other.currencyName.copy();
        this.country = other.country == null ? null : other.country.copy();
    }

    @Override
    public CurrencyTableCriteria copy() {
        return new CurrencyTableCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(StringFilter currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CurrencyLocalityFilter getLocality() {
        return locality;
    }

    public void setLocality(CurrencyLocalityFilter locality) {
        this.locality = locality;
    }

    public StringFilter getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(StringFilter currencyName) {
        this.currencyName = currencyName;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyTableCriteria that = (CurrencyTableCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(currencyCode, that.currencyCode) &&
            Objects.equals(locality, that.locality) &&
            Objects.equals(currencyName, that.currencyName) &&
            Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        currencyCode,
        locality,
        currencyName,
        country
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyTableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (currencyCode != null ? "currencyCode=" + currencyCode + ", " : "") +
                (locality != null ? "locality=" + locality + ", " : "") +
                (currencyName != null ? "currencyName=" + currencyName + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
            "}";
    }

}
