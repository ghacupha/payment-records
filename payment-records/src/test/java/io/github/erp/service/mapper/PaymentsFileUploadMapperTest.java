package io.github.erp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentsFileUploadMapperTest {

    private PaymentsFileUploadMapper paymentsFileUploadMapper;

    @BeforeEach
    public void setUp() {
        paymentsFileUploadMapper = new PaymentsFileUploadMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paymentsFileUploadMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentsFileUploadMapper.fromId(null)).isNull();
    }
}
