package au.com.momenton.jyaml.jmeter.control;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.control.TransactionController;
import org.apache.jmeter.control.gui.TransactionControllerGui;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class TransactionControllerBuilder implements Builder {

    public static final String INCLUDE_TIMERS = "TransactionController.includeTimers";
    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private Boolean includeTimers = false;
    @Setter
    private Boolean generateParentSample = false;

    public TestElement build() {

        TransactionController controller = new TransactionController();
        controller.setName(name);
        controller.setEnabled(enabled);
        if (comment != null) {
            controller.setComment(comment);
        }

        controller.setProperty(INCLUDE_TIMERS, includeTimers);
        controller.setGenerateParentSample(generateParentSample);

        controller.setProperty(TestElement.TEST_CLASS, TransactionController.class.getName());
        controller.setProperty(TestElement.GUI_CLASS, TransactionControllerGui.class.getName());

        return controller;
    }

}
