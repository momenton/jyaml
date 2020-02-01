package au.com.momenton.jyaml.jmeter.assertions

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.assertions.ResponseAssertion
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class ResponseAssertionBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ResponseAssertion:
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
              ResponseAssertion:
                name: ${name}
                enabled: false
                comment: ${comment}
                assumeSuccess: true
                useOr: true
                useNot: true 
                matchingRule: ${match}
                fieldTest: ${test}
                testStrings:
                - 200
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ResponseAssertion.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement("Assertion.test_type", matchtype))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("Assertion.test_field", testfield))

        where:
        name     | comment     | match       | test           || matchtype | testfield
        "assert" | "something" | "contains"  | "responsecode" || 38        | "Assertion.response_code"
        "assert" | "something" | "contains"  | "textresponse" || 38        | "Assertion.response_data"
        "assert" | "something" | "matches"   | "textresponse" || 37        | "Assertion.response_data"
        "assert" | "something" | "equals"    | "textresponse" || 44        | "Assertion.response_data"
        "assert" | "something" | "substring" | "textresponse" || 52        | "Assertion.response_data"

    }

    def "invalid test throws exception"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ResponseAssertion:
                name: name
                fieldTest: notreal
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        thrown Exception
    }

    def "invalid match throws exception"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ResponseAssertion:
                name: name
                matchingRule: notreal
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        thrown Exception
    }
}