package io.github.erp.service.mapper;


import io.github.erp.domain.*;
import io.github.erp.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlaceholderMapper.class, PaymentLabelMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {


    @Mapping(target = "removePlaceholder", ignore = true)
    @Mapping(target = "removePaymentLabel", ignore = true)

    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
