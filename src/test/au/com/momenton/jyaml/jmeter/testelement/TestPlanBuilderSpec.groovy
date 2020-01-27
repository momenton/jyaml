package au.com.momenton.jyaml.jmeter.testelement

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.testelement.TestPlan
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class TestPlanBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
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
        "testPlan" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
                name: ${name}
                comment: ${comment}
                functionalMode: false
                tearDownOnShutdown: false
                serialized: false
                enabled: false
                UserVariables:
                - test: 123a 
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(TestPlan.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("Argument.name", "test"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("Argument.value", "123a"))


        where:
        name       | comment     || _
        "testplan" | "something" || _
    }

}