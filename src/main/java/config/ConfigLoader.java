
package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private Properties props;
    private boolean isRemote;

    public ConfigLoader(String filePath) {
        props = new Properties();
        try {
            InputStream inputStream = new FileInputStream(filePath);
            props.load(inputStream);
            isRemote = Boolean.parseBoolean(props.getProperty("isRemote"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRemote() {
        return isRemote;
    }
}
