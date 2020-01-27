package au.com.momenton.jyaml.jmeter.config;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.config.KeystoreConfig;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class KeystoreConfigBuilder implements Builder {

    public static final String CLEINT_CERT_ALIAS_VAR_NAME = "clientCertAliasVarName";
    public static final String START_INDEX = "startIndex";
    public static final String END_INDEX = "endIndex";
    public static final String PRELOAD = "preload";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;

    @Setter
    private String clientCertAliasVarName;
    @Setter
    private Integer endIndex;
    @Setter
    private Integer startIndex;
    @Setter
    private String preload;

    public TestElement build() {

        KeystoreConfig keystoreConfig = new KeystoreConfig();
        keystoreConfig.setProperty(KeystoreConfig.TEST_CLASS, KeystoreConfig.class.getName());
        keystoreConfig.setProperty(KeystoreConfig.GUI_CLASS, TestBeanGUI.class.getName());
        keystoreConfig.setName(name);
        keystoreConfig.setEnabled(enabled);
        if (comment != null) {
            keystoreConfig.setComment(comment);
        }
        if (clientCertAliasVarName != null) {
            keystoreConfig.setProperty(CLEINT_CERT_ALIAS_VAR_NAME, clientCertAliasVarName);
        }
        if (startIndex != null) {
            keystoreConfig.setProperty(START_INDEX, startIndex);
        }
        if (endIndex != null) {
            keystoreConfig.setProperty(END_INDEX, endIndex);
        }
        if (preload != null) {
            keystoreConfig.setProperty(PRELOAD, preload);
        }

        return keystoreConfig;

    }

}
