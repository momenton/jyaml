package au.com.momenton.jyaml.jmeter.extractor;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.extractor.XPathExtractor;
import org.apache.jmeter.extractor.gui.XPathExtractorGui;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class XPathExtractorBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String refName;
    @Setter
    private String defaultValue;
    @Setter
    private String matchNumber;
    @Setter
    private String xpathQuery;

    @Setter
    private Boolean validate;
    @Setter
    private Boolean tolerant;
    @Setter
    private Boolean namespace;

    public TestElement build() {

        XPathExtractor extractor = new XPathExtractor();
        extractor.setName(name);
        extractor.setEnabled(enabled);
        if (comment != null) {
            extractor.setComment(comment);
        }
        if (refName != null) {
            extractor.setRefName(refName);
        }
        extractor.setProperty(XPathExtractor.TEST_CLASS, XPathExtractor.class.getName());
        extractor.setProperty(XPathExtractor.GUI_CLASS, XPathExtractorGui.class.getName());
        if (xpathQuery != null) {
            extractor.setXPathQuery(xpathQuery);
        }
        if (defaultValue != null) {
            extractor.setDefaultValue(defaultValue);
        }
        if (matchNumber != null) {
            extractor.setMatchNumber(matchNumber);
        }
        if (validate != null) {
            extractor.setValidating(validate);
        }
        if (tolerant != null) {
            extractor.setTolerant(tolerant);
        }
        if (namespace != null) {
            extractor.setNameSpace(namespace);
        }

        return extractor;
    }

}
