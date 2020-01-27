package au.com.momenton.jyaml.jmeter.threads

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.reporters.ResultCollector
import org.apache.jorphan.collections.HashTree
import org.apache.jmeter.threads.ThreadGroup
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class ThreadGroupControllerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              ThreadGroup:
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
              ThreadGroup:
                name: ${name}
                enabled: false
                comment: ${comment}
                numThreads: 1
                rampUp: 1
                duration: 10
                delay: 10
                loops: 1
                continueForever: false
                delayedStart: true
                scheduler: true
                onSampleError: continue
              """


        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(ThreadGroup.COMMENTS, comment))

        where:
        name      | comment     || _
        "threads" | "something" || _
    }

}