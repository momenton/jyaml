package au.com.momenton.jyaml.parser;
import org.yaml.snakeyaml.Yaml;
import au.com.momenton.jyaml.parser.strategy.Strategy;
import au.com.momenton.jyaml.parser.strategy.StrategyFactory;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import java.lang.IllegalArgumentException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import com.hubspot.jinjava.Jinjava;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import com.hubspot.jinjava.loader.FileLocator;
import java.io.IOException;

public abstract class YamlParser {

    static final Logger logger = LogManager.getLogger(YamlParser.class.getName());

    public static HashTree parse(String inputFile, String configFile) {

        Map<String, Object> context = new HashMap<>();
        if (configFile != null){
            try{
                Path configPath = Paths.get(configFile);
                String config = new String(Files.readAllBytes(configPath));
                context = (new Yaml()).load(config);
            } catch(IOException ex){
                logger.error("Config file does not exist " +configFile );
                throw new IllegalArgumentException("Config file does not exist");
            }
        }

        String input = null;
        try {
            Path path = Paths.get(inputFile);
            input = new String(Files.readAllBytes(path));
        } catch(IOException ioe){
            logger.error("Failed to read Input file");
            throw new IllegalArgumentException("Failed to read Input file");
        }

        Jinjava jinjava = new Jinjava();
        jinjava.setResourceLocator(new FileLocator());
        String inputPreProcessed = jinjava.render(input,context);

        System.out.println(inputPreProcessed );
        Map<String, Object> yaml = (new Yaml()).load(inputPreProcessed);

        HashTree jmxRoot = new ListedHashTree();
        parseKey("TestPlan", yaml.get("TestPlan"), new HashMap<>(), jmxRoot);
        return jmxRoot;
    }

    public static HashTree parse(Map<String, Object> yaml) {
        HashTree jmxRoot = new ListedHashTree();
        parseKey("TestPlan", yaml.get("TestPlan"), new HashMap<>(), jmxRoot);
        return jmxRoot;
    }

    @SuppressWarnings("rawtypes")
    private static void parseKey(String key, Object value, HashMap<String, Object> props, HashTree tree) {

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
                parseKey(k, v, childProps, childTree);
            });
            TestElement element = strategy.hydrate(childProps, map);
            if (element != null) {
                tree = tree.add(element);
            }
        } else if (value instanceof ArrayList) {
            ArrayList list = (ArrayList) value;
            for (Object itm : list) {
                parseKey(key, itm, childProps, childTree);
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
