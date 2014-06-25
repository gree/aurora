package net.gree.aurora.domain.shardingconfig;

import org.apache.commons.lang.Validate;

/**
 * {@link ShardingConfig}の種別を表す列挙型。
 */
public enum ShardingConfigType {
    /**
     * Generic Type
     */
    GENERIC_TYPE("generic"),
    /**
     * JDBC
     */
    JDBC_TYPE("jdbc"),
    /**
     * Redis
     */
    REDIS_TYPE("redis");

    private final String code;

    /**
     * インスタンスを生成する。
     *
     * @param code コード
     */
    ShardingConfigType(String code) {
        Validate.notNull(code);
        this.code = code;
    }

    /**
     * コードから列挙値を解決する。
     *
     * @param code コード
     * @return {@link ShardingConfigType}
     */
    public static ShardingConfigType resolveByCode(String code) {
        Validate.notNull(code);
        for (ShardingConfigType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * コードを取得する。
     *
     * @return コード
     */
    public String getCode() {
        return code;
    }
}
