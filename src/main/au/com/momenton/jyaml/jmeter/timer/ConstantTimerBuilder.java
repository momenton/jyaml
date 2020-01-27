package au.com.momenton.jyaml.jmeter.timer;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.timers.gui.ConstantTimerGui;

@NoArgsConstructor
public class ConstantTimerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String delay;

    public TestElement build() {

        ConstantTimer timer = new ConstantTimer();
        timer.setName(name);
        timer.setEnabled(enabled);
        if (comment != null) {
            timer.setComment(comment);
        }
        timer.setProperty(ConstantTimer.TEST_CLASS, ConstantTimer.class.getName());
        timer.setProperty(ConstantTimer.GUI_CLASS, ConstantTimerGui.class.getName());
        if (delay != null) {
            timer.setDelay(delay);
        }

        return timer;
    }
}
