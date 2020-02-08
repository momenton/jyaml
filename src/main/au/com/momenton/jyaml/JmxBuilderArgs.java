package au.com.momenton.jyaml;

import com.beust.jcommander.Parameter;

/**
 * Command line arguments
 */
public class JmxBuilderArgs {

    /**
     * Input yaml
     */
    @Parameter(names = {"-input", "--i"}, description = "Input yaml")
    public String input;

    /**
     * Config yaml
     */
    @Parameter(names = {"-config", "--c"}, description = "Config yaml")
    public String config;

    /**
     * Output file name
     */
    @Parameter(names = {"-output", "--o"}, description = "Output script")
    public String output;

    /**
     * Home directory for jmeter
     */
    @Parameter(names = {"-jmeterhome", "--j"}, description = "Jmeter home directory")
    public String jmeterHome = "./apache-jmeter-5.2.1";

    /**
     * Properties files for jmeter
     */
    @Parameter(names = {"-jmeterproperties", "--p"}, description = "Jmeter properties file")
    public String jmeterProperties = "./apache-jmeter-5.2.1/bin/jmeter.properties";

    /**
     * Show help usage
     */
    @Parameter(names = {"-help", "--h"}, description = "Display command line options", help = true)
    public boolean help;

    /**
     * ctor
     */
    public JmxBuilderArgs() {

    }
}