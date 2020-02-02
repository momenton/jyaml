package au.com.momenton.jyaml.jmeter.assertions;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.assertions.JSR223Assertion;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class JSR223AssertionBuilder implements Builder {

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
    private Boolean cacheKey;
    @Setter
    private String script;

    public TestElement build() {

        JSR223Assertion assertion = new JSR223Assertion();

        assertion.setName(name);
        assertion.setEnabled(enabled);
        if (comment != null) {
            assertion.setComment(comment);
        }

        assertion.setProperty(JSR223Assertion.TEST_CLASS, JSR223Assertion.class.getName());
        assertion.setProperty(JSR223Assertion.GUI_CLASS, TestBeanGUI.class.getName());

        if (fileName != null) {
            assertion.setProperty(FILENAME, fileName);
        }
        if (parameters != null) {
            assertion.setProperty(PARAMETERS, parameters);
        }
        if (cacheKey != null) {
            assertion.setProperty(CACHE_KEY, cacheKey);
        }
        if (script != null) {
            assertion.setProperty(SCRIPT, script);
        }
        if (scriptLanguage != null) {
            assertion.setProperty(SCRIPT_LANGUAGE, scriptLanguage);
        }

        return assertion;
    }

}
