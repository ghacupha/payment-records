package io.github.erp.service.mapper;


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
