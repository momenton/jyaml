package au.com.momenton.jyaml.jmeter.timer;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.ConstantThroughputTimer;
import org.apache.jmeter.timers.ConstantTimer;

@NoArgsConstructor
public class ConstantThroughputTimerBuilder implements Builder {

    public final static String THROUGHPUT = "throughput";
    public final static String CALC_MODE = "calcMode";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String throughput;

    @Setter
    private Integer calcMode;

    public TestElement build() {

        ConstantThroughputTimer timer = new ConstantThroughputTimer();
        timer.setName(name);
        timer.setEnabled(enabled);
        if (comment != null) {
            timer.setComment(comment);
        }
        timer.setProperty(ConstantTimer.TEST_CLASS, ConstantTimer.class.getName());
        timer.setProperty(ConstantTimer.GUI_CLASS, TestBeanGUI.class.getName());

        if (throughput != null) {
            timer.setProperty(THROUGHPUT, throughput);
        }

        if (calcMode != null) {
            timer.setProperty(CALC_MODE, calcMode);
        }

        return timer;
    }
}
