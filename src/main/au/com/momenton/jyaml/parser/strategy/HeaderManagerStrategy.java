package au.com.momenton.jyaml.parser.strategy;

import au.com.momenton.jyaml.jmeter.control.HeaderManagerBuilder;
import au.com.momenton.jyaml.parser.strategy.DefaultStrategy;
import org.apache.jmeter.protocol.http.control.Header;

import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Optional;

public class HeaderManagerStrategy extends DefaultStrategy {

    public HeaderManagerStrategy(String name) {
        super(name);
    }

    @Override
    protected void setChildProperties(LinkedHashMap props) {
        List<Header> headerList = new ArrayList<>();
        List<LinkedHashMap> variables = (List<LinkedHashMap>) props.get("Headers");
        if (variables == null) {
            return;
        }
        variables.forEach(variable -> {
            variable.forEach(
                    (k, v) -> {
                        Header header = new Header();
                        header.setName((String) k);
                        header.setValue(String.valueOf(v));
                        headerList.add(header);
                    }
            );
        });
        ((HeaderManagerBuilder) builder).setHeaders(headerList);
    }
}
