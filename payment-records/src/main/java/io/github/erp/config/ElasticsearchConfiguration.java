package io.github.erp.config;

/*-
 * Payment Records - Payment records is part of the ERP System
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.mapper.DefaultJestResultsMapper;
import io.searchbox.client.JestClient;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.mapping.MappingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchConfiguration {

    private ObjectMapper mapper;

    public ElasticsearchConfiguration(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public EntityMapper getEntityMapper() {
        return new CustomEntityMapper(mapper);
    }

    @Bean
    @Primary
    public ElasticsearchOperations elasticsearchTemplate(JestClient jestClient,
                                                         ElasticsearchConverter elasticsearchConverter,
                                                         SimpleElasticsearchMappingContext mappingContext,
                                                         EntityMapper entityMapper) {
        return new JestElasticsearchTemplate(
            jestClient,
            elasticsearchConverter,
            new DefaultJestResultsMapper(mappingContext, entityMapper));
    }

    public class CustomEntityMapper implements EntityMapper {

        private ObjectMapper objectMapper;

        public CustomEntityMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        }

        @Override
        public String mapToString(Object object) throws IOException {
            return objectMapper.writeValueAsString(object);
        }

        @Override
        public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
            return objectMapper.readValue(source, clazz);
        }

        @Override
        public Map<String, Object> mapObject(Object source) {
            try {
                return objectMapper.readValue(mapToString(source), HashMap.class);
            } catch (IOException e) {
                throw new MappingException(e.getMessage(), e);
            }
        }

        @Override
        public <T> T readObject(Map<String, Object> source, Class<T> targetType) {
            try {
                return mapToObject(mapToString(source), targetType);
            } catch (IOException e) {
                throw new MappingException(e.getMessage(), e);
            }
        }
    }

}
