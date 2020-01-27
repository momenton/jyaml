package au.com.momenton.jyaml.jmeter.sampler;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.protocol.java.sampler.JSR223Sampler;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class JSR223SamplerBuilder implements Builder {

    public static String CACHE_KEY = "cacheKey";
    public static String PARAMETERS = "parameters";
    public static String FILENAME = "filename";
    public static String SCRIPT = "script";
    public static String SCRIPT_LANGUAGE = "scriptLanguage";

    @Setter
    private String name;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String comment;
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

        JSR223Sampler sampler = new JSR223Sampler();
        sampler.setName(name);
        sampler.setEnabled(enabled);
        sampler.setProperty(JSR223Sampler.TEST_CLASS, JSR223Sampler.class.getName());
        sampler.setProperty(JSR223Sampler.GUI_CLASS, TestBeanGUI.class.getName());
        if (comment != null) {
            sampler.setComment(comment);
        }
        if (fileName != null) {
            sampler.setProperty(FILENAME, fileName);
        }
        if (parameters != null) {
            sampler.setProperty(PARAMETERS, parameters);
        }
        if (cacheKey != null) {
            sampler.setProperty(CACHE_KEY, cacheKey);
        }
        if (script != null) {
            sampler.setProperty(SCRIPT, script);
        }
        if (scriptLanguage != null) {
            sampler.setProperty(SCRIPT_LANGUAGE, scriptLanguage);
        }
        return sampler;
    }

}
