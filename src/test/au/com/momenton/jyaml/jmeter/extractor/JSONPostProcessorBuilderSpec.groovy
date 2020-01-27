package au.com.momenton.jyaml.jmeter.extractor

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import au.com.momenton.jyaml.jmeter.extractor.JSONPostProcessorBuilder
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class JSONPostProcessorBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              JSONPostProcessor:
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
              JSONPostProcessor:
                name: ${name}
                enabled: false
                comment: ${comment}
                referenceNames: "A"
                jsonPathExpression: "B"
                matchNumber: "32"
                defaultValues: "C"
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSONPostProcessor.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSONPostProcessorBuilder.REFERENCENAMES, "A"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSONPostProcessorBuilder.JSONPATHEXPRESSIONS, "B"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSONPostProcessorBuilder.MATCHNUMBERS, "32"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSONPostProcessorBuilder.DEFAULTVALUES, "C"))

        where:
        name        | comment     || _
        "extractor" | "something" || _
    }

}