package net.gree.aurora.domain.tablename;

import org.apache.commons.lang.Validate;

/**
 * {@link TableNameResolver}の骨格実装。
 *
 * @param <T> ヒントの型
 */
public abstract class AbstractTableNameResolver<T> implements TableNameResolver<T> {

    /**
     * 基準となるテーブル名。
     */
    protected final String tableBaseName;

    /**
     * インスタンスを生成する。
     *
     * @param tableBaseName 基準となるテーブル名
     */
    protected AbstractTableNameResolver(String tableBaseName) {
        Validate.notNull(tableBaseName);
        this.tableBaseName = tableBaseName;
    }

    /**
     * ヒントからサフィックス名を取得する。
     *
     * @param hint ヒント
     * @return サフィックス名
     */
    protected abstract String getSuffixName(T hint);

    @Override
    public TableName apply(T hint) {
        Validate.notNull(hint);
        return TableNameFactory.create(tableBaseName + getSuffixName(hint), false);
    }

}
