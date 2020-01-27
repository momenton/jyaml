package au.com.momenton.jyaml.jmeter.reporters

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.reporters.ResultCollector
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class FlexibleFileWriterBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              FlexibleFileWriter:
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
        name       || _
        "reporter" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              FlexibleFileWriter:
                name: ${name}
                enabled: false
                comment: ${comment}
                fileName: test.txt
                header: A
                footer: B
                columns: C
                overwrite: true
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ResultCollector.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(FlexibleFileWriterBuilder.FILENAME, "test.txt"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(FlexibleFileWriterBuilder.HEADER, "A"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(FlexibleFileWriterBuilder.FOOTER, "B"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(FlexibleFileWriterBuilder.COLUMNS, "C"))
        testPlan.contains(JMeterTestUtils.getBooleanPropertyElement(FlexibleFileWriterBuilder.OVERWRITE, true))


        where:
        name       | comment     || _
        "reporter" | "something" || _
    }

}