package io.github.erp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentsMessageTokenMapperTest {

    private PaymentsMessageTokenMapper paymentsMessageTokenMapper;

    @BeforeEach
    public void setUp() {
        paymentsMessageTokenMapper = new PaymentsMessageTokenMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paymentsMessageTokenMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentsMessageTokenMapper.fromId(null)).isNull();
    }
}
