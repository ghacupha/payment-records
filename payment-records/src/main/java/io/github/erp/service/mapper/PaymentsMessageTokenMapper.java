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
import io.github.erp.service.dto.PaymentsMessageTokenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentsMessageToken} and its DTO {@link PaymentsMessageTokenDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentsMessageTokenMapper extends EntityMapper<PaymentsMessageTokenDTO, PaymentsMessageToken> {



    default PaymentsMessageToken fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentsMessageToken paymentsMessageToken = new PaymentsMessageToken();
        paymentsMessageToken.setId(id);
        return paymentsMessageToken;
    }
}
