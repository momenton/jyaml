package au.com.momenton.jyaml.jmeter.control;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class LoopControllerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private Integer loops = 1;
    @Setter
    private Boolean continueForever = true;

    public TestElement build() {

        LoopController controller = new LoopController();
        controller.setName(name);
        if (comment != null) {
            controller.setComment(comment);
        }
        controller.setEnabled(enabled);
        controller.setContinueForever(continueForever);
        if (!continueForever) {
            controller.setLoops(loops);
        } else {
            controller.setLoops(-1);
        }
        controller.setProperty(LoopController.TEST_CLASS, LoopController.class.getName());
        controller.setProperty(LoopController.GUI_CLASS, LoopControlPanel.class.getName());
        return controller;
    }
}

