package net.gree.aurora.application.config;

import com.typesafe.config.Config;

import java.io.File;

/**
 * {@link AuroraTableNamesConfig}を設定ファイルから読み込むためのインターフェイス。
 */
public interface AuroraTableNamesConfigLoader {

    /**
     * {@link File 設定ファイル}から{@link AuroraTableNamesConfig}の集合をロードする。
     *
     * @param configFile {@link File 設定ファイル}
     * @return {@link AuroraTableNamesConfig}の集合
     */
    AuroraTableNamesConfig load(File configFile);

    /**
     * {@link Config 設定ファイル}から{@link AuroraTableNamesConfig}の集合をロードする。
     *
     * @param config {@link Config 設定ファイル}
     * @return {@link AuroraTableNamesConfig}の集合
     */
    AuroraTableNamesConfig load(Config config);

}
