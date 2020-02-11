package au.com.momenton.jyaml.parser.strategy;

import au.com.momenton.jyaml.jmeter.Builder;
import au.com.momenton.jyaml.jmeter.BuilderFactory;
import org.apache.jmeter.testelement.TestElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DefaultStrategy implements Strategy {

    static final Logger logger = LogManager.getLogger(DefaultStrategy.class.getName());

    protected final Builder builder;

    public DefaultStrategy(String name) {
        builder = BuilderFactory.getBuilder(name);
    }

    protected String getSetterName(String input) {
        char c = input.charAt(0);
        String s = new String("" + c);
        return "set" + s.toUpperCase() + input.substring(1);
    }

    public TestElement hydrate(HashMap<String, Object> properties, LinkedHashMap childproperties) {
        if (builder == null) {
            return null;
        }
        setProperties(properties);
        setChildProperties(childproperties);
        return builder.build();
    }

    protected boolean skipProperty(String name) {
        return false;
    }

    protected void setChildProperties(LinkedHashMap childproperties) {

    }

    private void setProperty(String name, Object value) {
        try {
            if (value instanceof Integer) {
                try {
                    //Try integer first
                    Method method = builder.getClass().getMethod(getSetterName(name), value.getClass());
                    method.invoke(builder, value);
                } catch (Exception ex) {
                    //Try string next
                    String strValue = String.valueOf(value);
                    Method method = builder.getClass().getMethod(getSetterName(name), strValue.getClass());
                    method.invoke(builder, strValue);
                }
            } else {
                Method method = builder.getClass().getMethod(getSetterName(name), value.getClass());
                method.invoke(builder, value);
            }
        } catch (NoSuchMethodException nsm) {
            logger.warn("No such method " + nsm.getMessage());
        } catch (IllegalAccessException | InvocationTargetException iae) {
            logger.warn("Access issue " + iae.getMessage());
        }
    }

    private void setProperties(HashMap<String, Object> props) {
        props.entrySet()
                .stream()
                .filter(e -> !skipProperty(e.getKey()))
                .forEach(e -> {
                    setProperty(e.getKey(), e.getValue());
                });
    }

}
