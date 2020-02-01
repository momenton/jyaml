package au.com.momenton.jyaml.jmeter.extractor

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import au.com.momenton.jyaml.jmeter.extractor.BeanShellPostProcessorBuilder
import org.apache.jmeter.extractor.BeanShellPostProcessor
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class BeanShellPostProcessorBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              BeanShellPostProcessor:
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
        "extractor" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              BeanShellPostProcessor:
                name: ${name}
                enabled: false
                comment: ${comment}
                fileName: test.txt
                parameters: ABC
                resetInterpreter: true
                script: def(){}
              """


        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellPostProcessor.COMMENTS, comment))

        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(BeanShellPostProcessorBuilder.RESET_INTERPRETER, true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellPostProcessorBuilder.PARAMETERS, "ABC"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellPostProcessorBuilder.FILENAME, "test.txt"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BeanShellPostProcessorBuilder.SCRIPT, "def(){}"))


        where:
        name        | comment     || _
        "extractor" | "something" || _
    }

}