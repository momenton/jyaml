package au.com.momenton.jyaml.jmeter.extractor;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.extractor.BeanShellPostProcessor;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class BeanShellPostProcessorBuilder implements Builder {

    public static String RESET_INTERPRETER = "resetInterpreter";
    public static String PARAMETERS = "parameters";
    public static String FILENAME = "filename";
    public static String SCRIPT = "script";

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

        BeanShellPostProcessor beanShell = new BeanShellPostProcessor();
        beanShell.setName(name);
        beanShell.setEnabled(enabled);
        beanShell.setProperty(BeanShellPostProcessor.TEST_CLASS, BeanShellPostProcessor.class.getName());
        beanShell.setProperty(BeanShellPostProcessor.GUI_CLASS, TestBeanGUI.class.getName());
        if (comment != null) {
            beanShell.setComment(comment);
        }
        if (resetInterpreter != null) {
            beanShell.setProperty(RESET_INTERPRETER, resetInterpreter);
        }
        if (parameters != null) {
            beanShell.setProperty(PARAMETERS, parameters);
        }
        if (fileName != null) {
            beanShell.setProperty(FILENAME, fileName);
        }
        if (script != null) {
            beanShell.setProperty(SCRIPT, script);
        }

        return beanShell;
    }
}
