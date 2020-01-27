package au.com.momenton.jyaml.jmeter.sampler;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.protocol.jdbc.sampler.JDBCSampler;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class JDBCSamplerBuilder implements Builder {

    public static final String DATASOURCE = "dataSource";
    public static final String QUERY = "query";
    public static final String QUERY_ARGUMENTS = "queryArguments";
    public static final String QUERY_ARGUMENTS_TYPES = "queryArgumentsTypes";
    public static final String QUERY_TIMEOUT = "queryTimeout";
    public static final String QUERY_TYPE = "queryType";
    public static final String RESULT_SET_HANDLER = "resultSetHandler";
    public static final String RESULT_VARIABLE = "resultVariable";
    public static final String VARIABLE_NAME = "variableNames";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String dataSource;
    @Setter
    private String query;
    @Setter
    private String queryArgumnents;
    @Setter
    private String queryArgumnentsTypes;
    @Setter
    private String queryTimeout;
    @Setter
    private String queryType;
    @Setter
    private String resultSetHandler;
    @Setter
    private String resultVariable;
    @Setter
    private String variableNames;

    public TestElement build() {

        JDBCSampler sampler = new JDBCSampler();
        sampler.setName(name);
        sampler.setEnabled(enabled);
        sampler.setProperty(JDBCSampler.TEST_CLASS, JDBCSampler.class.getName());
        sampler.setProperty(JDBCSampler.GUI_CLASS, TestBeanGUI.class.getName());
        if (comment != null) {
            sampler.setComment(comment);
        }
        if (dataSource != null) {
            sampler.setProperty(DATASOURCE, dataSource);
        }
        if (query != null) {
            sampler.setProperty(QUERY, query);
        }
        if (queryArgumnents != null) {
            sampler.setProperty(QUERY_ARGUMENTS, queryArgumnents);
        }
        if (queryArgumnentsTypes != null) {
            sampler.setProperty(QUERY_ARGUMENTS_TYPES, queryArgumnentsTypes);
        }
        if (queryTimeout != null) {
            sampler.setProperty(QUERY_TIMEOUT, queryTimeout);
        }
        if (queryType != null) {
            sampler.setProperty(QUERY_TYPE, queryType);
        }
        if (resultSetHandler != null) {
            sampler.setProperty(RESULT_SET_HANDLER, resultSetHandler);
        }
        if (resultVariable != null) {
            sampler.setProperty(RESULT_VARIABLE, resultVariable);
        }
        if (variableNames != null) {
            sampler.setProperty(VARIABLE_NAME, variableNames);
        }

        return sampler;
    }

}
