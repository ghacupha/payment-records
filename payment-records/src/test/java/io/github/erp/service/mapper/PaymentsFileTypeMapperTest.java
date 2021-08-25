package io.github.erp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentsFileTypeMapperTest {

    private PaymentsFileTypeMapper paymentsFileTypeMapper;

    @BeforeEach
    public void setUp() {
        paymentsFileTypeMapper = new PaymentsFileTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paymentsFileTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentsFileTypeMapper.fromId(null)).isNull();
    }
}
