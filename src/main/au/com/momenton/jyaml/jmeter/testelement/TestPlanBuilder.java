package au.com.momenton.jyaml.jmeter.testelement;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;

import java.util.List;

@NoArgsConstructor
public class TestPlanBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private Boolean functionalMode;
    @Setter
    private Boolean tearDownOnShutdown;
    @Setter
    private Boolean serialized;
    @Setter
    private List<Argument> userVariables;

    public TestElement build() {

        TestPlan testPlan = new TestPlan();
        testPlan.setName(name);
        testPlan.setEnabled(enabled);

        if (comment != null) {
            testPlan.setComment(comment);
        }

        if (functionalMode != null) {
            testPlan.setFunctionalMode(functionalMode);
        }
        if (serialized != null) {
            testPlan.setSerialized(serialized);
        }
        if (tearDownOnShutdown != null) {
            testPlan.setTearDownOnShutdown(tearDownOnShutdown);
        }
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        Arguments args = new Arguments();
        if (userVariables != null) {
            userVariables.forEach(arg -> {
                args.addArgument(arg);
            });
        }
        testPlan.setUserDefinedVariables(args);
        return testPlan;
    }
}

