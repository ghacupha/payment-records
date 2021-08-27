package io.github.erp.internal.model;

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
