package net.gree.aurora.application.config.defaultconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;

/**
 * {@link AuroraDefaultConfigFactory}の骨格実装。
 */
public abstract class AbstractDefaultConfigFactory implements AuroraDefaultConfigFactory<Config> {

    /**
     * {@link Config}からポート番号を取得する。
     *
     * @param source {@link Config}
     * @return ポート番号。存在しない場合null
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
     * @param source {@link Config}
     * @return マスターホスト名
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
     * @param source {@link Config}
     * @return スレーブホスト名
     */
    protected String getSlaveHost(Config source) {
        try {
            return source.getString("slave");
        } catch (ConfigException.Missing e) {
            return null;
        }
    }

}
