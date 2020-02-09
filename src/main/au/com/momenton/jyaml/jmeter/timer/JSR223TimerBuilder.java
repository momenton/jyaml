package au.com.momenton.jyaml.jmeter.timer;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.timers.JSR223Timer;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class JSR223TimerBuilder implements Builder {

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

        JSR223Timer Timer = new JSR223Timer();
        Timer.setName(name);
        Timer.setEnabled(enabled);
        Timer.setProperty(JSR223Timer.TEST_CLASS, JSR223Timer.class.getName());
        Timer.setProperty(JSR223Timer.GUI_CLASS, TestBeanGUI.class.getName());
        if (comment != null) {
            Timer.setComment(comment);
        }
        if (fileName != null) {
            Timer.setProperty(FILENAME, fileName);
        }
        if (parameters != null) {
            Timer.setProperty(PARAMETERS, parameters);
        }
        if (cacheKey != null) {
            Timer.setProperty(CACHE_KEY, cacheKey);
        }
        if (script != null) {
            Timer.setProperty(SCRIPT, script);
        }
        if (scriptLanguage != null) {
            Timer.setProperty(SCRIPT_LANGUAGE, scriptLanguage);
        }
        return Timer;
    }

}
