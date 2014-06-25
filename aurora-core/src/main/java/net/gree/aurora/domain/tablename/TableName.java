package net.gree.aurora.domain.tablename;

/**
 * テーブル名を表すインターフェイス。
 */
public interface TableName {

    /**
     * テーブル名を取得する。
     *
     * @return テーブル名
     */
    String getName();

    /**
     * 文字列がテーブル名と一致するかどうかを検査する。
     *
     * @param value 文字列
     * @return 一致する場合はtrue
     */
    boolean isMatch(String value);

}
