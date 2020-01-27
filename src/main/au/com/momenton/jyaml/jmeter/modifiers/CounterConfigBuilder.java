package au.com.momenton.jyaml.jmeter.modifiers;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.modifiers.CounterConfig;
import org.apache.jmeter.modifiers.gui.CounterConfigGui;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class CounterConfigBuilder implements Builder {

    public static final String REFERENCE_NAME = "CounterConfig.name";
    public static final String PER_USER = "CounterConfig.per_user";
    public static final String RESET_ON_THREADGROUP_ITERTAION = "CounterConfig.reset_on_tg_iteration";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String start;
    @Setter
    private String end;
    @Setter
    private String incr;
    @Setter
    private String format;
    @Setter
    private String referenceName;
    @Setter
    private Boolean perUser;
    @Setter
    private Boolean resetOnThreadGroupIteration;

    public TestElement build() {

        CounterConfig config = new CounterConfig();
        config.setProperty(CounterConfig.TEST_CLASS, CounterConfig.class.getName());
        config.setProperty(CounterConfig.GUI_CLASS, CounterConfigGui.class.getName());
        if (referenceName != null) {
            config.setProperty(REFERENCE_NAME, referenceName);
        }
        if (perUser != null) {
            config.setProperty(PER_USER, perUser);
        }
        if (resetOnThreadGroupIteration != null) {
            config.setProperty(RESET_ON_THREADGROUP_ITERTAION, resetOnThreadGroupIteration);
        }
        config.setName(name);
        config.setEnabled(enabled);
        if (comment != null) {
            config.setComment(comment);
        }
        if (start != null) {
            config.setStart(start);
        }
        if (end != null) {
            config.setEnd(end);
        }
        if (incr != null) {
            config.setIncrement(incr);
        }
        if (format != null) {
            config.setFormat(format);
        }

        return config;

    }

}
