package at.irian.cdiatwork.ideafork.config.client.internal;

import at.irian.cdiatwork.ideafork.config.client.TypedConfig;
import at.irian.cdiatwork.ideafork.config.client.internal.context.ConfigScoped;
import at.irian.cdiatwork.ideafork.config.client.internal.remote.ConfigService;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.deltaspike.core.util.ExceptionUtils;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TypedConfig
@ConfigScoped
@SuppressWarnings("unused")
public class TypedConfigHandler implements InvocationHandler {
    @Inject
    private ConfigService configService;

    private Map<String, Object> loadedValues = new ConcurrentHashMap<String, Object>();

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String key = method.getName();
        Object result = loadedValues.get(key);

        if (result != null) {
            return result;
        }

        //don't use ConfigService in a org.apache.deltaspike.core.spi.config.ConfigSource to limit the remote-lookup to typed-configs
        String loadedValue = null;

        TypedConfig typedConfig = proxy.getClass().getAnnotation(TypedConfig.class);
        if (typedConfig != null && typedConfig.remote()) {
            loadedValue = configService.loadForKey(key);
        }

        if (loadedValue == null) {
            loadedValue = ConfigResolver.getProjectStageAwarePropertyValue(key);
        }
        final Class<?> configType = method.getReturnType();
        result = parseValue(loadedValue, configType);

        loadedValues.put(key, result);
        return result;
    }

    private Object parseValue(String loadedValue, Class<?> configType) {
        if (loadedValue != null) {
            if (configType.equals(Integer.class)) {
                return Integer.parseInt(loadedValue);
            } else if (configType.equals(String.class)) {
                return loadedValue;
            } else {
                try {
                    Constructor<?> constructor = configType.getConstructor(String.class);
                    return constructor.newInstance(loadedValue);
                } catch (Exception e) {
                    throw ExceptionUtils.throwAsRuntimeException(e);
                }
            }
        }
        return null;
    }
}
