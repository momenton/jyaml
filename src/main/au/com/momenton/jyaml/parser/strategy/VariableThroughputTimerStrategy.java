package au.com.momenton.jyaml.parser.strategy;

import au.com.momenton.jyaml.jmeter.timer.VariableThroughputTimerBuilder;
import au.com.momenton.jyaml.parser.strategy.DefaultStrategy;
import org.apache.jmeter.config.Argument;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.LinkedHashMap;

public class VariableThroughputTimerStrategy extends DefaultStrategy {

    public VariableThroughputTimerStrategy(String name) {
        super(name);
    }

    @Override
    protected void setChildProperties(LinkedHashMap props) {
        List<Argument> argumentList = new ArrayList<>();
        List<LinkedHashMap> schedules = (List<LinkedHashMap>) props.get("Schedule");
        if (schedules == null) {
            return;
        }
        schedules.forEach(schedule ->
                ((VariableThroughputTimerBuilder) builder).addSchedule(
                        String.valueOf(schedule.get("start")),
                        String.valueOf(schedule.get("end")),
                        String.valueOf(schedule.get("duration"))
                ));
    }
}
