package au.com.momenton.jyaml.jmeter.extractor

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.extractor.XPathExtractor
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class XPathExtractorBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              XPathExtractor:
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
              XPathExtractor:
                name: ${name}
                enabled: false
                comment: ${comment}
                refName: "A"
                defaultValue: "B"
                matchNumber: "32"
                xpathQuery: "C"
                validate: true
                tolerant: true
                namespace: true
             """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(XPathExtractor.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("XPathExtractor.refname", "A"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("XPathExtractor.xpathQuery", "C"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("XPathExtractor.matchNumber", "32"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement("XPathExtractor.default", "B"))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement("XPathExtractor.validate", true))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement("XPathExtractor.tolerant", true))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement("XPathExtractor.namespace", true))

        where:
        name        | comment     || _
        "extractor" | "something" || _
    }

}