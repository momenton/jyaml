package au.com.momenton.jyaml.jmeter;

import au.com.momenton.jyaml.jmeter.assertions.JSONPathAssertionBuilder;
import au.com.momenton.jyaml.jmeter.assertions.JSR223AssertionBuilder;
import au.com.momenton.jyaml.jmeter.assertions.ResponseAssertionBuilder;
import au.com.momenton.jyaml.jmeter.config.JDBCDataSourceBuilder;
import au.com.momenton.jyaml.jmeter.config.KeystoreConfigBuilder;
import au.com.momenton.jyaml.jmeter.config.UserDefinedVariablesBuilder;
import au.com.momenton.jyaml.jmeter.control.*;
import au.com.momenton.jyaml.jmeter.extractor.BeanShellPostProcessorBuilder;
import au.com.momenton.jyaml.jmeter.extractor.BoundaryExtractorBuilder;
import au.com.momenton.jyaml.jmeter.extractor.JSONPostProcessorBuilder;
import au.com.momenton.jyaml.jmeter.extractor.XPathExtractorBuilder;
import au.com.momenton.jyaml.jmeter.modifiers.BeanShellPreProcessorBuilder;
import au.com.momenton.jyaml.jmeter.modifiers.CounterConfigBuilder;
import au.com.momenton.jyaml.jmeter.modifiers.JSR223PreProcessorBuilder;
import au.com.momenton.jyaml.jmeter.reporters.AggregrateReportBuilder;
import au.com.momenton.jyaml.jmeter.reporters.FlexibleFileWriterBuilder;
import au.com.momenton.jyaml.jmeter.reporters.SimpleDataWriterBuilder;
import au.com.momenton.jyaml.jmeter.reporters.ViewResultsTreeBuilder;
import au.com.momenton.jyaml.jmeter.sampler.BeanShellSamplerBuilder;
import au.com.momenton.jyaml.jmeter.sampler.HTTPSamplerProxyBuilder;
import au.com.momenton.jyaml.jmeter.sampler.JDBCSamplerBuilder;
import au.com.momenton.jyaml.jmeter.sampler.JSR223SamplerBuilder;
import au.com.momenton.jyaml.jmeter.testelement.TestPlanBuilder;
import au.com.momenton.jyaml.jmeter.threads.ThreadGroupControllerBuilder;
import au.com.momenton.jyaml.jmeter.timer.ConstantThroughputTimerBuilder;
import au.com.momenton.jyaml.jmeter.timer.ConstantTimerBuilder;
import au.com.momenton.jyaml.jmeter.timer.VariableThroughputTimerBuilder;
import au.com.momenton.jyaml.jmeter.visualizers.BackendListenerBuilder;
import au.com.momenton.jyaml.jmeter.threads.ConcurrencyThreadGroupBuilder;

public class BuilderFactory {

    public static Builder getBuilder(String name) {

        if (name.equals("TestPlan")) {
            return new TestPlanBuilder();
        } else if (name.equals("ThreadGroup")) {
            return new ThreadGroupControllerBuilder();
        } else if (name.equals("ConcurrencyThreadGroup")) {
            return new ConcurrencyThreadGroupBuilder();
        } else if (name.equals("LoopController")) {
            return new LoopControllerBuilder();
        } else if (name.equals("ResponseAssertion")) {
            return new ResponseAssertionBuilder();
        } else if (name.equals("JSR223Sampler")) {
            return new JSR223SamplerBuilder();
        } else if (name.equals("JSR223Assertion")) {
            return new JSR223AssertionBuilder();
        } else if (name.equals("JSONPathAssertion")) {
            return new JSONPathAssertionBuilder();
        } else if (name.equals("JSR223Assertion")) {
            return new JSR223AssertionBuilder();
        } else if (name.equals("BeanShellPreProcessor")) {
            return new BeanShellPreProcessorBuilder();
        } else if (name.equals("CounterConfig")) {
            return new CounterConfigBuilder();
        } else if (name.equals("JSR223PreProcessor")) {
            return new JSR223PreProcessorBuilder();
        } else if (name.equals("JDBCDataSource")) {
            return new JDBCDataSourceBuilder();
        } else if (name.equals("KeystoreConfig")) {
            return new KeystoreConfigBuilder();
        } else if (name.equals("UserDefinedVariables")) {
            return new UserDefinedVariablesBuilder();
        } else if (name.equals("HeaderManager")) {
            return new HeaderManagerBuilder();
        } else if (name.equals("IfController")) {
            return new IfControllerBuilder();
        } else if (name.equals("ModuleController")) {
            return new ModuleControllerBuilder();
        } else if (name.equals("RandomController")) {
            return new RandomControllerBuilder();
        } else if (name.equals("ThroughputController")) {
            return new ThroughputControllerBuilder();
        } else if (name.equals("TransactionController")) {
            return new TransactionControllerBuilder();
        } else if (name.equals("BeanShellPostProcessor")) {
            return new BeanShellPostProcessorBuilder();
        } else if (name.equals("BoundaryExtractor")) {
            return new BoundaryExtractorBuilder();
        } else if (name.equals("JSONPostProcessor")) {
            return new JSONPostProcessorBuilder();
        } else if (name.equals("XPathExtractor")) {
            return new XPathExtractorBuilder();
        } else if (name.equals("AggregrateReport")) {
            return new AggregrateReportBuilder();
        } else if (name.equals("ViewResultsTree")) {
            return new ViewResultsTreeBuilder();
        } else if (name.equals("SimpleDataWriter")) {
            return new SimpleDataWriterBuilder();
        } else if (name.equals("FlexibleFileWriter")) {
            return new FlexibleFileWriterBuilder();
        } else if (name.equals("BeanShellSampler")) {
            return new BeanShellSamplerBuilder();
        } else if (name.equals("JSR223Sampler")) {
            return new JSR223SamplerBuilder();
        } else if (name.equals("JDBCSampler")) {
            return new JDBCSamplerBuilder();
        } else if (name.equals("BackendListener")) {
            return new BackendListenerBuilder();
        } else if (name.equals("ConstantTimer")) {
            return new ConstantTimerBuilder();
        } else if (name.equals("ConstantThroughputTimer")) {
            return new ConstantThroughputTimerBuilder();
        } else if (name.equals("VariableThroughputTimer")) {
            return new VariableThroughputTimerBuilder();
        } else if (name.equals("HTTPSamplerProxy")) {
            return new HTTPSamplerProxyBuilder();
        } else if (name.equals("HTTPSamplerProxy")) {
            return new HTTPSamplerProxyBuilder();
        }

        return null;
    }
}