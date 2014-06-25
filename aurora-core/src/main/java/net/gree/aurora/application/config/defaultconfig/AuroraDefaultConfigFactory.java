package net.gree.aurora.application.config.defaultconfig;

/**
 * {@link AuroraDefaultConfig}のためのファクトリ。
 *
 * @param <T> ソースの型
 */
public interface AuroraDefaultConfigFactory<T> {

    /**
     * {@link AuroraDefaultConfig}を生成する。
     *
     * @param source ソース
     * @return {@link AuroraDefaultConfig}
     */
    AuroraDefaultConfig create(T source);

}
