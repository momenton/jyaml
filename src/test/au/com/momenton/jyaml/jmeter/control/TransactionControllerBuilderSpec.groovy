package au.com.momenton.jyaml.jmeter.control

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.control.TransactionController
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class TransactionControllerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              TransactionController:
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
        "trans" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              TransactionController:
                name: ${name}
                enabled: false
                comment: ${comment}
                includeTimers: true
                generateParentSample: true
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(TransactionController.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(TransactionControllerBuilder.INCLUDE_TIMERS, true))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement("TransactionController.parent", true))

        where:
        name    | comment     || _
        "trans" | "something" || _

    }
}