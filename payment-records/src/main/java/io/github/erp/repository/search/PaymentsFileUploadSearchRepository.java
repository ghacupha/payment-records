package io.github.erp.repository.search;

import io.github.erp.domain.PaymentsFileUpload;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PaymentsFileUpload} entity.
 */
public interface PaymentsFileUploadSearchRepository extends ElasticsearchRepository<PaymentsFileUpload, Long> {
}
