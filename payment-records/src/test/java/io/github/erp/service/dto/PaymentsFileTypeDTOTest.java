package io.github.erp.service.dto;

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
