package au.com.momenton.jyaml;


import au.com.momenton.jyaml.jmeter.Builder;
import au.com.momenton.jyaml.jmeter.BuilderFactory;
import au.com.momenton.jyaml.jmeter.assertions.ResponseAssertionBuilder;
import au.com.momenton.jyaml.jmeter.config.UserDefinedVariablesBuilder;
import au.com.momenton.jyaml.jmeter.control.HeaderManagerBuilder;
import au.com.momenton.jyaml.jmeter.testelement.TestPlanBuilder;
import au.com.momenton.jyaml.jmeter.timer.VariableThroughputTimerBuilder;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class YamlParser {

    public static HashTree parse(Map<String, Object> yaml) {
        HashTree jmxRoot = new ListedHashTree();
        parse("TestPlan", yaml.get("TestPlan"), new HashMap<>(), jmxRoot);
        return jmxRoot;
    }

    @SuppressWarnings("rawtypes")
    private static void parse(String key, Object value, HashMap<String, Object> props, HashTree tree) {

        HashTree childTree = new ListedHashTree();
        HashMap<String, Object> childProps = new HashMap<>();

        if (value instanceof String) {
            props.put(key, value);
        } else if (value instanceof Integer) {
            props.put(key, value);
        } else if (value instanceof Boolean) {
            props.put(key, value);
        } else if (value instanceof LinkedHashMap) {
            LinkedHashMap map = (LinkedHashMap) value;
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String k = (String) it.next();
                Object v = map.get(k);
                if (key.equals("TestPlan") && k.equals("UserVariables")) {
                    System.out.println("skipping");
                } else {
                    parse(k, v, childProps, childTree);
                }
            }

            TestElement element = null;
            Builder builder = BuilderFactory.getBuilder(key);
            if (builder != null) {
                setBuilderProperties(builder, childProps);
                if (key.equals("TestPlan")) {
                    //get test strings
                    List<Argument> argumentList = new ArrayList<>();
                    List<LinkedHashMap> variables = (List<LinkedHashMap>) map.get("UserVariables");
                    if (variables != null) {
                        for (LinkedHashMap variable : variables) {
                            Iterator it2 = variable.keySet().iterator();
                            while (it2.hasNext()) {
                                String k2 = (String) it2.next();
                                String v2 = String.valueOf(variable.get(k2));
                                Argument arg = new Argument();
                                arg.setName(k2);
                                arg.setValue(v2);
                                argumentList.add(arg);
                            }
                        }
                        ((TestPlanBuilder) builder).setUserVariables(argumentList);
                    }
                } else if (key.equals("VariableThroughputTimer")) {
                    List<Argument> argumentList = new ArrayList<>();
                    List<LinkedHashMap> schedules = (List<LinkedHashMap>) map.get("Schedule");
                    if (schedules != null) {
                        for (LinkedHashMap schedule : schedules) {
                            String start = String.valueOf(schedule.get("start"));
                            String end = String.valueOf(schedule.get("end"));
                            String duration = String.valueOf(schedule.get("duration"));
                            ((VariableThroughputTimerBuilder) builder).addSchedule(start, end, duration);
                        }
                    }
                } else if (key.equals("UserDefinedVariables")) {
                    //get test strings
                    List<Argument> argumentList = new ArrayList<>();
                    List<LinkedHashMap> variables = (List<LinkedHashMap>) map.get("UserVariables");
                    if (variables != null) {
                        for (LinkedHashMap variable : variables) {
                            Iterator it2 = variable.keySet().iterator();
                            while (it2.hasNext()) {
                                String k2 = (String) it2.next();
                                String v2 = String.valueOf(variable.get(k2));
                                Argument arg = new Argument();
                                arg.setName(k2);
                                arg.setValue(v2);
                                argumentList.add(arg);
                            }
                        }
                        ((UserDefinedVariablesBuilder) builder).setUserVariables(argumentList);
                    }
                } else if (key.equals("HeaderManager")) {
                    //get test strings
                    List<Header> headerList = new ArrayList<>();
                    List<LinkedHashMap> variables = (List<LinkedHashMap>) map.get("Headers");
                    if (variables != null) {
                        for (LinkedHashMap variable : variables) {
                            Iterator it2 = variable.keySet().iterator();
                            while (it2.hasNext()) {
                                String k2 = (String) it2.next();
                                String v2 = String.valueOf(variable.get(k2));
                                Header header = new Header();
                                header.setName(k2);
                                header.setValue(v2);
                                headerList.add(header);
                            }
                        }
                        ((HeaderManagerBuilder) builder).setHeaders(headerList);
                    }

                } else if (key.equals("ResponseAssertion")) {
                    //get test strings
                    ArrayList testMap = (ArrayList) map.get("testStrings");
                    if (testMap != null) {
                        List<String> testStrings = (List<String>) testMap
                                .stream()
                                .map(Object::toString)
                                .collect(Collectors.toList());

                        ((ResponseAssertionBuilder) builder).setTestStrings(testStrings);
                    }
                }
                element = builder.build();
                if (element != null) {
                    tree = tree.add(element);
                }
            }
        } else if (value instanceof ArrayList) {
            ArrayList list = (ArrayList) value;
            for (Object itm : list) {
                parse(key, itm, childProps, childTree);
            }
        }

        if (childTree.size() > 0) {
            tree.add(childTree);

        }
        if (childProps.size() > 0) {
            props.put(key, childProps);
        }

    }

    private static void setBuilderProperties(Object builder, HashMap<String, Object> props) {

        props.forEach((key, val) -> {
            try {
                if (val instanceof Integer) {
                    try {
                        //Try integer first
                        Method method = builder.getClass().getMethod(getSetterName(key), val.getClass());
                        method.invoke(builder, val);
                    } catch (Exception ex) {
                        //Try string next
                        String strVal = String.valueOf(val);
                        Method method = builder.getClass().getMethod(getSetterName(key), strVal.getClass());
                        method.invoke(builder, strVal);
                    }
                } else {
                    Method method = builder.getClass().getMethod(getSetterName(key), val.getClass());
                    method.invoke(builder, val);
                }
            } catch (NoSuchMethodException nsm) {
                System.out.println("No such method " + nsm.getMessage());
            } catch (IllegalAccessException | InvocationTargetException iae) {
                System.out.println("Access issue " + iae.getMessage());
            }
        });
    }

    private static String getSetterName(String input) {

        char c = input.charAt(0);
        String s = new String("" + c);
        return "set" + s.toUpperCase() + input.substring(1);
    }
}
