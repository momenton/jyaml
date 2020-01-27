package au.com.momenton.jyaml.jmeter.control

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.control.ThroughputController
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class ThroughputControllerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ThroughputController:
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
        name   || _
        "thru" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              ThroughputController:
                name: ${name}
                enabled: false
                comment: ${comment}
                style: ${style}
                percentThroughput: 10
                maxThroughput: 40
                perThread: true
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ThroughputController.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement("ThroughputController.perThread", true))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement("ThroughputController.style", styleComputed))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("ThroughputController.maxThroughput", "40"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("ThroughputController.percentThroughput", "10"))

        where:
        name   | comment     | style     || styleComputed
        "thru" | "something" | "percent" || 1
        "thru" | "something" | "total"   || 0
    }

    def "invalid style throws exception"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ThroughputController:
                name: name
                style: notreal
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        thrown Exception
    }
}