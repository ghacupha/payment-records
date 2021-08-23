package io.github.erp.domain;

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

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.erp.web.rest.TestUtil;

public class PaymentsFileUploadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsFileUpload.class);
        PaymentsFileUpload paymentsFileUpload1 = new PaymentsFileUpload();
        paymentsFileUpload1.setId(1L);
        PaymentsFileUpload paymentsFileUpload2 = new PaymentsFileUpload();
        paymentsFileUpload2.setId(paymentsFileUpload1.getId());
        assertThat(paymentsFileUpload1).isEqualTo(paymentsFileUpload2);
        paymentsFileUpload2.setId(2L);
        assertThat(paymentsFileUpload1).isNotEqualTo(paymentsFileUpload2);
        paymentsFileUpload1.setId(null);
        assertThat(paymentsFileUpload1).isNotEqualTo(paymentsFileUpload2);
    }
}
