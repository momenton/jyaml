package au.com.momenton.jyaml.jmeter.reporters;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.StatVisualizer;

@NoArgsConstructor
public class AggregrateReportBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;

    public TestElement build() {

        ResultCollector collector = new ResultCollector();
        collector.setName(name);
        collector.setEnabled(enabled);
        if (comment != null) {
            collector.setComment(comment);
        }
        collector.setProperty(ResultCollector.TEST_CLASS, ResultCollector.class.getName());
        collector.setProperty(ResultCollector.GUI_CLASS, StatVisualizer.class.getName());

        return collector;
    }

}
