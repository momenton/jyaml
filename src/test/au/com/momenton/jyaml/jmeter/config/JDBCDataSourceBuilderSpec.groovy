package au.com.momenton.jyaml.jmeter.config

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.protocol.jdbc.config.DataSourceElement
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class JDBCDataSourceBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              JDBCDataSource:
                name: ${name}
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))

        where:
        name       || _
        "keystore" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              JDBCDataSource:
                name: ${name}
                enabled: false
                comment: ${comment}
                autoCommit: true
                checkQuery: SELECT 1
                dbUrl: jdbcdriver
                connectionAge: 12
                password: dummy
                driver: sqlserver
                keepAlive: true
                poolMax: 100
                timeout: 10
                transactionIsolation: default
                trimInterval: 100
                dataSource: data
                userName: test
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(DataSourceElement.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.DATASOURCE, "data"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.DBURL, "jdbcdriver"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.DRIVER, "sqlserver"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.USERNAME, "test"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.PASSWORD, "dummy"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.TRANSACTIONISOLATION, "default"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.TIMEOUT, "10"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.CHECKQUERY, "SELECT 1"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.CONNECTIONAGE, "12"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.POOLMAX, "100"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCDataSourceBuilder.TRIMINTERVAL, "100"))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(JDBCDataSourceBuilder.KEEPALIVE, true))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(JDBCDataSourceBuilder.AUTOCOMMIT, true))

        where:
        name       | comment     || _
        "keystore" | "something" || _
    }

}