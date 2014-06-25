package net.gree.aurora.application.config.defaultconfig;

/**
 * {@link AuroraDefaultJDBCConfig}のためのファクトリ。
 *
 * @param <T> ソースの型
 */
public interface AuroraDefaultJDBCConfigFactory<T> extends AuroraDefaultConfigFactory<T> {

    /**
     * {@link AuroraDefaultJDBCConfig}を生成する。
     *
     * @param source ソース
     * @return {@link AuroraDefaultJDBCConfig}
     */
    AuroraDefaultJDBCConfig create(T source);

}


