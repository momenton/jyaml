package au.com.momenton.jyaml.jmeter.sampler

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.YamlParser
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class HTTPSamplerProxyBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              HTTPSamplerProxy:
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
              HTTPSamplerProxy:
                name: ${name}
                enabled: false
                comment: ${comment}
                postBodyRaw: true
                followRedirects: true
                autoRedirects: true
                useKeepAlive: true
                doMultiPartPost: true
                port: 8443
                domain: google.com
                protocol: https
                contentEncoding: json
                path: test
                method: GET
                embeddedUrlRe: a
                connectTimeout: 10
                responseTimeout: 10
                requestBody: body
                concurrentPool: 100
                proxyHost: google.com
                proxyPort: 8443
                proxyUser: dummy
                proxyPass: "****"
                implementation: HttpClient
                parameters:
                - test: 123
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(HTTPSamplerProxy.COMMENTS, comment))

        where:
        name      | comment     || _
        "sampler" | "something" || _
    }

}