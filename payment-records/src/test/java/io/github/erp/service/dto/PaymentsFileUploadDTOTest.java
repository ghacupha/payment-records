package io.github.erp.service.dto;

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

public class PaymentsFileUploadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsFileUploadDTO.class);
        PaymentsFileUploadDTO paymentsFileUploadDTO1 = new PaymentsFileUploadDTO();
        paymentsFileUploadDTO1.setId(1L);
        PaymentsFileUploadDTO paymentsFileUploadDTO2 = new PaymentsFileUploadDTO();
        assertThat(paymentsFileUploadDTO1).isNotEqualTo(paymentsFileUploadDTO2);
        paymentsFileUploadDTO2.setId(paymentsFileUploadDTO1.getId());
        assertThat(paymentsFileUploadDTO1).isEqualTo(paymentsFileUploadDTO2);
        paymentsFileUploadDTO2.setId(2L);
        assertThat(paymentsFileUploadDTO1).isNotEqualTo(paymentsFileUploadDTO2);
        paymentsFileUploadDTO1.setId(null);
        assertThat(paymentsFileUploadDTO1).isNotEqualTo(paymentsFileUploadDTO2);
    }
}
