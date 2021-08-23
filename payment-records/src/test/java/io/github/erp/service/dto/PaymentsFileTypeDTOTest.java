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

public class PaymentsFileTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsFileTypeDTO.class);
        PaymentsFileTypeDTO paymentsFileTypeDTO1 = new PaymentsFileTypeDTO();
        paymentsFileTypeDTO1.setId(1L);
        PaymentsFileTypeDTO paymentsFileTypeDTO2 = new PaymentsFileTypeDTO();
        assertThat(paymentsFileTypeDTO1).isNotEqualTo(paymentsFileTypeDTO2);
        paymentsFileTypeDTO2.setId(paymentsFileTypeDTO1.getId());
        assertThat(paymentsFileTypeDTO1).isEqualTo(paymentsFileTypeDTO2);
        paymentsFileTypeDTO2.setId(2L);
        assertThat(paymentsFileTypeDTO1).isNotEqualTo(paymentsFileTypeDTO2);
        paymentsFileTypeDTO1.setId(null);
        assertThat(paymentsFileTypeDTO1).isNotEqualTo(paymentsFileTypeDTO2);
    }
}
