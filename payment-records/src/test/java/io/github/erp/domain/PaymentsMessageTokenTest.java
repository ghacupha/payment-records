package io.github.erp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.erp.web.rest.TestUtil;

public class PaymentsMessageTokenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsMessageToken.class);
        PaymentsMessageToken paymentsMessageToken1 = new PaymentsMessageToken();
        paymentsMessageToken1.setId(1L);
        PaymentsMessageToken paymentsMessageToken2 = new PaymentsMessageToken();
        paymentsMessageToken2.setId(paymentsMessageToken1.getId());
        assertThat(paymentsMessageToken1).isEqualTo(paymentsMessageToken2);
        paymentsMessageToken2.setId(2L);
        assertThat(paymentsMessageToken1).isNotEqualTo(paymentsMessageToken2);
        paymentsMessageToken1.setId(null);
        assertThat(paymentsMessageToken1).isNotEqualTo(paymentsMessageToken2);
    }
}
