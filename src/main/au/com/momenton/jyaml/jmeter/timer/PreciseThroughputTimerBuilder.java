package au.com.momenton.jyaml.jmeter.timer;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.poissonarrivals.PreciseThroughputTimer;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.property.DoubleProperty;

@NoArgsConstructor
public class PreciseThroughputTimerBuilder implements Builder {

    public static String ALLOWED_THROUGHPUT_SURPLUS = "allowedThroughputSurplus";
    public static String BATCH_SIZE = "batchSize";
    public static String BATCH_THREAD_DELAY = "batchThreadDelay";
    public static String DURATION = "duration";
    public static String EXACT_LIMIT = "exactLimit";
    public static String RANDOM_SEED = "randomSeed";
    public static String THROUGHPUT = "throughput";
    public static String THROUGHPUT_PERIOD = "throughputPeriod";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private Double allowedThroughputSurplus;
    @Setter
    private Integer batchSize;
    @Setter
    private Integer batchThreadDelay;
    @Setter
    private Integer duration; //long
    @Setter
    private Integer exactLimit;
    @Setter
    private Integer randomSeed; //long
    @Setter
    private Double throughput;
    @Setter
    private Integer throughputPeriod;


    public TestElement build() {

        PreciseThroughputTimer timer = new PreciseThroughputTimer();
        timer.setName(name);
        timer.setEnabled(enabled);
        timer.setProperty(PreciseThroughputTimer.TEST_CLASS, PreciseThroughputTimer.class.getName());
        timer.setProperty(PreciseThroughputTimer.GUI_CLASS, TestBeanGUI.class.getName());

        if (comment != null) {
            timer.setComment(comment);
        }
        if (allowedThroughputSurplus != null) {
            DoubleProperty prop = new DoubleProperty(ALLOWED_THROUGHPUT_SURPLUS,allowedThroughputSurplus);
            timer.setProperty(prop);
        }
        if (batchSize != null) {
            timer.setProperty(BATCH_SIZE,batchSize);
        }
        if (batchThreadDelay != null) {
            timer.setProperty(BATCH_THREAD_DELAY,batchThreadDelay);
        }
        if (duration != null) {
            timer.setProperty(DURATION,Long.valueOf(duration));
        }
        if (exactLimit != null) {
            timer.setProperty(EXACT_LIMIT , exactLimit);
        }
        if (randomSeed != null) {
            timer.setProperty(RANDOM_SEED, Long.valueOf(randomSeed));
        }
        if (throughput != null) {
            DoubleProperty prop = new DoubleProperty(THROUGHPUT,throughput);
            timer.setProperty(prop);
        }
        if (throughputPeriod != null) {
            timer.setProperty(THROUGHPUT_PERIOD ,  throughputPeriod);
        }
        return timer;
    }

}
