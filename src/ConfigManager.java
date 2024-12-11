import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private Properties properties;
    private String environment;

    public ConfigManager(String filePath, String environment) throws IOException {
        this.environment = environment;
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (input == null) {
                throw new IOException("Configuration file '" + filePath + "' not found in the classpath");
            }
            properties.load(input);
        }
    }

    public String getProperty(String key) {
        String envKey = environment + "." + key;
        return properties.getProperty(envKey);    }
}
