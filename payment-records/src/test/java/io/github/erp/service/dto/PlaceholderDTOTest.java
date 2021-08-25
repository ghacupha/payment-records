package io.github.erp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.erp.web.rest.TestUtil;

public class PlaceholderDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaceholderDTO.class);
        PlaceholderDTO placeholderDTO1 = new PlaceholderDTO();
        placeholderDTO1.setId(1L);
        PlaceholderDTO placeholderDTO2 = new PlaceholderDTO();
        assertThat(placeholderDTO1).isNotEqualTo(placeholderDTO2);
        placeholderDTO2.setId(placeholderDTO1.getId());
        assertThat(placeholderDTO1).isEqualTo(placeholderDTO2);
        placeholderDTO2.setId(2L);
        assertThat(placeholderDTO1).isNotEqualTo(placeholderDTO2);
        placeholderDTO1.setId(null);
        assertThat(placeholderDTO1).isNotEqualTo(placeholderDTO2);
    }
}
