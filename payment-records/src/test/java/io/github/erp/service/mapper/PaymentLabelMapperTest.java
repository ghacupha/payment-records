package io.github.erp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentLabelMapperTest {

    private PaymentLabelMapper paymentLabelMapper;

    @BeforeEach
    public void setUp() {
        paymentLabelMapper = new PaymentLabelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paymentLabelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentLabelMapper.fromId(null)).isNull();
    }
}
