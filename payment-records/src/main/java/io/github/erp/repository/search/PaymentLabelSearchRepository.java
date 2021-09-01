package io.github.erp.repository.search;

import io.github.erp.domain.PaymentLabel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PaymentLabel} entity.
 */
public interface PaymentLabelSearchRepository extends ElasticsearchRepository<PaymentLabel, Long> {
}
