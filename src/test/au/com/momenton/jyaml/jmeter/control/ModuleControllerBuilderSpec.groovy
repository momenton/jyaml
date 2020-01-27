package au.com.momenton.jyaml.jmeter.control

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.control.ModuleController
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class ModuleControllerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ModuleController:
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
        name  || _
        "mod" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              ModuleController:
                name: ${name}
                enabled: false
                comment: ${comment}
                testPlanName: testpm
                threadGroupName: threadgn
                controllerName: ctl
                parentControllerName: pctl
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ModuleController.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ModuleControllerBuilder.TEST_PLAN_NAME, "testpm"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ModuleControllerBuilder.THREAD_GROUP_NAME, "threadgn"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ModuleControllerBuilder.PARENT_CONTROLLER_NAME, "pctl"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ModuleControllerBuilder.CONTROLLER_NAME, "ctl"))

        where:
        name  | comment     || _
        "mod" | "something" || _

    }
}