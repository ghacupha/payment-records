package io.github.erp.service.mapper;


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
