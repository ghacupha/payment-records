package io.github.erp.service.dto;

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
