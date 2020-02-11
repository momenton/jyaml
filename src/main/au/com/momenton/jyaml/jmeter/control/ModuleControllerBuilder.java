package au.com.momenton.jyaml.jmeter.control;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.control.ModuleController;
import org.apache.jmeter.control.gui.ModuleControllerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.StringProperty;

@NoArgsConstructor
public class ModuleControllerBuilder implements Builder {

    // next section is dodgy and needs to br fix, work around for running in headless mode
    public final static String TEST_PLAN_NAME = "764597751";
    public final static String THREAD_GROUP_NAME = "-1504026899";
    public final static String PARENT_CONTROLLER_NAME = "-2099909926";
    public final static String CONTROLLER_NAME = "-1187124224";
    public final static String NODE_PATH = "ModuleController.node_path";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String testPlanName;
    @Setter
    private String threadGroupName;
    @Setter
    private String controllerName;
    @Setter
    private String parentControllerName;

    public TestElement build() {

        ModuleController moduleController = new ModuleController();

        moduleController.setProperty(ModuleController.TEST_CLASS, ModuleController.class.getName());
        moduleController.setProperty(ModuleController.GUI_CLASS, ModuleControllerGui.class.getName());
        moduleController.setName(name);
        moduleController.setEnabled(enabled);
        if (comment != null) {
            moduleController.setComment(comment);
        }
        CollectionProperty collectionProp = new CollectionProperty();
        StringProperty prop = new StringProperty();
        if (testPlanName != null) {
            prop.setValue(testPlanName);
            prop.setName(TEST_PLAN_NAME);
            collectionProp.addProperty(prop);
        }
        if (threadGroupName != null) {
            prop = new StringProperty();
            prop.setValue(threadGroupName);
            prop.setName(THREAD_GROUP_NAME);
            collectionProp.addProperty(prop);
        }
        if (parentControllerName != null) {
            prop = new StringProperty();
            prop.setValue(parentControllerName);
            prop.setName(PARENT_CONTROLLER_NAME);
            collectionProp.addProperty(prop);
        }
        if (controllerName != null) {
            prop = new StringProperty();
            prop.setValue(controllerName);
            prop.setName(CONTROLLER_NAME);
            collectionProp.addProperty(prop);
        }
        collectionProp.setName(NODE_PATH);
        moduleController.setProperty(collectionProp);

        return moduleController;
    }
}
