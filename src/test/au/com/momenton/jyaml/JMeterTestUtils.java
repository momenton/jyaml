package au.com.momenton.jyaml;

import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JMeterTestUtils {

    static final Logger logger = LogManager.getLogger(JMeterTestUtils.class.getName());

    public static void init() throws Exception {

        String jmeterhome = "./apache-jmeter-5.2.1";
        String jmeterproperties = "./apache-jmeter-5.2.1/bin/jmeter.properties";

        // Setup JMeter
        JMeterUtils.loadJMeterProperties(jmeterproperties);
        JMeterUtils.setJMeterHome(jmeterhome);

        SaveService.loadProperties();
    }


    public static String getTestNameAttribute(String name) {
        return "testname=\"" + name + "\"";
    }

    public static String getEnabledAttribute(boolean name) {
        return "enabled=\"" + name + "\"";
    }


    public static String getLongPropertyElement(String name, long value) {
        return "<longProp name=\"" + name + "\">" + value + "</longProp>";
    }

    public static String getIntPropertyElement(String name, int value) {
        return "<intProp name=\"" + name + "\">" + value + "</intProp>";
    }


    public static String getStringPropertyElement(String name, String value) {
        return "<stringProp name=\"" + name + "\">" + value + "</stringProp>";
    }

    public static String getBooleanPropertyElement(String name, boolean value) {
        return "<boolProp name=\"" + name + "\">" + value + "</boolProp>";
    }

    public static String getFloatPropertyElement(String name, float value) {
        return " <FloatProperty><name>" + name + "</name><value>" + value + "</value>";
    }

    public static String save(TestElement element) {

        String test = null;
        HashTree tree = new ListedHashTree();
        tree.add(element);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            SaveService.saveTree(tree, os);
            test = new String(os.toByteArray());
            logger.debug(test);
        } catch (IOException ex) {
            //ignore
        }
        return test;
    }

    public static String save(HashTree tree) {

        String test = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            SaveService.saveTree(tree, os);
            test = new String(os.toByteArray());
            logger.debug(test);
        } catch (IOException ex) {
            //ignore
        }
        return test;
    }


}
