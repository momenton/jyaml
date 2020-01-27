package au.com.momenton.jyaml.jmeter.extractor;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.extractor.BoundaryExtractor;
import org.apache.jmeter.extractor.gui.BoundaryExtractorGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.StringProperty;

@NoArgsConstructor
public class BoundaryExtractorBuilder implements Builder {

    public static final String USE_HEADERS = "BoundaryExtractor.useHeaders";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String refName;
    @Setter
    private String leftBoundary;
    @Setter
    private String rightBoundary;
    @Setter
    private String defaultValue;
    @Setter
    private Boolean defaultEmptyValue;
    @Setter
    private String matchNumber;
    @Setter
    private Boolean useHeaders;

    public TestElement build() {

        BoundaryExtractor extractor = new BoundaryExtractor();
        extractor.setName(name);
        extractor.setEnabled(enabled);
        if (comment != null) {
            extractor.setComment(comment);
        }
        if (refName != null) {
            extractor.setRefName(refName);
        }
        extractor.setProperty(BoundaryExtractor.TEST_CLASS, BoundaryExtractor.class.getName());
        extractor.setProperty(BoundaryExtractor.GUI_CLASS, BoundaryExtractorGui.class.getName());
        if (leftBoundary != null) {
            extractor.setLeftBoundary(leftBoundary);
        }
        if (rightBoundary != null) {
            extractor.setRightBoundary(rightBoundary);
        }
        if (defaultEmptyValue != null) {
            extractor.setDefaultEmptyValue(defaultEmptyValue);
        }
        if (defaultValue != null) {
            extractor.setDefaultValue(defaultValue);
        }
        if (matchNumber != null) {
            extractor.setMatchNumber(matchNumber);
        }
        if (useHeaders != null) {
            StringProperty prop = new StringProperty();
            prop.setName(USE_HEADERS);
            prop.setValue(String.valueOf(useHeaders));
            extractor.setProperty(prop);
        }

        return extractor;
    }
}
