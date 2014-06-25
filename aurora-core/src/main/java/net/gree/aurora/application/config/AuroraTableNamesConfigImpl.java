package net.gree.aurora.application.config;

import org.apache.commons.lang.Validate;
import org.sisioh.dddbase.utils.CloneUtil;

import java.util.Set;

public class AuroraTableNamesConfigImpl implements AuroraTableNamesConfig {

    private final Set<String> tableNames;

    public AuroraTableNamesConfigImpl(Set<String> tableNames) {
        Validate.notNull(tableNames);
        this.tableNames = CloneUtil.cloneValueHashSet(tableNames);
    }

    @Override
    public Set<String> getTableNames() {
        return CloneUtil.cloneValueHashSet(tableNames);
    }

}
