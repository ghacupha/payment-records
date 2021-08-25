package io.github.erp.service.mapper;


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
