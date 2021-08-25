package io.github.erp.repository.search;

import io.github.erp.domain.Placeholder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Placeholder} entity.
 */
public interface PlaceholderSearchRepository extends ElasticsearchRepository<Placeholder, Long> {
}
