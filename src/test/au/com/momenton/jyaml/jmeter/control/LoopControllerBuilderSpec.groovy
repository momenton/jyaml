package au.com.momenton.jyaml.jmeter.control

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.control.LoopController
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class LoopControllerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              LoopController:
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
        "loop" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              LoopController:
                name: ${name}
                enabled: false
                comment: ${comment}
                loops: ${loops} 
                continueForever: ${continueForever}
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(LoopController.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement("LoopController.loops", numberOfLoops))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement("LoopController.continue_forever", continueForever))

        where:
        name   | comment     | loops | continueForever || numberOfLoops
        "loop" | "something" | 1     | true            || -1
        "loop" | "something" | 0     | false           || 0
        "loop" | "something" | 10    | false           || 10
    }
}