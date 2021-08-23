package io.github.erp.internal.report;

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

import org.hibernate.Criteria;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.persister.entity.OuterJoinLoadable;

/**
 * General utils used in tests and logs for checking queries created by JPA
 */
public class QueryTools {

    public static String toSQL(Criteria crit) {
        String sql = new BasicFormatterImpl().format(
            (new CriteriaJoinWalker(
                (OuterJoinLoadable)
                    ((CriteriaImpl)crit).getSession().getFactory().getEntityPersister(
                        ((CriteriaImpl)crit).getSession().getFactory().getImplementors(
                            ((CriteriaImpl)crit).getEntityOrClassName())[0]),
                new CriteriaQueryTranslator(
                    ((CriteriaImpl)crit).getSession().getFactory(),
                    ((CriteriaImpl)crit),
                    ((CriteriaImpl)crit).getEntityOrClassName(),
                    CriteriaQueryTranslator.ROOT_SQL_ALIAS),
                ((CriteriaImpl)crit).getSession().getFactory(),
                (CriteriaImpl)crit,
                ((CriteriaImpl)crit).getEntityOrClassName(),
                ((CriteriaImpl)crit).getSession().getLoadQueryInfluencers()
            )
            ).getSQLString()
        );

        return sql;
    }
}
