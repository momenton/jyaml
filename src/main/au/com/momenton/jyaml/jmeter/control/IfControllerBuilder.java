package au.com.momenton.jyaml.jmeter.control;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.control.IfController;
import org.apache.jmeter.control.gui.IfControllerPanel;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class IfControllerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String condition;
    @Setter
    private Boolean evaluateAll;
    @Setter
    private Boolean useExpression;

    public TestElement build() {

        IfController controller = new IfController();
        controller.setName(name);
        controller.setEnabled(enabled);
        controller.setProperty(IfController.TEST_CLASS, IfController.class.getName());
        controller.setProperty(IfController.GUI_CLASS, IfControllerPanel.class.getName());
        if (comment != null) {
            controller.setComment(comment);
        }
        if (condition != null) {
            controller.setCondition(condition);
        }
        if (evaluateAll != null) {
            controller.setEvaluateAll(evaluateAll);
        }
        if (useExpression != null) {
            controller.setUseExpression(useExpression);
        }

        return controller;
    }

}
