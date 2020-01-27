package au.com.momenton.jyaml.jmeter.modifiers;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.modifiers.JSR223PreProcessor;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class JSR223PreProcessorBuilder implements Builder {

    public static String CACHE_KEY = "cacheKey";
    public static String PARAMETERS = "parameters";
    public static String FILENAME = "filename";
    public static String SCRIPT = "script";
    public static String SCRIPT_LANGUAGE = "scriptLanguage";
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
    private String scriptLanguage;
    @Setter
    private String cacheKey;
    @Setter
    private String script;

    public TestElement build() {

        JSR223PreProcessor preprocessor = new JSR223PreProcessor();
        preprocessor.setName(name);
        preprocessor.setEnabled(enabled);
        preprocessor.setProperty(JSR223PreProcessor.TEST_CLASS, JSR223PreProcessor.class.getName());
        preprocessor.setProperty(JSR223PreProcessor.GUI_CLASS, TestBeanGUI.class.getName());
        if (comment != null) {
            preprocessor.setComment(comment);
        }
        if (fileName != null) {
            preprocessor.setProperty(FILENAME, fileName);
        }
        if (parameters != null) {
            preprocessor.setProperty(PARAMETERS, parameters);
        }
        if (cacheKey != null) {
            preprocessor.setProperty(CACHE_KEY, cacheKey);
        }
        if (script != null) {
            preprocessor.setProperty(SCRIPT, script);
        }
        if (scriptLanguage != null) {
            preprocessor.setProperty(SCRIPT_LANGUAGE, scriptLanguage);
        }

        return preprocessor;
    }

}
