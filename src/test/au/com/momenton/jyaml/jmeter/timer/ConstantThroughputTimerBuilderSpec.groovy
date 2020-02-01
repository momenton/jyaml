package au.com.momenton.jyaml.jmeter.timer

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.timers.ConstantThroughputTimer
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class ConstantThroughputTimerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ConstantThroughputTimer:
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
        name    || _
        "timer" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              ConstantThroughputTimer:
                name: ${name}
                enabled: false
                comment: ${comment}
                throughput: 12
                calcMode: 1
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ConstantThroughputTimer.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement(ConstantThroughputTimerBuilder.CALC_MODE, 1))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ConstantThroughputTimerBuilder.THROUGHPUT, "12"))

        where:
        name    | comment     || _
        "timer" | "something" || _
    }

}