package net.gree.aurora.application.config.defaultconfig;

/**
 * JDBCに対応した{@link AuroraDefaultConfig}。
 */
public interface AuroraDefaultJDBCConfig extends AuroraDefaultConfig {

    /**
     * ドライバークラス名を取得する。
     *
     * @return ドライバークラス名
     */
    String getDriverClassName();

    /**
     * プレフィックスURLを取得する。
     *
     * @return プレフィックスURL
     */
    String getPrefixUrl();

    /**
     * ユーザ名を取得する。
     *
     * @return ユーザ名
     */
    String getUserName();

    /**
     * パスワードを取得する。
     *
     * @return パスワード
     */
    String getPassword();

    /**
     * 読み取り用ユーザ名を取得する。
     *
     * @return 読み取り用ユーザ名
     */
    String getReadOnlyUserName();

    /**
     * 読み取り用パスワードを取得する。
     *
     * @return 読み取り用パスワード
     */
    String getReadOnlyPassword();

}
