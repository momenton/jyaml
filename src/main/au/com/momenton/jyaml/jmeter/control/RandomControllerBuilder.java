package au.com.momenton.jyaml.jmeter.control;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.control.InterleaveControl;
import org.apache.jmeter.control.RandomController;
import org.apache.jmeter.control.gui.RandomControlGui;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class RandomControllerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private Boolean ignoreSubControllerBlocks = false;

    public TestElement build() {

        RandomController controller = new RandomController();
        controller.setName(name);
        controller.setEnabled(enabled);
        if (comment != null) {
            controller.setComment(comment);
        }
        if (ignoreSubControllerBlocks) {
            controller.setStyle(InterleaveControl.IGNORE_SUB_CONTROLLERS);
        } else {
            controller.setStyle(InterleaveControl.USE_SUB_CONTROLLERS);
        }
        controller.setProperty(TestElement.TEST_CLASS, RandomController.class.getName());
        controller.setProperty(TestElement.GUI_CLASS, RandomControlGui.class.getName());

        return controller;
    }

}
