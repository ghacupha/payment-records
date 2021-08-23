package io.github.erp.repository.search;

import io.github.erp.domain.PaymentsFileType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PaymentsFileType} entity.
 */
public interface PaymentsFileTypeSearchRepository extends ElasticsearchRepository<PaymentsFileType, Long> {
}
