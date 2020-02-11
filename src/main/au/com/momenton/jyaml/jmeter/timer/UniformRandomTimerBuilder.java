package au.com.momenton.jyaml.jmeter.timer;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.UniformRandomTimer;
import org.apache.jmeter.timers.gui.UniformRandomTimerGui;

@NoArgsConstructor
public class UniformRandomTimerBuilder implements Builder {

    public final static String DELAY = "ConstantTimer.delay";
    public final static String RANGE = "RandomTimer.range";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String delay;
    @Setter
    private String range;

    public TestElement build() {

        UniformRandomTimer timer = new UniformRandomTimer();
        timer.setName(name);
        timer.setEnabled(enabled);
        if (comment != null) {
            timer.setComment(comment);
        }
        timer.setProperty(UniformRandomTimer.TEST_CLASS, UniformRandomTimer.class.getName());
        timer.setProperty(UniformRandomTimer.GUI_CLASS, UniformRandomTimerGui.class.getName());
        if (delay != null) {
            timer.setDelay(delay);
        }
        if (range != null) {
            timer.setProperty(RANGE, range);
        }

        return timer;
    }
}
