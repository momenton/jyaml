package au.com.momenton.jyaml.jmeter.timer

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.timers.JSR223Timer
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class JSR223TimerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              JSR223Timer:
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
        "sampler" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              JSR223Timer:
                name: ${name}
                enabled: false
                comment: ${comment}
                fileName: test.txt
                parameters: ABC
                scriptLanguage: groovy 
                script: func()
                cacheKey: key
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223Timer.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223TimerBuilder.FILENAME, "test.txt"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223TimerBuilder.PARAMETERS, "ABC"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223TimerBuilder.SCRIPT, "func()"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223TimerBuilder.SCRIPT_LANGUAGE, "groovy"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223TimerBuilder.CACHE_KEY, "key"))

        where:
        name      | comment     || _
        "sampler" | "something" || _
    }

}