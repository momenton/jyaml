package au.com.momenton.jyaml;

import com.beust.jcommander.JCommander;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import au.com.momenton.jyaml.parser.YamlParser;

class JmxBuilder {

    static final Logger logger = LogManager.getLogger(JmxBuilder.class.getName());

    public static void main(String[] argv) throws Exception {

        JmxBuilderArgs args = new JmxBuilderArgs();
        JCommander jCommander = new JCommander(args);
        jCommander.setProgramName("JmxBuilder");
        jCommander.parse(argv);
        if (args.help) {
            jCommander.usage();
            System.exit(0);
        }

        JMeterUtils.loadJMeterProperties(args.jmeterProperties);
        JMeterUtils.setJMeterHome(args.jmeterHome);
        JMeterUtils.initLocale();

        String outputFileName = args.output;
        if (outputFileName == null){
            outputFileName = args.input.replace(".yaml","").replace(".yml","")+ ".jmx";
        }
        InputStream inputstream = new FileInputStream(args.input);
        Map<String, Object> yaml = (new Yaml()).load(inputstream);

        HashTree jmxRoot = YamlParser.parse(yaml);

        try {
            SaveService.loadProperties();
            SaveService.saveTree(jmxRoot, new FileOutputStream(outputFileName));
        } catch (IOException ex) {
            logger.error("Failed to build jmx file");
        }
        System.exit(0);
    }

}

