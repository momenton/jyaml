package au.com.momenton.jyaml.jmeter.visualizers;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.apache.jmeter.visualizers.backend.BackendListenerGui;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class BackendListenerBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;

    @Setter
    private List<Argument> arguments = new ArrayList<>();
    @Setter
    private String className;
    @Setter
    private String queueSize;

    public TestElement build() {

        BackendListener listener = new BackendListener();
        listener.setName(name);
        listener.setEnabled(enabled);
        if (comment != null) {
            listener.setComment(comment);
        }
        if (className != null) {
            listener.setClassname(className);
        }
        if (queueSize != null) {
            listener.setQueueSize(queueSize);
        }
        Arguments args = new Arguments();
        arguments.forEach(arg -> args.addArgument(arg));
        listener.setArguments(args);
        listener.setProperty(BackendListener.TEST_CLASS, BackendListener.class.getName());
        listener.setProperty(BackendListener.GUI_CLASS, BackendListenerGui.class.getName());

        return listener;
    }
}
