package au.com.momenton.jyaml.jmeter.threads;

import au.com.momenton.jyaml.jmeter.Builder;
import com.blazemeter.jmeter.threads.concurrency.ConcurrencyThreadGroup;
import com.blazemeter.jmeter.threads.concurrency.ConcurrencyThreadGroupGui;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class ConcurrencyThreadGroupBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String targetLevel;
    @Setter
    private String rampUp;
    @Setter
    private String steps;
    @Setter
    private String hold;
    @Setter
    private String logFilename;
    @Setter
    private String iterations;
    @Setter
    private String units = Units.SECONDS.getValue();
    @Setter
    private String onSampleError = OnSampleError.CONTINUE.getValue();

    public TestElement build() {

        ConcurrencyThreadGroup threadGroup = new ConcurrencyThreadGroup();
        threadGroup.setName(name);
        threadGroup.setEnabled(enabled);
        if (comment != null) {
            threadGroup.setComment(comment);
        }
        if (targetLevel != null) {
            threadGroup.setTargetLevel(targetLevel);
        }
        if (rampUp != null) {
            threadGroup.setRampUp(rampUp);
        }
        if (steps != null) {
            threadGroup.setSteps(steps);
        }
        if (hold != null) {
            threadGroup.setHold(hold);
        }
        if (logFilename != null) {
            threadGroup.setLogFilename(logFilename);
        }
        if (iterations != null) {
            threadGroup.setIterationsLimit(iterations);
        }

        threadGroup.setUnit(units);


        threadGroup.setProperty(ConcurrencyThreadGroup.ON_SAMPLE_ERROR, onSampleError);
        threadGroup.setProperty(ConcurrencyThreadGroup.TEST_CLASS, ConcurrencyThreadGroup.class.getName());
        threadGroup.setProperty(ConcurrencyThreadGroup.GUI_CLASS, ConcurrencyThreadGroupGui.class.getName());

        return threadGroup;
    }

    @AllArgsConstructor
    public enum OnSampleError {
        CONTINUE("continue"),
        STARTNEXTTHREADLOOP("startnextloop"),
        STOPTHREAD("stopthread"),
        STOPTEST("stoptest"),
        STOPTESTNOW("stoptestnow");

        @Getter
        private String value;

    }

    @AllArgsConstructor
    public enum Units {
        SECONDS("S"),
        MINUTES("M");

        @Getter
        private String value;
    }

}
