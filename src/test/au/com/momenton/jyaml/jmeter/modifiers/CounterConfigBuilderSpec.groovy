package au.com.momenton.jyaml.jmeter.modifiers

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.modifiers.CounterConfig
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class CounterConfigBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              CounterConfig:
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
        name        || _
        "beanshell" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              CounterConfig:
                name: ${name}
                enabled: false
                comment: ${comment}
                start: 0
                end: 1
                incr: 1
                format: "##"
                referenceName: A
                perUser: true
                resetOnThreadGroupIteration: true
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(CounterConfig.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(CounterConfigBuilder.REFERENCE_NAME, "A"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("CounterConfig.start", "0"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("CounterConfig.end", "1"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("CounterConfig.incr", "1"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("CounterConfig.format", "##"))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(CounterConfigBuilder.PER_USER, true))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(CounterConfigBuilder.RESET_ON_THREADGROUP_ITERTAION, true))

        where:
        name        | comment     || _
        "beanshell" | "something" || _
    }

}