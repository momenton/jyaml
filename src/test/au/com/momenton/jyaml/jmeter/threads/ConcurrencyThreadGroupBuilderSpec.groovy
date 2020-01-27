package au.com.momenton.jyaml.jmeter.threads

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.reporters.ResultCollector
import com.blazemeter.jmeter.threads.concurrency.ConcurrencyThreadGroup;
import org.apache.jorphan.collections.HashTree
import org.apache.jmeter.threads.ThreadGroup
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class ConcurrencyThreadGroupBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ConcurrencyThreadGroup:
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
        name      || _
        "threads" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              ConcurrencyThreadGroup:
                name: ${name}
                enabled: false
                comment: ${comment}
                steps: 1
                units: s
                targetLevel: 5
                hold: 10
                logFilename: test.log
                iterations: 5
                rampUp: 1
                onSampleError: continue
              """


        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ConcurrencyThreadGroup.COMMENTS, comment))

        where:
        name      | comment     || _
        "threads" | "something" || _
    }

}