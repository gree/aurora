package net.gree.aurora.domain.datasource;

/**
 * {@link DataSourceRepository}のためのファクトリ。
 */
public final class DataSourceRepositoryFactory {

    private DataSourceRepositoryFactory() {

    }

    /**
     * {@link DataSourceRepository}を生成する。
     *
     * @return {@link DataSourceRepository}
     */
    public static DataSourceRepository create() {
        return new DataSourceRepositoryImpl();
    }

}
