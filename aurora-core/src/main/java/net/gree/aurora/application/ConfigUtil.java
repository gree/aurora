package net.gree.aurora.application;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;

import java.util.List;

public class ConfigUtil {

    private static Config getConfig0(Config config, int index, List<String> keys) {
        Config result = null;
        try {
            result = config.getConfig(keys.get(index));
        } catch (ConfigException.Missing ex) {
            if (index + 1 < keys.size()) {
                result = getConfig0(config, index + 1, keys);
            } else {
                throw ex;
            }
        }
        return result;
    }

    public static Config getConfig(Config config, List<String> keys) {
        return getConfig0(config, 0, keys);
    }

    private static String getString0(Config config, int index, List<String> keys) {
        String result = null;
        try {
            result = config.getString(keys.get(index));
        } catch (ConfigException.Missing ex) {
            if (index + 1 < keys.size()) {
                result = getString0(config, index + 1, keys);
            } else {
                throw ex;
            }
        }
        return result;
    }

    public static String getString(Config config, List<String> keys) {
        return getString0(config, 0, keys);
    }

    private static List<? extends Config> getConfigList0(Config config, int index, List<String> keys) {
        List<? extends Config> result = null;
        try {
            result = config.getConfigList(keys.get(index));
        } catch (ConfigException.Missing ex) {
            if (index + 1 < keys.size()) {
                result = getConfigList0(config, index + 1, keys);
            } else {
                throw ex;
            }
        }
        return result;
    }

    public static List<? extends Config> getConfigList(Config config, List<String> keys) {
        return getConfigList0(config, 0, keys);
    }


}
