package au.com.momenton.jyaml.jmeter.sampler

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.protocol.java.sampler.JSR223Sampler
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class JSR223SamplerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              JSR223Sampler:
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
              JSR223Sampler:
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
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223Sampler.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223SamplerBuilder.FILENAME, "test.txt"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223SamplerBuilder.PARAMETERS, "ABC"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223SamplerBuilder.SCRIPT, "func()"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223SamplerBuilder.SCRIPT_LANGUAGE, "groovy"))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(JSR223SamplerBuilder.CACHE_KEY, "key"))

        where:
        name      | comment     || _
        "sampler" | "something" || _
    }

}