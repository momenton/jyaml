package au.com.momenton.jyaml.jmeter.assertions;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.assertions.JSONPathAssertion;
import org.apache.jmeter.assertions.gui.JSONPathAssertionGui;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class JSONPathAssertionBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String jsonPath;
    @Setter
    private String expectedValue;
    @Setter
    private Boolean jsonValidation;
    @Setter
    private Boolean expectNull;
    @Setter
    private Boolean invert;
    @Setter
    private Boolean isRegEx;

    public TestElement build() {

        JSONPathAssertion assertion = new JSONPathAssertion();
        assertion.setName(name);
        assertion.setEnabled(enabled);
        assertion.setProperty(JSONPathAssertion.TEST_CLASS, JSONPathAssertion.class.getName());
        assertion.setProperty(JSONPathAssertion.GUI_CLASS, JSONPathAssertionGui.class.getName());
        if (comment != null) {
            assertion.setComment(comment);
        }
        if (jsonPath != null) {
            assertion.setProperty(JSONPathAssertion.JSONPATH, jsonPath);
        }
        if (expectedValue != null) {
            assertion.setProperty(JSONPathAssertion.EXPECTEDVALUE, expectedValue);
        }
        if (jsonValidation != null) {
            assertion.setJsonValidationBool(jsonValidation);
        }
        if (expectNull != null) {
            assertion.setExpectNull(expectNull);
        }
        if (invert != null) {
            assertion.setInvert(invert);
        }
        if (isRegEx != null) {
            assertion.setProperty(JSONPathAssertion.ISREGEX, isRegEx);
        }
        return assertion;
    }

}
