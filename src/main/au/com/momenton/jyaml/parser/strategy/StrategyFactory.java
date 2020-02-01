package au.com.momenton.jyaml.parser.strategy;


public class StrategyFactory {
    public static Strategy getStrategy(String name) {
        if (name.equals("TestPlan")) {
            return new TestPlanStrategy(name);
        } else if (name.equals("ResponseAssertion")) {
            return new ResponseAssertionStrategy(name);
        } else if (name.equals("UserDefinedVariables")) {
            return new UserDefinedVariablesStrategy(name);
        } else if (name.equals("HeaderManager")) {
            return new HeaderManagerStrategy(name);
        } else if (name.equals("VariableThroughputTimer")) {
            return new VariableThroughputTimerStrategy(name);
        }
        return new DefaultStrategy(name);
    }
}