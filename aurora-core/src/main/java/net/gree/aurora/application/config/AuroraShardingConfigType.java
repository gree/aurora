package net.gree.aurora.application.config;

import org.apache.commons.lang.Validate;

/**
 * {@link AuroraShardingConfig}の種別を表す列挙型。
 */
public enum AuroraShardingConfigType {
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
    AuroraShardingConfigType(String code) {
        Validate.notNull(code);
        this.code = code;
    }

    /**
     * コードから列挙値を解決する。
     *
     * @param code コード
     * @return {@link AuroraShardingConfigType}
     */
    public static AuroraShardingConfigType resolveByCode(String code) {
        for (AuroraShardingConfigType type : values()) {
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
