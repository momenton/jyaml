package au.com.momenton.jyaml.jmeter.timer

import au.com.momenton.jyaml.JMeterTestUtils
import au.com.momenton.jyaml.parser.YamlParser
import org.apache.jmeter.timers.poissonarrivals.PreciseThroughputTimer
import org.apache.jorphan.collections.HashTree
import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class PreciseThroughputTimerBuilderSpec extends Specification {

    def setup() {
        JMeterTestUtils.init()
    }

    def "mandatory field set correctly"() {
        given:
        String yaml = """\
            TestPlan:
              name: test plan
              PreciseThroughputTimer:
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
              PreciseThroughputTimer:
                name: ${name}
                enabled: false
                comment: ${comment}
                allowedThroughputSurplus: 1.123
                batchSize: 1
                batchThreadDelay: 3 
                duration: 2
                exactLimit: 12
                randomSeed: 123
                throughput: 1000.2
                throughputPeriod: 12
              """

        when:
        Map<String, Object> map = (new Yaml()).load(yaml)
        HashTree jmx = YamlParser.parse(map)
        String testPlan = JMeterTestUtils.save(jmx)
        String testPlanNoWhitespace = testPlan.replaceAll('\\n|\\t|\\s','')

        then:
        testPlan.contains(JMeterTestUtils.getTestNameAttribute(name))
        testPlan.contains(JMeterTestUtils.getEnabledAttribute(false))
        testPlan.contains(JMeterTestUtils.getStringPropertyElement(PreciseThroughputTimer.COMMENTS, comment))
        testPlanNoWhitespace.contains(JMeterTestUtils.getDoublePropertyElement(PreciseThroughputTimerBuilder.ALLOWED_THROUGHPUT_SURPLUS, 1.123))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement(PreciseThroughputTimerBuilder.BATCH_SIZE, 1))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement(PreciseThroughputTimerBuilder.BATCH_THREAD_DELAY, 3))
        testPlan.contains(JMeterTestUtils.getLongPropertyElement(PreciseThroughputTimerBuilder.DURATION, 2))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement(PreciseThroughputTimerBuilder.EXACT_LIMIT, 12))
        testPlan.contains(JMeterTestUtils.getLongPropertyElement(PreciseThroughputTimerBuilder.RANDOM_SEED, 123))
        testPlanNoWhitespace.contains(JMeterTestUtils.getDoublePropertyElement(PreciseThroughputTimerBuilder.THROUGHPUT, 1000.2))
        testPlan.contains(JMeterTestUtils.getIntPropertyElement(PreciseThroughputTimerBuilder.THROUGHPUT_PERIOD, 12))

        where:
        name      | comment     || _
        "sampler" | "something" || _
    }

}