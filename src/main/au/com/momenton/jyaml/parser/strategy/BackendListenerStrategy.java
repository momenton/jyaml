package au.com.momenton.jyaml.parser.strategy;

import au.com.momenton.jyaml.jmeter.visualizers.BackendListenerBuilder;
import au.com.momenton.jyaml.parser.strategy.DefaultStrategy;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Optional;

public class BackendListenerStrategy extends DefaultStrategy {

    public BackendListenerStrategy(String name) {
        super(name);
    }

    @Override
    protected void setChildProperties(LinkedHashMap props) {
        List<Argument> argumentList = new ArrayList<>();
        List<LinkedHashMap> argumentsList = (List<LinkedHashMap>) props.get("Arguments");
        if (argumentsList == null) {
            return;
        }
        argumentsList.forEach(arguments -> {
            arguments.forEach(
                    (k, v) -> {
                        Argument argument = new Argument();
                        argument.setName((String) k);
                        argument.setValue(String.valueOf(v));
                        argumentList.add(argument);
                    }
            );
        });
        ((BackendListenerBuilder) builder).setArguments(argumentList);
    }
}
