package io.github.erp.service;

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

import io.github.erp.domain.PersistentAuditEvent;
import io.github.erp.repository.PersistenceAuditEventRepository;
import io.github.erp.PaymentRecordsGateApp;
import io.github.jhipster.config.JHipsterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link AuditEventService}.
 */
@SpringBootTest(classes = PaymentRecordsGateApp.class)
@Transactional
public class AuditEventServiceIT {
    @Autowired
    private AuditEventService auditEventService;

    @Autowired
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    private PersistentAuditEvent auditEventOld;

    private PersistentAuditEvent auditEventWithinRetention;

    private PersistentAuditEvent auditEventNew;

    @BeforeEach
    public void init() {
        auditEventOld = new PersistentAuditEvent();
        auditEventOld.setAuditEventDate(Instant.now().minus(jHipsterProperties.getAuditEvents().getRetentionPeriod() + 1, ChronoUnit.DAYS));
        auditEventOld.setPrincipal("test-user-old");
        auditEventOld.setAuditEventType("test-type");

        auditEventWithinRetention = new PersistentAuditEvent();
        auditEventWithinRetention.setAuditEventDate(Instant.now().minus(jHipsterProperties.getAuditEvents().getRetentionPeriod() - 1, ChronoUnit.DAYS));
        auditEventWithinRetention.setPrincipal("test-user-retention");
        auditEventWithinRetention.setAuditEventType("test-type");

        auditEventNew = new PersistentAuditEvent();
        auditEventNew.setAuditEventDate(Instant.now());
        auditEventNew.setPrincipal("test-user-new");
        auditEventNew.setAuditEventType("test-type");
    }

    @Test
    @Transactional
    public void verifyOldAuditEventsAreDeleted() {
        persistenceAuditEventRepository.deleteAll();
        persistenceAuditEventRepository.save(auditEventOld);
        persistenceAuditEventRepository.save(auditEventWithinRetention);
        persistenceAuditEventRepository.save(auditEventNew);

        persistenceAuditEventRepository.flush();
        auditEventService.removeOldAuditEvents();
        persistenceAuditEventRepository.flush();

        assertThat(persistenceAuditEventRepository.findAll().size()).isEqualTo(2);
        assertThat(persistenceAuditEventRepository.findByPrincipal("test-user-old")).isEmpty();
        assertThat(persistenceAuditEventRepository.findByPrincipal("test-user-retention")).isNotEmpty();
        assertThat(persistenceAuditEventRepository.findByPrincipal("test-user-new")).isNotEmpty();
    }
}
