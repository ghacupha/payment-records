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

import io.github.erp.internal.AppConstants;
import io.github.erp.internal.model.PaymentEVM;
import io.github.erp.internal.model.sampleDataModel.CurrencyTableEVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static io.github.erp.internal.excel.ExcelTestUtil.readFile;
import static io.github.erp.internal.excel.ExcelTestUtil.toBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * This test shows how the deserializer works inside the ItemReader interface.
 */
public class ExcelFileUtilsTest {

    private ExcelDeserializerContainer container;

    @BeforeEach
    void setUp() {
        container = new ExcelDeserializerContainer();
    }

    @Test
    public void deserializeCurrenciesFile() throws Exception {

        ExcelFileDeserializer<CurrencyTableEVM> deserializer = container.currencyTableExcelFileDeserializer();

        List<CurrencyTableEVM> currencies = deserializer.deserialize(toBytes(readFile("currencies.xlsx")));

        assertThat(currencies.size()).isEqualTo(13);
        assertThat(currencies.get(0)).isEqualTo(CurrencyTableEVM.builder().rowIndex(1).country("USA").currencyCode("USD").currencyName("US DOLLAR").locality("FOREIGN").build());
        assertThat(currencies.get(1)).isEqualTo(CurrencyTableEVM.builder().rowIndex(2).country("UNITED KINGDOM").currencyCode("GBP").currencyName("STERLING POUND").locality("FOREIGN").build());
        assertThat(currencies.get(2)).isEqualTo(CurrencyTableEVM.builder().rowIndex(3).country("EURO-ZONE").currencyCode("EUR").currencyName("EURO").locality("FOREIGN").build());
        assertThat(currencies.get(3)).isEqualTo(CurrencyTableEVM.builder().rowIndex(4).country("KENYA").currencyCode("KES").currencyName("KENYA SHILLING").locality("LOCAL").build());
        assertThat(currencies.get(4)).isEqualTo(CurrencyTableEVM.builder().rowIndex(5).country("SWITZERLAND").currencyCode("CHF").currencyName("SWISS FRANC").locality("FOREIGN").build());
        assertThat(currencies.get(5)).isEqualTo(CurrencyTableEVM.builder().rowIndex(6).country("SOUTH AFRICA").currencyCode("ZAR").currencyName("SOUTH AFRICAN RAND").locality("FOREIGN").build());
        assertThat(currencies.get(12)).isEqualTo(CurrencyTableEVM.builder().rowIndex(13).country("CHINA").currencyCode("CNY").currencyName("CHINESE YUAN").locality("FOREIGN").build());
    }

    @Test
    public void deserializePaymentsFile() throws Exception {

        ExcelFileDeserializer<PaymentEVM> deserializer = container.paymentsExcelFileDeserializer();

        List<PaymentEVM> payments = deserializer.deserialize(toBytes(readFile("payments.xlsx")));

        assertThat(payments.size()).isEqualTo(1000);

        for (int i = 0; i < 1000; i++) {
            String index = String.valueOf(1 + i);
            assertThat(payments.get(i))
                .isEqualTo(
                    PaymentEVM.builder()
                        .rowIndex(Integer.parseInt(index))
                        .paymentsCategory("category"+i)
                        .transactionNumber("trn"+i)
                        .transactionDate(AppConstants.DATETIME_FORMATTER.format(LocalDate.of(2021,8,23)))
                        .transactionCurrency("crn"+i)
                        .transactionAmount(100 + i)
                        .beneficiary("ben"+i)
                        .build()
                );
        }
    }
}
