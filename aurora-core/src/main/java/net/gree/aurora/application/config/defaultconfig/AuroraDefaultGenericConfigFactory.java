package net.gree.aurora.application.config.defaultconfig;

/**
 * {@link AuroraDefaultGenericConfig}のためのファクトリ。　
 *
 * @param <T> ソース
 */
public interface AuroraDefaultGenericConfigFactory<T> extends AuroraDefaultConfigFactory<T> {

    /**
     * {@link AuroraDefaultGenericConfig}を生成する。
     *
     * @param source ソース
     * @return {@link AuroraDefaultGenericConfig}
     */
    AuroraDefaultGenericConfig create(T source);

}
