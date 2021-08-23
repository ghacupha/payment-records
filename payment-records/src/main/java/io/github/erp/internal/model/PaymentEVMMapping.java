package io.github.erp.internal.model;

import io.github.erp.domain.enumeration.CurrencyLocality;
import io.github.erp.internal.AppConstants;
import io.github.erp.internal.Mapping;
import io.github.erp.service.dto.PaymentDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.github.erp.internal.AppConstants.DATETIME_FORMATTER;
import static net.logstash.logback.encoder.org.apache.commons.lang3.math.NumberUtils.toDouble;
import static net.logstash.logback.encoder.org.apache.commons.lang3.math.NumberUtils.toScaledBigDecimal;

@Mapper(componentModel = "spring", uses = {})
public interface PaymentEVMMapping extends Mapping<PaymentEVM, PaymentDTO> {

    @org.mapstruct.Mapping(target = "transactionDate", source = "transactionDate")
    default LocalDate paymentDate(String stringDate) {
        return LocalDate.parse(stringDate, DATETIME_FORMATTER);
    }

    @org.mapstruct.Mapping(target = "transactionDate", source = "transactionDate")
    default String paymentDate(LocalDate paymentDate) {
        return DATETIME_FORMATTER.format(paymentDate);
    }

    @org.mapstruct.Mapping(target = "transactionAmount", source = "transactionAmount")
    default BigDecimal paymentAmount(double paymentAmount) {

        return toScaledBigDecimal(paymentAmount);
    }

    @org.mapstruct.Mapping(target = "transactionAmount", source = "transactionAmount")
    default double paymentAmount(BigDecimal paymentAmount) {

        return toDouble(paymentAmount);
    }
}
