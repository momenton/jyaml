package au.com.momenton.jyaml.jmeter.control;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.testelement.TestElement;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class HeaderManagerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private List<Header> headers = new ArrayList<>();

    public TestElement build() {

        HeaderManager headerManager = new HeaderManager();
        headerManager.setProperty(HeaderManager.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(HeaderManager.GUI_CLASS, HeaderPanel.class.getName());
        headerManager.setName(name);
        headerManager.setEnabled(enabled);

        if (comment != null) {
            headerManager.setComment(comment);
        }
        for (Header header : headers) {
            headerManager.add(header);
        }
        return headerManager;
    }
}
