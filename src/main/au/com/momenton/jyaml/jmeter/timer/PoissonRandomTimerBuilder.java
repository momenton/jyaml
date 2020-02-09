package au.com.momenton.jyaml.jmeter.timer;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.PoissonRandomTimer;
import org.apache.jmeter.timers.gui.PoissonRandomTimerGui;

@NoArgsConstructor
public class PoissonRandomTimerBuilder implements Builder {

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

        PoissonRandomTimer timer = new PoissonRandomTimer();
        timer.setName(name);
        timer.setEnabled(enabled);
        if (comment != null) {
            timer.setComment(comment);
        }
        timer.setProperty(PoissonRandomTimer.TEST_CLASS, PoissonRandomTimer.class.getName());
        timer.setProperty(PoissonRandomTimer.GUI_CLASS, PoissonRandomTimerGui.class.getName());
        if (delay != null) {
            timer.setDelay(delay);
        }
        if (range != null) {
            timer.setProperty(RANGE, range);
        }

        return timer;
    }
}
