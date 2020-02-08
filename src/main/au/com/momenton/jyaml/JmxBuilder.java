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
import java.util.HashMap;
import com.hubspot.jinjava.Jinjava;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import au.com.momenton.jyaml.parser.YamlParser;
import com.hubspot.jinjava.loader.FileLocator;

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

        Map<String, Object> context = new HashMap<>();
        if (args.config != null){
            try(FileInputStream config = new FileInputStream(args.config)) {
                context = (new Yaml()).load(config);
            } catch(Exception ex){
                logger.error("Config file does not exist");
                System.exit(1);
            }
        }

        Path path = FileSystems.getDefault().getPath(args.input);
        if (!Files.exists(path)) {
            logger.error("Input file does not exist");
            System.exit(1);
        }

        String input = new String(Files.readAllBytes(path));
        Jinjava jinjava = new Jinjava();
        jinjava.setResourceLocator(new FileLocator());
        String inputPreProcessed = jinjava.render(input,context);

        Map<String, Object> yaml = (new Yaml()).load(inputPreProcessed);
        HashTree jmxRoot = YamlParser.parse(yaml);

        try {
            SaveService.loadProperties();
            SaveService.saveTree(jmxRoot, new FileOutputStream(outputFileName));
        } catch (IOException ex) {
            logger.error("Failed to build jmx file");
            System.exit(1);
        }
        System.exit(0);
    }

}

