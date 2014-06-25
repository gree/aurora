package net.gree.aurora.domain.tablename;

/**
 * {@link TableName}のためのファクトリ。
 */
public final class TableNameFactory {

    private TableNameFactory() {
    }

    /**
     * {@link TableName}を生成する。
     * <p>
     * {@code name}が正規表現の場合は評価される。
     * </p>
     *
     * @param name テーブル名。
     * @return {@link TableName}
     */
    public static TableName create(String name) {
        return new TableNameImpl(name);
    }

    /**
     * {@link TableName}を生成する。
     *
     * @param name      テーブル名
     * @param isPattern {@code name}が正規表現の場合はtrue
     * @return {@link TableName}
     */
    public static TableName create(String name, boolean isPattern) {
        return new TableNameImpl(name, isPattern);
    }

}
