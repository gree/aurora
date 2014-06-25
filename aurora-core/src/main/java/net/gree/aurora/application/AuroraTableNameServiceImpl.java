package net.gree.aurora.application;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.gree.aurora.application.config.AuroraTableNamesConfig;
import net.gree.aurora.application.config.AuroraTableNamesConfigLoader;
import net.gree.aurora.application.config.AuroraTableNmaesConfigLoaderFactory;
import net.gree.aurora.domain.tablename.TableName;
import net.gree.aurora.domain.tablename.TableNameFactory;
import net.gree.aurora.domain.tablename.TableNameResolver;
import org.apache.commons.lang.Validate;
import org.sisioh.dddbase.utils.Try;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link AuroraTableNameService}の実装クラス。
 *
 * @param <T> {@link TableNameResolver}のヒントの型
 */
final class AuroraTableNameServiceImpl<T> implements AuroraTableNameService<T> {

    private final TableNameResolver<T> tableNameResolver;
    private final Set<TableName> tableNames;

    AuroraTableNameServiceImpl(TableNameResolver<T> tableNameResolver, File configFile) {
        this(tableNameResolver, ConfigFactory.parseFile(configFile));
    }

    AuroraTableNameServiceImpl(TableNameResolver<T> tableNameResolver, Config config) {
        Validate.notNull(tableNameResolver);
        Validate.notNull(config);
        AuroraTableNamesConfigLoader auroraTableNamesConfigLoader = AuroraTableNmaesConfigLoaderFactory.create();
        Config auroraConfig = config.getConfig("aurora");
        Config tableNameConfig = ConfigUtil.getConfig(auroraConfig, Arrays.asList("tableNameConfigs", "table-name-configs"));
        AuroraTableNamesConfig auroraTableNamesConfig = auroraTableNamesConfigLoader.load(tableNameConfig);
        this.tableNames = new HashSet<>();
        for (String name : auroraTableNamesConfig.getTableNames()) {
            tableNames.add(TableNameFactory.create(name));
        }
        this.tableNameResolver = tableNameResolver;
    }

    AuroraTableNameServiceImpl(TableNameResolver<T> tableNameResolver, Set<TableName> tableNames) {
        Validate.notNull(tableNameResolver);
        Validate.notNull(tableNames);
        Validate.notEmpty(tableNames);
        this.tableNames = new HashSet<>();
        this.tableNames.addAll(tableNames);
        this.tableNameResolver = tableNameResolver;
    }

    AuroraTableNameServiceImpl(TableNameResolver<T> tableNameResolver) {
        Validate.notNull(tableNameResolver);
        this.tableNames = null;
        this.tableNameResolver = tableNameResolver;
    }

    private boolean containsInTableNames(TableName tableName) {
        Validate.notNull(tableName);
        for (TableName tn : tableNames) {
            if (tn.isMatch(tableName.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Try<TableName> resolveTableNameByHint(T hint) {
        Validate.notNull(hint);
        TableName tableName = tableNameResolver.apply(hint);
        if (tableNames != null && !containsInTableNames(tableName)) {
            return Try.ofFailure(new IllegalArgumentException(String.format("hint(%s) is not found.", hint)));
        } else {
            return Try.ofSuccess(tableName);
        }
    }

}
