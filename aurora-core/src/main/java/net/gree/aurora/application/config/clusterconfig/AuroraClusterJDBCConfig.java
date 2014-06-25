package net.gree.aurora.application.config.clusterconfig;

/**
 * JDBC用の{@link AuroraClusterConfig}。
 */
public interface AuroraClusterJDBCConfig extends AuroraClusterConfig {

    /**
     * ドライバークラス名を取得する。
     *
     * @return ドライバークラス名
     */
    String getDriverClassName();

    /**
     * データベース名を取得する。
     *
     * @return データベース名
     */
    String getDatabaseName();

    /**
     * ユーザ名を取得する。
     *
     * @return ユーザ名
     */
    String getUserName();

    /**
     * パスワード名取得する。
     *
     * @return パスワード
     */
    String getPassword();

}
