package au.com.momenton.jyaml.jmeter.sampler

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.protocol.java.sampler.BeanShellSampler
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class BeanShellSamplerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              BeanShellSampler:
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
        name      || _
        "sampler" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              BeanShellSampler:
                name: ${name}
                enabled: false
                comment: ${comment}
                fileName: test.txt
                parameters: ABC
                resetInterpreter: true 
                script: func()
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellSampler.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellSampler.FILENAME, "test.txt"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellSampler.PARAMETERS, "ABC"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellSampler.SCRIPT, "func()"))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(BeanShellSampler.RESET_INTERPRETER, true))

        where:
        name      | comment     || _
        "sampler" | "something" || _
    }

}