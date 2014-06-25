package net.gree.aurora.application;

import com.typesafe.config.Config;
import net.gree.aurora.domain.tablename.TableName;
import net.gree.aurora.domain.tablename.TableNameResolver;

import java.io.File;
import java.util.Set;

/**
 * {@link AuroraTableNameService}のファクトリ。
 */
public final class AuroraTableNameServiceFactory {

    /**
     * {@link AuroraTableNameService}を生成する。
     *
     * @param tableNameResolver {@link TableNameResolver}
     * @param tableNames {@link TableName}の集合
     * @param <T> ヒントの型
     * @return {@link AuroraTableNameService}
     */
    public static <T> AuroraTableNameService<T> create(TableNameResolver<T> tableNameResolver, Set<TableName> tableNames) {
        return new AuroraTableNameServiceImpl<>(tableNameResolver, tableNames);
    }

    /**
     * {@link AuroraTableNameService}を生成する。
     *
     * @param tableNameResolver {@link TableNameResolver}
     * @param <T> ヒントの型
     * @return {@link AuroraTableNameService}
     */
    public static <T> AuroraTableNameService<T> create(TableNameResolver<T> tableNameResolver) {
        return new AuroraTableNameServiceImpl<>(tableNameResolver);
    }

    /**
     * {@link AuroraTableNameService}を生成する。
     *
     * @param tableNameResolver {@link TableNameResolver}
     * @param config {@link Config}
     * @param <T> ヒントの型
     * @return {@link AuroraTableNameService}
     */
    public static <T> AuroraTableNameService<T> create(TableNameResolver<T> tableNameResolver, Config config) {
        return new AuroraTableNameServiceImpl<>(tableNameResolver, config);
    }

    /**
     * {@link AuroraTableNameService}を生成する。
     *
     * @param tableNameResolver {@link TableNameResolver}
     * @param configFile 設定ファイル
     * @param <T> ヒントの型
     * @return {@link AuroraTableNameService}
     */
    public static <T> AuroraTableNameService<T> create(TableNameResolver<T> tableNameResolver, File configFile) {
        return new AuroraTableNameServiceImpl<>(tableNameResolver, configFile);
    }

}
