package io.github.erp.internal.batch;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for handling bits as sub-lists and for batches should have a job-scope
 * The object is initialized once for every list that is being processed and should exist
 * the entire time the list if still in progress. If a new one is initialized it will cause
 * trouble
 */
public class ListPartition<T> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ListPartition.class);

    private final int dataSize;

    private final int listPageSize;
    private final List<T> unProcessedItems;

    private int listPageNumber;
    private int processedDataSize;

    public ListPartition(final int listPageSize, final List<T> unProcessedItems) {
        this.listPageSize = listPageSize;
        this.unProcessedItems = unProcessedItems;
        this.dataSize = unProcessedItems.size();
        this.listPageNumber = -1;
        this.processedDataSize = 0;
    }



    public List<T> getPartition() {

        List<T> forProcessing = new ArrayList<>();

        if (dataSize > processedDataSize) {

            listPageNumber++;

            log.info("Getting page number : {}", listPageNumber);

            try {
                forProcessing = ImmutableList.copyOf(Lists.partition(unProcessedItems, listPageSize).get(listPageNumber));
            } catch (Exception e) {
                // ? DO NOTHING?
                // An error is quietly thrown, making the forProcessing list null
            }

            // TODO WHY THIS DIDN'T WORK processedDataSize =+ forProcessing.size();
            processedDataSize = processedDataSize + forProcessing.size();

            log.info("{} items enqueued for processing", processedDataSize);
        } else {

            forProcessing = ImmutableList.of();
        }
        return forProcessing;
    }
}
