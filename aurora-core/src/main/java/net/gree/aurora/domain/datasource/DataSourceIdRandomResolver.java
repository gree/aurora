package net.gree.aurora.domain.datasource;

import org.apache.commons.lang.Validate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public final class DataSourceIdRandomResolver implements DataSourceIdResolver<Integer> {

    private final List<DataSourceId> dataSourceIds;
    private final AtomicReference<Random> random = new AtomicReference<Random>(new Random());

    public DataSourceIdRandomResolver(List<DataSourceId> dataSourceIds) {
        Validate.notNull(dataSourceIds);
        Validate.notEmpty(dataSourceIds);
        this.dataSourceIds = dataSourceIds;
    }

    @Override
    public DataSourceId apply(Integer hint) {
        if (hint != null) {
            random.set(new Random(hint));
        }
        int index = random.get().nextInt(dataSourceIds.size());
        return dataSourceIds.get(index);
    }

}



