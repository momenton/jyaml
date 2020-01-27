package au.com.momenton.jyaml.jmeter.sampler;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.protocol.java.control.gui.BeanShellSamplerGui;
import org.apache.jmeter.protocol.java.sampler.BeanShellSampler;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class BeanShellSamplerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String fileName;
    @Setter
    private String parameters;
    @Setter
    private Boolean resetInterpreter;
    @Setter
    private String script;

    public TestElement build() {

        BeanShellSampler beanShell = new BeanShellSampler();
        beanShell.setName(name);
        beanShell.setEnabled(enabled);
        beanShell.setProperty(BeanShellSampler.TEST_CLASS, BeanShellSampler.class.getName());
        beanShell.setProperty(BeanShellSampler.GUI_CLASS, BeanShellSamplerGui.class.getName());
        if (comment != null) {
            beanShell.setComment(comment);
        }
        if (resetInterpreter != null) {
            beanShell.setProperty(BeanShellSampler.RESET_INTERPRETER, resetInterpreter);
        }
        if (parameters != null) {
            beanShell.setProperty(BeanShellSampler.PARAMETERS, parameters);
        }
        if (fileName != null) {
            beanShell.setProperty(BeanShellSampler.FILENAME, fileName);
        }
        if (script != null) {
            beanShell.setProperty(BeanShellSampler.SCRIPT, script);
        }

        return beanShell;
    }

}
