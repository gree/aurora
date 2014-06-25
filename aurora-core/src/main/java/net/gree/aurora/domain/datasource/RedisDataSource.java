package net.gree.aurora.domain.datasource;

/**
 * Redisに対応した{@link DataSource}。
 */
public interface RedisDataSource extends DataSource {

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
