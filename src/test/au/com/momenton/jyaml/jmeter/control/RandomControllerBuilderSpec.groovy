package au.com.momenton.jyaml.jmeter.control

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.control.RandomController
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class RandomControllerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              RandomController:
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
        name     || _
        "random" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              RandomController:
                name: ${name}
                enabled: false
                comment: ${comment}
                ignoreSubControllerBlocks: ${ignoreSubControllerBlocks} 
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(RandomController.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement("InterleaveControl.style", interleaveControl))

        where:
        name     | comment     | ignoreSubControllerBlocks || interleaveControl
        "random" | "something" | true                      || 0
        "random" | "something" | false                     || 1

    }

}