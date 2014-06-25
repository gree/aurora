package net.gree.aurora.application.config.clusterconfig;

import java.util.List;

/**
 * クラスターのためのインターフェイス。
 */
public interface AuroraClusterConfig {

    /**
     * 識別子を取得する。
     *
     * @return 識別子
     */
    String getId();

    /**
     * マスターのホスト名とポート番号を取得する。
     *
     * @return マスターのホスト名とポート
     */
    String getMasterHostAndPort();

    /**
     * スレーブのホスト名とポート番号の集合を取得する。
     *
     * @return スレーブのホスト名とポート番号の集合
     */
    List<String> getSlaveHostAndPorts();

    /**
     * スタンバイのホスト名とポート番号を取得する。
     *
     * @return スタンバイのホスト名とポート番号
     */
    String getStandbyHostAndPort();

}
