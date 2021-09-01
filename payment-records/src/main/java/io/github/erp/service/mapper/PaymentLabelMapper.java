package io.github.erp.service.mapper;


import io.github.erp.domain.*;
import io.github.erp.service.dto.PaymentLabelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentLabel} and its DTO {@link PaymentLabelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentLabelMapper extends EntityMapper<PaymentLabelDTO, PaymentLabel> {


    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "removePayment", ignore = true)
    PaymentLabel toEntity(PaymentLabelDTO paymentLabelDTO);

    default PaymentLabel fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentLabel paymentLabel = new PaymentLabel();
        paymentLabel.setId(id);
        return paymentLabel;
    }
}
