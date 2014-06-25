package net.gree.aurora.application;

import com.typesafe.config.Config;
import org.sisioh.dddbase.utils.Try;

import java.io.File;

/**
 * 設定ファイルを読み込み、{@link Repositories} を生成するサービス。
 */
public interface AuroraShardingConfigLoadService {

    /**
     * 設定ファイルから読み込み、{@link Repositories} を生成する。
     *
     * @param configFile 設定ファイル
     * @return Tryでラップされた{@link Repositories}
     */
    Try<Repositories> loadFromConfigFile(File configFile);

    /**
     * 設定ファイルから読み込み、{@link Repositories} を生成する。
     *
     * @param config 設定ファイル
     * @return Tryでラップされた{@link Repositories}
     */
    Try<Repositories> loadFromConfig(Config config);

}
