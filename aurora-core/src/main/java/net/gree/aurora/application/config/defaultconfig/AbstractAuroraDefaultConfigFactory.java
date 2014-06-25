package net.gree.aurora.application.config.defaultconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;

/**
 * {@link AuroraDefaultConfigFactory}の骨格実装。
 */
public abstract class AbstractAuroraDefaultConfigFactory
        implements AuroraDefaultConfigFactory<Config> {

    /**
     * {@link Config}からポート番号を取得する。
     *
     * @param source ソース
     * @return ポート番号。存在しない場合はnull
     */
    protected Integer getPort(Config source) {
        try {
            return source.getInt("port");
        } catch (ConfigException.Missing e) {
            return null;
        }
    }

    /**
     * {@link Config}からマスターホスト名を取得する。
     *
     * @param source ソース
     * @return マスターホスト名。存在しない場合はnull
     */
    protected String getMasterHost(Config source) {
        try {
            return source.getString("master");
        } catch (ConfigException.Missing e) {
            return null;
        }
    }

    /**
     * {@link Config}からスレーブホスト名を取得する。
     *
     * @param source ソース
     * @return スレーブホスト名。存在しない場合はnull
     */
    protected String getSlaveHost(Config source) {
        try {
            return source.getString("slave");
        } catch (ConfigException.Missing e) {
            return null;
        }
    }

}
