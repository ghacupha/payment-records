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
import io.github.erp.service.dto.PaymentsFileUploadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentsFileUpload} and its DTO {@link PaymentsFileUploadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentsFileUploadMapper extends EntityMapper<PaymentsFileUploadDTO, PaymentsFileUpload> {



    default PaymentsFileUpload fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentsFileUpload paymentsFileUpload = new PaymentsFileUpload();
        paymentsFileUpload.setId(id);
        return paymentsFileUpload;
    }
}
