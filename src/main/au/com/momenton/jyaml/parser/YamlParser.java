package au.com.momenton.jyaml.parser;

import au.com.momenton.jyaml.parser.strategy.Strategy;
import au.com.momenton.jyaml.parser.strategy.StrategyFactory;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class YamlParser {

    public static HashTree parse(Map<String, Object> yaml) {
        HashTree jmxRoot = new ListedHashTree();
        parse("TestPlan", yaml.get("TestPlan"), new HashMap<>(), jmxRoot);
        return jmxRoot;
    }

    @SuppressWarnings("rawtypes")
    private static void parse(String key, Object value, HashMap<String, Object> props, HashTree tree) {

        HashTree childTree = new ListedHashTree();
        HashMap<String, Object> childProps = new HashMap<>();

        if (value instanceof String) {
            props.put(key, value);
        } else if (value instanceof Integer) {
            props.put(key, value);
        } else if (value instanceof Boolean) {
            props.put(key, value);
        } else if (value instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap) value;
            Strategy strategy = StrategyFactory.getStrategy(key);
            map.forEach((k, v) -> {
                parse(k, v, childProps, childTree);
            });
            TestElement element = strategy.hydrate(childProps, map);
            if (element != null) {
                tree = tree.add(element);
            }
        } else if (value instanceof ArrayList) {
            ArrayList list = (ArrayList) value;
            for (Object itm : list) {
                parse(key, itm, childProps, childTree);
            }
        }

        if (childTree.size() > 0) {
            tree.add(childTree);
        }
        if (childProps.size() > 0) {
            props.put(key, childProps);
        }
    }

}
