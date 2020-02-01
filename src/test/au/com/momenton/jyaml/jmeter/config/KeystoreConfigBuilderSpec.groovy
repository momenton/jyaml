package au.com.momenton.jyaml.jmeter.config

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.config.KeystoreConfig
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class KeystoreConfigBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              KeystoreConfig:
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
        "keystore" || _

    }

    def "non-mandatory fields set correctly"() {
        given:
        def yaml = """\
            TestPlan:
              name: test plan
              KeystoreConfig:
                name: ${name}
                enabled: false
                comment: ${comment}
                clientCertAliasVarName: certname
                startIndex: 0
                endIndex: 1 
                preload: abc
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(KeystoreConfig.COMMENTS, comment))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(KeystoreConfigBuilder.CLEINT_CERT_ALIAS_VAR_NAME, "certname"))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement(KeystoreConfigBuilder.START_INDEX, 0))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement(KeystoreConfigBuilder.END_INDEX, 1))

        testPlan.contains(JMeterTestUtils.getStringPropertyElement(KeystoreConfigBuilder.PRELOAD, "abc"))

        where:
        name       | comment     || _
        "keystore" | "something" || _
    }

}