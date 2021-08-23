package io.github.erp.domain;

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
