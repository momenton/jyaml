package au.com.momenton.jyaml.jmeter.timer

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.timers.SyncTimer
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class SyncTimerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              SyncTimer:
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
              SyncTimer:
                name: ${name}
                enabled: false
                comment: ${comment}
                groupSize: 123
                timeoutInMs: 1000
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(SyncTimer.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement(SyncTimerBuilder.GROUPSIZE, 123))
        testPlan.contains(JMeterTestUtils.getLongPropertyElement(SyncTimerBuilder.TIMEOUT, 1000))

        where:
        name    | comment     || _
        "timer" | "something" || _
    }

}