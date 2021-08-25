package io.github.erp.repository.search;

import io.github.erp.domain.PaymentsMessageToken;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PaymentsMessageToken} entity.
 */
public interface PaymentsMessageTokenSearchRepository extends ElasticsearchRepository<PaymentsMessageToken, Long> {
}
