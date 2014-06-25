package net.gree.aurora.domain.datasource;

import java.util.UUID;

/**
 * {@link DataSourceId}のためのファクトリ。
 */
public final class DataSourceIdFactory {

    private DataSourceIdFactory() {

    }

    /**
     * {@link DataSourceId}を生成する。
     *
     * @param value 識別子の値
     * @return {@link DataSourceId}
     */
    public static DataSourceId create(UUID value) {
        return new DataSourceIdImpl(value);
    }

}
