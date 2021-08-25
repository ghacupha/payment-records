package io.github.erp.service.mapper;


import io.github.erp.domain.*;
import io.github.erp.service.dto.PaymentsFileUploadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentsFileUpload} and its DTO {@link PaymentsFileUploadDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlaceholderMapper.class})
public interface PaymentsFileUploadMapper extends EntityMapper<PaymentsFileUploadDTO, PaymentsFileUpload> {


    @Mapping(target = "removePlaceholder", ignore = true)

    default PaymentsFileUpload fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentsFileUpload paymentsFileUpload = new PaymentsFileUpload();
        paymentsFileUpload.setId(id);
        return paymentsFileUpload;
    }
}
