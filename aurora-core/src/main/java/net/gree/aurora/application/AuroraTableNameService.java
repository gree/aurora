package net.gree.aurora.application;

import net.gree.aurora.domain.tablename.TableName;
import org.sisioh.dddbase.utils.Try;

/**
 * ヒントからテーブル名を解決するためのサービス。
 *
 * @param <T> ヒントの型
 */
public interface AuroraTableNameService<T> {

    /**
     * ヒントからテーブル名を解決する。
     *
     * @param hint ヒント
     * @return Tryでラップされた{@link TableName}
     */
    Try<TableName> resolveTableNameByHint(T hint);

}
