package au.com.momenton.jyaml.parser;

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.assertions.JSONPathAssertion
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification
import java.lang.IllegalArgumentException
import spock.lang.Ignore
import spock.lang.IgnoreIf
import spock.lang.Requires

class YamlParserSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }


    @Requires({ System.getProperty('OS').contains('windows') })
    def "parse successfully substitutes variables"() {
        given:
        String input = getClass().getResource('/testsubstitution.yaml').getFile()
        String config = getClass().getResource('/config.yaml').getFile()
        input = input.substring(1)
        config = config.substring(1)

        when:
        HashTree jmx = YamlParser.parse(input, config)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute("Test"))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
    }

    def "parse errors for missing input file"() {
        given:
        String input = '/missing.yaml'

        when:
        HashTree jmx = YamlParser.parse(input, null)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == 'Failed to read Input file'
    }

    @Requires({ System.getProperty('OS').contains('windows') })
    def "parse successful for no config file "() {
        given:
        String input = getClass().getResource('/testsimple.yaml').getFile()
        input = input.substring(1)

        when:
        HashTree jmx = YamlParser.parse(input, null)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute("Test"))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(true))
    }

    def "parse errors for missing config file"() {
        String input = getClass().getResource('/testsimple.yaml').getFile()
        String config = '/missing.yaml'
        input = input.substring(1)

        when:
        HashTree jmx = YamlParser.parse(input, config)
        String testPlan = JMeterTestUtils.save(jmx)
        then:
        IllegalArgumentException ex = thrown()
        ex.message == 'Config file does not exist'
    }

}