package au.com.momenton.jyaml.jmeter.assertions;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.AssertionGui;
import org.apache.jmeter.testelement.TestElement;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ResponseAssertionBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;

    @Setter
    private List<String> testStrings = new ArrayList<>();
    @Setter
    private Boolean assumeSuccess = false;
    @Setter
    private Boolean useOr = false;
    @Setter
    private Boolean useNot = false;
    @Setter
    private String matchingRule = MatchingRule.CONTAINS.getValue();
    @Setter
    private String fieldTest = FieldTest.TEXTRESPONSE.getValue();

    public TestElement build() {

        ResponseAssertion assertion = new ResponseAssertion();
        assertion.setName(name);
        assertion.setEnabled(enabled);
        if (comment != null) {
            assertion.setComment(comment);
        }

        assertion.setProperty(ResponseAssertion.TEST_CLASS, ResponseAssertion.class.getName());
        assertion.setProperty(ResponseAssertion.GUI_CLASS, AssertionGui.class.getName());
        for (String testString : testStrings) {
            assertion.addTestString(testString);
        }
        if (useOr) {
            assertion.setToOrType();
        }
        if (useNot) {
            assertion.setToNotType();
        }

        MatchingRule rule = MatchingRule.fromValue(matchingRule);
        if (rule == MatchingRule.CONTAINS) {
            assertion.setToContainsType();
        } else if (rule == MatchingRule.MATCHES) {
            assertion.setToMatchType();
        } else if (rule == MatchingRule.EQUALS) {
            assertion.setToEqualsType();
        } else {
            assertion.setToSubstringType();
        }

        FieldTest test = FieldTest.fromValue(fieldTest);
        if (test == FieldTest.TEXTRESPONSE) {
            assertion.setTestFieldResponseData();
        } else {
            assertion.setTestFieldResponseCode();
        }
        assertion.setAssumeSuccess(assumeSuccess);

        return assertion;

    }

    @AllArgsConstructor
    public enum MatchingRule {
        CONTAINS("contains"),
        MATCHES("matches"),
        EQUALS("equals"),
        SUBSTRING("substring");
        @Getter
        private String value;

        public static MatchingRule fromValue(String text) {
            for (MatchingRule b : MatchingRule.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    @AllArgsConstructor
    public enum FieldTest {
        TEXTRESPONSE("textresponse"),
        RESPONSECODE("responsecode");
        @Getter
        private String value;

        public static FieldTest fromValue(String text) {

            for (FieldTest b : FieldTest.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            throw new IllegalArgumentException();
        }
    }

}
