package au.com.momenton.jyaml.jmeter.reporters;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.SimpleDataWriter;

@NoArgsConstructor
public class SimpleDataWriterBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String fileName;

    public TestElement build() {

        ResultCollector collector = new ResultCollector();
        collector.setName(name);
        collector.setEnabled(enabled);
        if (comment != null) {
            collector.setComment(comment);
        }
        if (fileName != null) {
            collector.setFilename(fileName);
        }
        collector.setProperty(ResultCollector.TEST_CLASS, ResultCollector.class.getName());
        collector.setProperty(ResultCollector.GUI_CLASS, SimpleDataWriter.class.getName());

        SampleSaveConfiguration config = collector.getSaveConfig();
        config.setAssertionResultsFailureMessage(true);
        collector.setSaveConfig(config);

        return collector;

    }

}
