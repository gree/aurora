package net.gree.aurora.domain.tablename;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class TableNameImpl implements TableName {
    private final String name;
    private final Pattern pattern;

    public TableNameImpl(String name) {
        this(name, true);
    }

    public TableNameImpl(String name, boolean isPattern) {
        Validate.notNull(name);
        this.name = name;
        if (isPattern) {
            this.pattern = Pattern.compile(name);
        } else {
            this.pattern = null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isMatch(String value) {
        Validate.notNull(value);
        if (pattern == null) {
            return name.equals(value);
        } else {
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableNameImpl tableName = (TableNameImpl) o;

        if (!name.equals(tableName.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).toString();
    }

}
