package au.com.momenton.jyaml.jmeter.modifiers

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.modifiers.BeanShellPreProcessor
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class BeanShellPreProcessorBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              BeanShellPreProcessor:
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
              BeanShellPreProcessor:
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
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellPreProcessor.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellPreProcessorBuilder.FILENAME, "test.txt"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellPreProcessorBuilder.PARAMETERS, "ABC"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellPreProcessorBuilder.SCRIPT, "func()"))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(BeanShellPreProcessorBuilder.RESET_INTERPRETER, true))

        where:
        name        | comment     || _
        "beanshell" | "something" || _
    }

}