package au.com.momenton.jyaml.parser.strategy;

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.assertions.JSONPathAssertion
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class DefaultStrategySpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "invalid field does not fail"() {
        given:
        String yaml = """\
            TestPlan:
              name: ${name}
              badname: test
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
        "testbad"  || _
    }

}