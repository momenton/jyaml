package au.com.momenton.jyaml.parser.strategy;

import au.com.momenton.jyaml.jmeter.testelement.TestPlanBuilder;
import au.com.momenton.jyaml.parser.strategy.DefaultStrategy;
import org.apache.jmeter.config.Argument;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class TestPlanStrategy extends DefaultStrategy {

    public TestPlanStrategy(String name) {
        super(name);
    }

    @Override
    protected boolean skipProperty(String name) {
        return name.equals("UserVariables");
    }

    @Override
    protected void setChildProperties(LinkedHashMap props) {
        List<Argument> argumentList = new ArrayList<>();
        List<LinkedHashMap> variables = (List<LinkedHashMap>) props.get("UserVariables");
        if (variables == null) {
            return;
        }
        variables.forEach(variable -> {
            variable.forEach(
                    (k, v) -> {
                        Argument arg = new Argument();
                        arg.setName((String) k);
                        arg.setValue(String.valueOf(v));
                        argumentList.add(arg);
                    }
            );
        });
        ((TestPlanBuilder) builder).setUserVariables(argumentList);
    }
}
