package au.com.momenton.jyaml.parser.strategy;


import org.apache.jmeter.testelement.TestElement;

import java.util.LinkedHashMap;
import java.util.HashMap;

public interface Strategy {
    public TestElement hydrate(HashMap<String, Object> properties, LinkedHashMap childproperties);
}
