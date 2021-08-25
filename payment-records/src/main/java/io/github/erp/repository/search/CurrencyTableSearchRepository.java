package io.github.erp.repository.search;

import io.github.erp.domain.CurrencyTable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CurrencyTable} entity.
 */
public interface CurrencyTableSearchRepository extends ElasticsearchRepository<CurrencyTable, Long> {
}
