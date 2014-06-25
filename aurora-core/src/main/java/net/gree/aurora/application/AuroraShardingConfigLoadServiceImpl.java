package net.gree.aurora.application;

import com.typesafe.config.Config;
import org.sisioh.dddbase.utils.Function1;
import org.sisioh.dddbase.utils.Try;

import java.io.File;

final class AuroraShardingConfigLoadServiceImpl implements AuroraShardingConfigLoadService {

    private final ShardingConfigModelsParser parser = new ShardingConfigModelsParser();

    private final Function1<ShardingConfigModelsParser.ParseResult, Repositories> f = new Function1<ShardingConfigModelsParser.ParseResult, Repositories>() {
        @Override
        public Repositories apply(ShardingConfigModelsParser.ParseResult value) {
            return new RepositoriesImpl(value.dataSourceRepository, value.shardingConfigRepository);
        }
    };

    @Override
    public Try<Repositories> loadFromConfigFile(File configFile) {
        return parser.parse(configFile).map(f);
    }

    @Override
    public Try<Repositories> loadFromConfig(Config config) {
        return parser.parse(config).map(f);
    }

}
