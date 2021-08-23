package io.github.erp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.erp.web.rest.TestUtil;

public class PaymentsFileTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsFileType.class);
        PaymentsFileType paymentsFileType1 = new PaymentsFileType();
        paymentsFileType1.setId(1L);
        PaymentsFileType paymentsFileType2 = new PaymentsFileType();
        paymentsFileType2.setId(paymentsFileType1.getId());
        assertThat(paymentsFileType1).isEqualTo(paymentsFileType2);
        paymentsFileType2.setId(2L);
        assertThat(paymentsFileType1).isNotEqualTo(paymentsFileType2);
        paymentsFileType1.setId(null);
        assertThat(paymentsFileType1).isNotEqualTo(paymentsFileType2);
    }
}
