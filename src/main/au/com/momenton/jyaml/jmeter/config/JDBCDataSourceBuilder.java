package au.com.momenton.jyaml.jmeter.config;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.protocol.jdbc.config.DataSourceElement;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor
public class JDBCDataSourceBuilder implements Builder {

    public static final String DATASOURCE = "dataSource";
    public static final String DBURL = "dbUrl";
    public static final String DRIVER = "driver";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TRANSACTIONISOLATION = "transactionIsolation";
    public static final String TIMEOUT = "timeout";
    public static final String AUTOCOMMIT = "autocommit";
    public static final String CHECKQUERY = "checkQuery";
    public static final String CONNECTIONAGE = "connectionAge";
    public static final String KEEPALIVE = "keepAlive";
    public static final String POOLMAX = "poolMax";
    public static final String TRIMINTERVAL = "trimInterval";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;

    @Setter
    private String userName;
    @Setter
    private Boolean autoCommit;
    @Setter
    private String checkQuery;
    @Setter
    private String dbUrl;
    @Setter
    private String connectionAge;
    @Setter
    private String password;
    @Setter
    private String driver;
    @Setter
    private Boolean keepAlive;
    @Setter
    private String poolMax;
    @Setter
    private String timeout;
    @Setter
    private String transactionIsolation;
    @Setter
    private String trimInterval;
    @Setter
    private String dataSource;

    public TestElement build() {

        DataSourceElement dataSourceElement = new DataSourceElement();
        dataSourceElement.setProperty(DataSourceElement.TEST_CLASS, DataSourceElement.class.getName());
        dataSourceElement.setProperty(DataSourceElement.GUI_CLASS, TestBeanGUI.class.getName());
        if (dataSource != null) {
            dataSourceElement.setDataSource(dataSource);
        }
        dataSourceElement.setName(name);
        dataSourceElement.setEnabled(enabled);
        if (comment != null) {
            dataSourceElement.setComment(comment);
        }
        if (dataSource != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.DATASOURCE, dataSource);
        }
        if (dbUrl != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.DBURL, dbUrl);
        }
        if (driver != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.DRIVER, driver);
        }
        if (userName != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.USERNAME, userName);
        }
        if (password != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.PASSWORD, password);
        }
        if (transactionIsolation != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.TRANSACTIONISOLATION, transactionIsolation);
        }
        if (timeout != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.TIMEOUT, timeout);
        }
        if (autoCommit != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.AUTOCOMMIT, autoCommit);
        }
        if (checkQuery != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.CHECKQUERY, checkQuery);
        }
        if (connectionAge != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.CONNECTIONAGE, connectionAge);
        }
        if (keepAlive != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.KEEPALIVE, keepAlive);
        }
        if (poolMax != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.POOLMAX, poolMax);
        }
        if (trimInterval != null) {
            dataSourceElement.setProperty(JDBCDataSourceBuilder.TRIMINTERVAL, trimInterval);
        }

        return dataSourceElement;

    }

}
