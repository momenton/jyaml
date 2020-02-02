package au.com.momenton.jyaml.jmeter.assertions

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.assertions.JSR223Assertion
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class JSR223AssertionBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              JSR223Assertion:
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
        "assert" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              JSR223Assertion:
                name: ${name}
                enabled: false
                comment: ${comment}
                fileName: test
                parameters: ABC
                scriptLanguage: groovy 
                cacheKey: true
                script:  def(){}
              """


        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223Assertion.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(JSR223AssertionBuilder.CACHE_KEY, true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223AssertionBuilder.PARAMETERS, "ABC"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223AssertionBuilder.FILENAME, "test"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223AssertionBuilder.SCRIPT, "def(){}"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223AssertionBuilder.SCRIPT_LANGUAGE, "groovy"))

        where:
        name     | comment     || _
        "assert" | "something" || _
    }

}