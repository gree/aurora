package net.gree.aurora.application.config;

import java.util.Set;

/**
 * テーブル名のための設定を表すインターフェイス。
 */
public interface AuroraTableNamesConfig {

    /**
     * テーブル名の集合を取得する。
     *
     * @return テーブル名の集合
     */
    Set<String> getTableNames();

}
