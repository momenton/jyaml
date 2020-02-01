package au.com.momenton.jyaml.jmeter.modifiers

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.modifiers.JSR223PreProcessor
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class JSR223PreProcessorBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              JSR223PreProcessor:
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
        "jsr223" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              JSR223PreProcessor:
                name: ${name}
                enabled: false
                comment: ${comment}
                fileName: test.txt
                parameters: ABC
                cacheKey: D 
                script: func()
                scriptLanguage: groovy
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223PreProcessor.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223PreProcessorBuilder.FILENAME, "test.txt"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223PreProcessorBuilder.PARAMETERS, "ABC"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223PreProcessorBuilder.SCRIPT, "func()"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223PreProcessorBuilder.SCRIPT_LANGUAGE, "groovy"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223PreProcessorBuilder.CACHE_KEY, "D"))

        where:
        name     | comment     || _
        "jsr223" | "something" || _
    }

}