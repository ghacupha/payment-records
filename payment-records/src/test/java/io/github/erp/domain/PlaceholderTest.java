package io.github.erp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.erp.web.rest.TestUtil;

public class PlaceholderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Placeholder.class);
        Placeholder placeholder1 = new Placeholder();
        placeholder1.setId(1L);
        Placeholder placeholder2 = new Placeholder();
        placeholder2.setId(placeholder1.getId());
        assertThat(placeholder1).isEqualTo(placeholder2);
        placeholder2.setId(2L);
        assertThat(placeholder1).isNotEqualTo(placeholder2);
        placeholder1.setId(null);
        assertThat(placeholder1).isNotEqualTo(placeholder2);
    }
}
