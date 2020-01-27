package au.com.momenton.jyaml.jmeter.sampler

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.protocol.jdbc.sampler.JDBCSampler
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class JDBCSamplerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              JDBCSampler:
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
        name      || _
        "sampler" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              JDBCSampler:
                name: ${name}
                enabled: false
                comment: ${comment}
                dataSource: A
                query: SELECT 1 
                queryArgumnents: B
                queryArgumnentsTypes: C
                queryTimeout: 0
                queryType: SELECT
                resultSetHandler: D
                resultVariable: E
                variableNames: F
              """


        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSampler.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.DATASOURCE, "A"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.QUERY, "SELECT 1"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.QUERY_ARGUMENTS, "B"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.QUERY_ARGUMENTS_TYPES, "C"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.QUERY_TIMEOUT, "0"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.QUERY_TYPE, "SELECT"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.RESULT_SET_HANDLER, "D"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.RESULT_VARIABLE, "E"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JDBCSamplerBuilder.VARIABLE_NAME, "F"))


        where:
        name      | comment     || _
        "sampler" | "something" || _
    }

}