package au.com.momenton.jyaml.jmeter.control;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.control.ThroughputController;
import org.apache.jmeter.control.gui.ThroughputControllerGui;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class ThroughputControllerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String style = Style.PERCENT.getValue();
    @Setter
    private String percentThroughput;
    @Setter
    private String maxThroughput;
    @Setter
    private Boolean perThread;

    public TestElement build() {

        ThroughputController controller = new ThroughputController();
        controller.setName(name);
        if (comment != null) {
            controller.setComment(comment);
        }

        Style styleEnum = Style.fromValue(style);
        if (styleEnum == Style.PERCENT) {
            controller.setStyle(1);
        } else {
            controller.setStyle(0);
        }

        if (percentThroughput != null) {
            controller.setPercentThroughput(percentThroughput);
        }
        if (maxThroughput != null) {
            controller.setMaxThroughput(maxThroughput);
        }
        if (perThread != null) {
            controller.setPerThread(perThread);
        }

        controller.setEnabled(enabled);

        controller.setProperty(ThroughputController.TEST_CLASS, ThroughputController.class.getName());
        controller.setProperty(ThroughputController.GUI_CLASS, ThroughputControllerGui.class.getName());

        return controller;
    }

    @AllArgsConstructor
    public enum Style {
        PERCENT("percent"),
        TOTAL("total");

        @Getter
        private String value;

        public static Style fromValue(String text) {
            for (Style b : Style.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            throw new IllegalArgumentException();
        }
    }

}
