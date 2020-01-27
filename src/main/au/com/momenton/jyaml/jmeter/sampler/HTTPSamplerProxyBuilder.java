package au.com.momenton.jyaml.jmeter.sampler;

import au.com.momenton.jyaml.jmeter.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class HTTPSamplerProxyBuilder implements Builder {

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private Boolean postBodyRaw = true;

    @Setter
    private List<HTTPArgument> parameters = new ArrayList<>();
    @Setter
    private String domain;
    @Setter
    private String port;
    @Setter
    private String protocol = HTTPSamplerProxy.PROTOCOL_HTTPS;
    @Setter
    private String contentEncoding;
    @Setter
    private String path;
    @Setter
    private String method;
    @Setter
    private Boolean followRedirects;
    @Setter
    private Boolean autoRedirects;
    @Setter
    private Boolean useKeepAlive;
    @Setter
    private Boolean doMultiPartPost;
    @Setter
    private String embeddedUrlRe;
    @Setter
    private String connectTimeout;
    @Setter
    private String responseTimeout;
    @Setter
    private String requestBody;
    @Setter
    private String concurrentPool;
    @Setter
    private String proxyHost;
    @Setter
    private String proxyPort;
    @Setter
    private String proxyUser;
    @Setter
    private String proxyPass;
    @Setter
    private String implementation;

    //public HTTPSamplerProxyBuilder addParameter(String name, String value, boolean alwaysEncode, boolean useEquals) {
//
//        HTTPArgument argument = new HTTPArgument(name, value, "=");
//        argument.setAlwaysEncoded(alwaysEncode);
//        argument.setUseEquals(useEquals);
//        this.parameters.add(argument);
//        return this;
//    }

    public TestElement build() {

        HTTPSamplerProxy sampler = new HTTPSamplerProxy();
        sampler.setName(name);
        sampler.setEnabled(enabled);
        if (comment != null) {
            sampler.setComment(comment);
        }
        if (domain != null) {
            sampler.setDomain(domain);
        }

        if (protocol != null) {
            sampler.setProtocol(protocol);
        }
        if (contentEncoding != null) {
            sampler.setContentEncoding(contentEncoding);
        }
        if (path != null) {
            sampler.setPath(path);
        }
        if (method != null) {
            sampler.setMethod(method);
        }
        if (responseTimeout != null) {
            sampler.setResponseTimeout(responseTimeout);
        }
        if (connectTimeout != null) {
            sampler.setConnectTimeout(connectTimeout);
        }
        if (embeddedUrlRe != null) {
            sampler.setEmbeddedUrlRE(embeddedUrlRe);
        }
        if (followRedirects != null) {
            sampler.setFollowRedirects(followRedirects);
        }
        if (autoRedirects != null) {
            sampler.setAutoRedirects(autoRedirects);
        }
        if (useKeepAlive != null) {
            sampler.setUseKeepAlive(useKeepAlive);
        }
        if (doMultiPartPost != null) {
            sampler.setDoMultipartPost(doMultiPartPost);
        }

        Arguments arguments = new Arguments();
        // arguments.setProperty(TestElement.TEST_CLASS, "ArgumentsPanel");
        // arguments.setProperty(TestElement.GUI_CLASS, "Arguments");
        if (requestBody != null) {
            sampler.setPostBodyRaw(postBodyRaw);
            HTTPArgument argument = new HTTPArgument("", requestBody, "=");
            argument.setAlwaysEncoded(false);
            arguments.addArgument(argument);
        } else {
            parameters.forEach(parameter -> arguments.addArgument(parameter));
        }
        if (concurrentPool != null) {
            sampler.setProperty(HTTPSamplerProxy.CONCURRENT_POOL, concurrentPool);
        }
        if (proxyHost != null) {
            sampler.setProperty(HTTPSamplerProxy.PROXYHOST, proxyHost);
        }
        if (proxyPort != null) {
            sampler.setProperty(HTTPSamplerProxy.PROXYPORT, proxyPort);
        }
        if (proxyUser != null) {
            sampler.setProperty(HTTPSamplerProxy.PROXYUSER, proxyUser);
        }
        if (proxyPass != null) {
            sampler.setProperty(HTTPSamplerProxy.PROXYPASS, proxyPass);
        }
        if (implementation != null) {
            sampler.setProperty(HTTPSamplerProxy.IMPLEMENTATION, implementation);
        }
        if (port != null) {
            sampler.setProperty(HTTPSamplerProxy.PORT, port);
        }
        if (arguments != null) {
            sampler.setArguments(arguments);
        }
        sampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        sampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        return sampler;
    }

}
