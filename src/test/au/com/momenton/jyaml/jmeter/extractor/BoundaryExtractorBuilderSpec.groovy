package au.com.momenton.jyaml.jmeter.extractor

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import au.com.momenton.jyaml.jmeter.extractor.BoundaryExtractorBuilder
import org.apache.jmeter.extractor.BoundaryExtractor
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class BoundaryExtractorBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              BoundaryExtractor:
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
              BoundaryExtractor:
                name: ${name}
                enabled: false
                comment: ${comment}
                refName: A
                leftBoundary: "["
                rightBoundary: "]"
                defaultValue: B
                defaultEmptyValue: true
                matchNumber: 32
                useHeaders: true
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BoundaryExtractor.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(BoundaryExtractorBuilder.USE_HEADERS, "true"))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement("BoundaryExtractor.default_empty_value", true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("BoundaryExtractor.refname", "A"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("BoundaryExtractor.lboundary", "["))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("BoundaryExtractor.rboundary", "]"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("BoundaryExtractor.default", "B"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("BoundaryExtractor.match_number", "32"))

        where:
        name        | comment     || _
        "extractor" | "something" || _
    }

}