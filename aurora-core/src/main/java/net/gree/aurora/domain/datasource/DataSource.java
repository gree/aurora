package net.gree.aurora.domain.datasource;

import org.sisioh.dddbase.model.Entity;
import org.sisioh.dddbase.utils.Option;

/**
 * データソースを表すエンティティ。
 */
public interface DataSource extends Entity<DataSourceId>, Cloneable, Comparable<DataSource> {

    /**
     * {@link DataSourceId}を取得する。
     *
     * @return {@link DataSourceId}
     */
    DataSourceId getIdentity();

    /**
     * {@link DataSourceType}を取得する。
     *
     * @return {@link DataSourceType}
     */
    DataSourceType getDataSourceType();

    /**
     * ホスト名を取得する。
     *
     * @return ホスト名
     */
    String getHost();

    /**
     * ポート番号を取得する。
     * @return
     */
    Option<Integer> getPort();

    /**
     * オーダーを取得する。
     * @return
     */
    Long getOrder();

    /**
     * インスタンスを複製する。
     *
     * @return 複製されたインスタンス
     */
    DataSource clone();

}
