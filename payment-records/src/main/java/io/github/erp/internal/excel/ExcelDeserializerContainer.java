package io.github.erp.internal.excel;

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

import io.github.erp.internal.excel.deserializer.DefaultExcelFileDeserializer;
import io.github.erp.internal.model.PaymentEVM;
import io.github.erp.internal.model.sampleDataModel.CurrencyTableEVM;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.github.erp.internal.excel.PoijiOptionsConfig.getDefaultPoijiOptions;

/**
 * This container has configurations for our excel file deserializers and a sample is provided for currency-table
 */
@Configuration
public class ExcelDeserializerContainer {

    @Bean("currencyTableExcelFileDeserializer")
    public ExcelFileDeserializer<CurrencyTableEVM> currencyTableExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(CurrencyTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }

    @Bean("paymentsExcelFileDeserializer")
    public ExcelFileDeserializer<PaymentEVM> paymentsExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(PaymentEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }
}
