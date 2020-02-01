package au.com.momenton.jyaml.parser.strategy;

import au.com.momenton.jyaml.jmeter.assertions.ResponseAssertionBuilder;
import au.com.momenton.jyaml.parser.strategy.DefaultStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.LinkedHashMap;
import java.util.*;

public class ResponseAssertionStrategy extends DefaultStrategy {

    public ResponseAssertionStrategy(String name) {
        super(name);
    }

    @Override
    protected void setChildProperties(LinkedHashMap props) {
        ArrayList testMap = (ArrayList) props.get("testStrings");
        if (testMap == null) {
            return;
        }
        List<String> testStrings = (List<String>) testMap
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        ((ResponseAssertionBuilder) builder).setTestStrings(testStrings);
    }
}
