package au.com.momenton.jyaml.jmeter.assertions

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.assertions.JSONPathAssertion
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class JSONPathAssertionBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              JSONPathAssertion:
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
              JSONPathAssertion:
                name: ${name}
                enabled: false
                comment: ${comment}
                jsonPath: test
                expectedValue: ABC
                jsonValidation: true 
                expectNull: true 
                invert: true 
                isRegEx: true
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSONPathAssertion.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSONPathAssertion.JSONPATH, "test"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSONPathAssertion.EXPECTEDVALUE, "ABC"))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(JSONPathAssertion.JSONVALIDATION, true))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(JSONPathAssertion.EXPECT_NULL, true))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(JSONPathAssertion.INVERT, true))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(JSONPathAssertion.ISREGEX, true))

        where:
        name     | comment     || _
        "assert" | "something" || _
    }

}