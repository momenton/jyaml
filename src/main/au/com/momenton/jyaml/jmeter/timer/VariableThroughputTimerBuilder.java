package au.com.momenton.jyaml.jmeter.timer;

import au.com.momenton.jyaml.jmeter.Builder;
import kg.apc.jmeter.timers.VariableThroughputTimer;
import kg.apc.jmeter.timers.VariableThroughputTimerGui;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.timers.ConstantTimer;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class VariableThroughputTimerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    private List<CollectionProperty> schedule = new ArrayList<>();

    public void addSchedule(String startRPS, String endRPS, String duration) {

        CollectionProperty prop = new CollectionProperty();
        prop.setName("1");
        prop.addProperty(new StringProperty("1", startRPS));
        prop.addProperty(new StringProperty("2", endRPS));
        prop.addProperty(new StringProperty("3", duration));
        this.schedule.add(prop);
    }

    public TestElement build() {

        VariableThroughputTimer timer = new VariableThroughputTimer();
        timer.setName(name);
        timer.setEnabled(enabled);
        if (comment != null) {
            timer.setComment(comment);
        }
        timer.setProperty(VariableThroughputTimer.TEST_CLASS, ConstantTimer.class.getName());
        timer.setProperty(VariableThroughputTimer.GUI_CLASS, VariableThroughputTimerGui.class.getName());
        CollectionProperty loadProfile = new CollectionProperty();
        loadProfile.setName("load_profile");
        schedule.forEach(schedule -> {
            loadProfile.addProperty(schedule);
        });
        timer.setData(loadProfile);

        return timer;
    }
}
