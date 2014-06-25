package net.gree.aurora.application.config;

/**
 * {@link AuroraTableNamesConfigLoader}のためのファクトリ。
 */
public final class AuroraTableNmaesConfigLoaderFactory {

    private AuroraTableNmaesConfigLoaderFactory() {

    }

    /**
     * {@link AuroraTableNamesConfigLoader}を生成する。
     *
     * @return {@link AuroraTableNamesConfigLoader}
     */
    public static AuroraTableNamesConfigLoader create() {
        return new AuroraTableNamesConfigLoaderImpl();
    }

}
