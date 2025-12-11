package attic.notium.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ConfigManager<T extends Config> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Core/ConfigManager");
    private final Path CONFIG_PATH = Path.of("config", "attic/notium.toml");
    private final Class<T> configClass;
    public T config;

    public ConfigManager(Class<T> configClass) {
        this.configClass = configClass;
    }

    public void load() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            if (!Files.exists(CONFIG_PATH)) {
                Files.createFile(CONFIG_PATH);
            }

            CommentedFileConfig fileConfig = CommentedFileConfig.builder(CONFIG_PATH).build();
            fileConfig.load();

            config = loadRecord(configClass, fileConfig);
            updateFile(config);
        } catch (Exception e) {
            LOGGER.warn("Config is not loaded", e);
        }
    }

    public void save() {
        try {
            if (config == null) return;
            updateFile(config);
        } catch (Exception e) {
            LOGGER.warn("Config is not saved", e);
        }
    }

    public T get() {
        return config;
    }

    private void updateFile(Config configObj)
            throws Exception {

        Files.createDirectories(CONFIG_PATH.getParent());
        if (!Files.exists(CONFIG_PATH)) {
            Files.createFile(CONFIG_PATH);
        }

        CommentedFileConfig fileConfig = CommentedFileConfig.builder(CONFIG_PATH).build();
        fileConfig.load();

        processRecord(configObj, fileConfig);

        fileConfig.save();
    }

    private void processRecord(Object recordObj, CommentedConfig section)
            throws InvocationTargetException, IllegalAccessException {

        Class<?> clazz = recordObj.getClass();
        if (!clazz.isRecord()) return;

        for (RecordComponent component : clazz.getRecordComponents()) {
            String key = component.getName();
            Object value = component.getAccessor().invoke(recordObj);

            if (value != null && value.getClass().isRecord()) {
                CommentedConfig nested = section.get(key);
                if (!(nested instanceof CommentedConfig)) {
                    nested = CommentedConfig.inMemory();
                    section.set(key, nested);
                }
                processRecord(value, nested);
            } else {
                if (!section.contains(key)) {
                    section.set(key, value);
                }
            }
        }
    }

    private <T> T loadRecord(Class<T> recordClass, CommentedConfig section) throws Exception {
        if (!recordClass.isRecord()) return null;

        T instance = recordClass.getDeclaredConstructor().newInstance();

        RecordComponent[] components = recordClass.getRecordComponents();
        Object[] args = new Object[components.length];

        for (int i = 0; i < components.length; i++) {
            RecordComponent comp = components[i];
            String key = comp.getName();
            Class<?> type = comp.getType();

            Object currentDefaultValue = comp.getAccessor().invoke(instance);
            Object storedValue = section.get(key);

            if (storedValue == null) {
                args[i] = currentDefaultValue;
            } else if (type.isRecord() && storedValue instanceof CommentedConfig nestedSection) {
                args[i] = loadRecord(type, nestedSection);
            } else {
                args[i] = storedValue;
            }
        }

        Constructor<T> constructor = recordClass.getDeclaredConstructor(
                Arrays.stream(components).map(RecordComponent::getType).toArray(Class[]::new)
        );
        return constructor.newInstance(args);
    }
}