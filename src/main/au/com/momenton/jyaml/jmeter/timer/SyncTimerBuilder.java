package au.com.momenton.jyaml.jmeter.timer;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.SyncTimer;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import  java.lang.Long;

@NoArgsConstructor
public class SyncTimerBuilder implements Builder {

    public final static String GROUPSIZE = "groupSize";
    public final static String TIMEOUT = "timeoutInMs";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private Integer groupSize;
    @Setter
    private Integer timeoutInMs;

    public TestElement build() {

        SyncTimer timer = new SyncTimer();
        timer.setName(name);
        timer.setEnabled(enabled);
        if (comment != null) {
            timer.setComment(comment);
        }
        timer.setProperty(SyncTimer.TEST_CLASS, SyncTimer.class.getName());
        timer.setProperty(SyncTimer.GUI_CLASS, TestBeanGUI.class.getName());

        if (groupSize != null) {
            timer.setProperty(GROUPSIZE, groupSize);
        }
        if (timeoutInMs != null) {
            timer.setProperty(TIMEOUT, Long.valueOf(timeoutInMs));
        }

        return timer;
    }
}
