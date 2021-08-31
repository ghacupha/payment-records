package io.github.erp.paymentRecords;

import java.util.List;

public interface ApplicationIndexingService<T> {

    List<T> index(List<T> entities);
}
