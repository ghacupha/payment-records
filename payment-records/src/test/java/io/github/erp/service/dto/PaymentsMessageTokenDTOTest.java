package io.github.erp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.erp.web.rest.TestUtil;

public class PaymentsMessageTokenDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsMessageTokenDTO.class);
        PaymentsMessageTokenDTO paymentsMessageTokenDTO1 = new PaymentsMessageTokenDTO();
        paymentsMessageTokenDTO1.setId(1L);
        PaymentsMessageTokenDTO paymentsMessageTokenDTO2 = new PaymentsMessageTokenDTO();
        assertThat(paymentsMessageTokenDTO1).isNotEqualTo(paymentsMessageTokenDTO2);
        paymentsMessageTokenDTO2.setId(paymentsMessageTokenDTO1.getId());
        assertThat(paymentsMessageTokenDTO1).isEqualTo(paymentsMessageTokenDTO2);
        paymentsMessageTokenDTO2.setId(2L);
        assertThat(paymentsMessageTokenDTO1).isNotEqualTo(paymentsMessageTokenDTO2);
        paymentsMessageTokenDTO1.setId(null);
        assertThat(paymentsMessageTokenDTO1).isNotEqualTo(paymentsMessageTokenDTO2);
    }
}
