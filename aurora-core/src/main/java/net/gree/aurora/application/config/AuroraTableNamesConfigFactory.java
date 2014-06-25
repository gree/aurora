package net.gree.aurora.application.config;

/**
 * {@link AuroraTableNamesConfig}のためのファクトリ。
 *
 * @param <T> ソースの型
 */
public interface AuroraTableNamesConfigFactory<T> {

    /**
     * {@link AuroraTableNamesConfig}を生成する。
     *
     * @param source ソース
     * @return {@link AuroraTableNamesConfig}
     */
    AuroraTableNamesConfig create(T source);

}
