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
import io.github.erp.service.dto.PaymentsFileTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentsFileType} and its DTO {@link PaymentsFileTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlaceholderMapper.class})
public interface PaymentsFileTypeMapper extends EntityMapper<PaymentsFileTypeDTO, PaymentsFileType> {


    @Mapping(target = "removePlaceholder", ignore = true)

    default PaymentsFileType fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentsFileType paymentsFileType = new PaymentsFileType();
        paymentsFileType.setId(id);
        return paymentsFileType;
    }
}
