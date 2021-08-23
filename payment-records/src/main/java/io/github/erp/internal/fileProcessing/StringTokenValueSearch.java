package io.github.erp.internal.fileProcessing;

import io.github.erp.service.PaymentsMessageTokenQueryService;
import io.github.erp.service.dto.PaymentsMessageTokenCriteria;
import io.github.erp.service.dto.PaymentsMessageTokenDTO;
import io.github.jhipster.service.filter.StringFilter;
import org.springframework.stereotype.Service;

/**
 * Implementation of token-search where the token value itself is of the value string
 */
@Service("stringTokenValueSearch")
public class StringTokenValueSearch implements TokenValueSearch<String> {

    private final PaymentsMessageTokenQueryService messageTokenQueryService;

    public StringTokenValueSearch(final PaymentsMessageTokenQueryService messageTokenQueryService) {
        this.messageTokenQueryService = messageTokenQueryService;
    }

    public PaymentsMessageTokenDTO getMessageToken(final String tokenValue) {
        StringFilter tokenFilter = new StringFilter();
        tokenFilter.setEquals(tokenValue);
        PaymentsMessageTokenCriteria tokenValueCriteria = new PaymentsMessageTokenCriteria();
        tokenValueCriteria.setTokenValue(tokenFilter);
        return messageTokenQueryService.findByCriteria(tokenValueCriteria).get(0);
    }
}
