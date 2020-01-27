package au.com.momenton.jyaml.jmeter.config;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.testelement.TestElement;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class UserDefinedVariablesBuilder implements Builder {

    @Setter
    private List<Argument> userVariables = new ArrayList<>();
    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;

    public TestElement build() {

        Arguments args = new Arguments();
        args.setName(name);
        args.setEnabled(enabled);
        if (comment != null) {
            args.setComment(comment);
        }
        for (Argument userVariable : userVariables) {
            args.addArgument(userVariable);
        }
        args.setProperty(Arguments.TEST_CLASS, Arguments.class.getName());
        args.setProperty(Arguments.GUI_CLASS, ArgumentsPanel.class.getName());

        return args;
    }
}
