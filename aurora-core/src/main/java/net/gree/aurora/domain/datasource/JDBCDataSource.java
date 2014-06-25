package net.gree.aurora.domain.datasource;

/**
 * JDBCに対応した{@link DataSource}。
 */
public interface JDBCDataSource extends DataSource {

    /**
     * ドライバークラス名を取得する。
     *
     * @return ドライバークラス名
     */
    String getDriverClassName();

    /**
     * URLを取得する。
     *
     * @return URL
     */
    String getUrl();

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

}
