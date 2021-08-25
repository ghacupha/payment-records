package io.github.erp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaceholderMapperTest {

    private PlaceholderMapper placeholderMapper;

    @BeforeEach
    public void setUp() {
        placeholderMapper = new PlaceholderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(placeholderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(placeholderMapper.fromId(null)).isNull();
    }
}
