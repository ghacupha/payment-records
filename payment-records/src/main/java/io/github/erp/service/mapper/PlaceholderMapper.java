package io.github.erp.service.mapper;

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


import io.github.erp.domain.*;
import io.github.erp.service.dto.PlaceholderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Placeholder} and its DTO {@link PlaceholderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaceholderMapper extends EntityMapper<PlaceholderDTO, Placeholder> {


    @Mapping(target = "currencyTables", ignore = true)
    @Mapping(target = "removeCurrencyTable", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "removePayment", ignore = true)
    @Mapping(target = "paymentsFileTypes", ignore = true)
    @Mapping(target = "removePaymentsFileType", ignore = true)
    @Mapping(target = "paymentsFileUploads", ignore = true)
    @Mapping(target = "removePaymentsFileUpload", ignore = true)
    Placeholder toEntity(PlaceholderDTO placeholderDTO);

    default Placeholder fromId(Long id) {
        if (id == null) {
            return null;
        }
        Placeholder placeholder = new Placeholder();
        placeholder.setId(id);
        return placeholder;
    }
}
