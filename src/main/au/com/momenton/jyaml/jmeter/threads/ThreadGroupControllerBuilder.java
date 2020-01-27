package au.com.momenton.jyaml.jmeter.threads;

import au.com.momenton.jyaml.jmeter.Builder;
import au.com.momenton.jyaml.jmeter.control.LoopControllerBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

@NoArgsConstructor
public class ThreadGroupControllerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private Integer numThreads = 1;
    @Setter
    private Integer rampUp;
    @Setter
    private Boolean delayedStart;
    @Setter
    private Boolean scheduler;
    @Setter
    private Integer duration;
    @Setter
    private Integer delay;
    @Setter
    private Integer loops = 1;
    @Setter
    private Boolean continueForever = false;
    @Setter
    private String onSampleError = OnSampleError.CONTINUE.getValue();

    public TestElement build() {

        LoopControllerBuilder sampleControllerBuilder = new LoopControllerBuilder();
        sampleControllerBuilder.setContinueForever(continueForever);
        sampleControllerBuilder.setLoops(loops);
        sampleControllerBuilder.setName(name + "LoopController");
        sampleControllerBuilder.setEnabled(enabled);
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName(name);
        threadGroup.setEnabled(enabled);
        if (comment != null) {
            threadGroup.setComment(comment);
        }
        threadGroup.setNumThreads(numThreads);
        if (rampUp != null) {
            threadGroup.setRampUp(rampUp);
        }
        threadGroup.setSamplerController((LoopController) sampleControllerBuilder.build());
        if (delay != null) {
            threadGroup.setDelay(delay);
        }
        if (scheduler != null) {
            threadGroup.setScheduler(scheduler);
        }
        if (duration != null) {
            threadGroup.setDuration(duration);
        }
        threadGroup.setProperty(ThreadGroup.ON_SAMPLE_ERROR, onSampleError);
        if (delayedStart != null) {
            threadGroup.setProperty(new BooleanProperty(ThreadGroup.DELAYED_START, delayedStart));
        }
        threadGroup.setProperty(ThreadGroup.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(ThreadGroup.GUI_CLASS, ThreadGroupGui.class.getName());
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
}

