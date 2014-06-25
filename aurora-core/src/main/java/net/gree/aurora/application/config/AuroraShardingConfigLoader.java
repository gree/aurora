package net.gree.aurora.application.config;

import com.typesafe.config.Config;

import java.io.File;
import java.util.Set;

/**
 * {@link AuroraShardingConfig}を設定ファイルから読み込むためのインターフェイス。
 */
public interface AuroraShardingConfigLoader {

    /**
     * {@link File 設定ファイル}から{@link AuroraShardingConfig}の集合をロードする。
     *
     * @param configFile {@link File 設定ファイル}
     * @return {@link AuroraShardingConfig}の集合
     */
    Set<AuroraShardingConfig> load(File configFile);

    /**
     * {@link Config 設定ファイル}から{@link AuroraShardingConfig}の集合をロードする。
     *
     * @param config {@link Config 設定ファイル}
     * @return {@link AuroraShardingConfig}の集合
     */
    Set<AuroraShardingConfig> load(Config config);

}
