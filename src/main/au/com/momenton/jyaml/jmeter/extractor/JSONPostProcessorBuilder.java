package au.com.momenton.jyaml.jmeter.extractor;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor;
import org.apache.jmeter.extractor.json.jsonpath.gui.JSONPostProcessorGui;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class JSONPostProcessorBuilder implements Builder {

    public static String REFERENCENAMES = "JSONPostProcessor.referenceNames";
    public static String JSONPATHEXPRESSIONS = "JSONPostProcessor.jsonPathExprs";
    public static String MATCHNUMBERS = "JSONPostProcessor.match_numbers";
    public static String DEFAULTVALUES = "JSONPostProcessor.defaultValues";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;

    @Setter
    private String referenceNames;
    @Setter
    private String jsonPathExpression;
    @Setter
    private String matchNumber;
    @Setter
    private String defaultValues;

    public TestElement build() {

        JSONPostProcessor postProcessor = new JSONPostProcessor();
        postProcessor.setName(name);
        postProcessor.setEnabled(enabled);
        postProcessor.setProperty(JSONPostProcessor.TEST_CLASS, JSONPostProcessor.class.getName());
        postProcessor.setProperty(JSONPostProcessor.GUI_CLASS, JSONPostProcessorGui.class.getName());
        if (comment != null) {
            postProcessor.setComment(comment);
        }
        if (referenceNames != null) {
            postProcessor.setProperty(REFERENCENAMES, referenceNames);
        }
        if (jsonPathExpression != null) {
            postProcessor.setProperty(JSONPATHEXPRESSIONS, jsonPathExpression);
        }
        if (matchNumber != null) {
            postProcessor.setProperty(MATCHNUMBERS, matchNumber);
        }
        if (defaultValues != null) {
            postProcessor.setProperty(DEFAULTVALUES, defaultValues);
        }

        return postProcessor;
    }
}
