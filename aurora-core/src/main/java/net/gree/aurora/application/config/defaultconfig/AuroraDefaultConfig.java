package net.gree.aurora.application.config.defaultconfig;

/**
 * デフォルト値を表すインターフェイス。
 */
public interface AuroraDefaultConfig {

    /**
     * ポート番号を取得する。
     *
     * @return ポート番号
     */
    Integer getPort();

    /**
     * マスターホスト名を取得する。
     *
     * @return マスターホスト名
     */
    String getMasterHost();

    /**
     * スレーブホスト名を取得する。
     *
     * @return スレーブホスト名
     */
    String getSlaveHost();

}
