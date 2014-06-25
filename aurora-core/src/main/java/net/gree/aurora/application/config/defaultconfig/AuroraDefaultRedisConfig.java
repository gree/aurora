package net.gree.aurora.application.config.defaultconfig;

/**
 * Redis用{@link AuroraDefaultConfig}。
 */
public interface AuroraDefaultRedisConfig extends AuroraDefaultConfig {

    /**
     * データベース番号を取得する。
     *
     * @return データベース番号
     */
    Integer getDatabaseNumber();

    /**
     * パスワードを取得する。
     *
     * @return パスワード
     */
    String getPassword();

}
