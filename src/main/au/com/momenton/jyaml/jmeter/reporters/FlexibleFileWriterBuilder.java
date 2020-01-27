package au.com.momenton.jyaml.jmeter.reporters;

import au.com.momenton.jyaml.jmeter.Builder;
import kg.apc.jmeter.reporters.FlexibleFileWriter;
import kg.apc.jmeter.reporters.FlexibleFileWriterGui;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;

@NoArgsConstructor

public class FlexibleFileWriterBuilder implements Builder {

    public static String FILENAME = "filename";
    public static String HEADER = "header";
    public static String FOOTER = "footer";
    public static String OVERWRITE = "overwrite";
    public static String COLUMNS = "columns";

    @Setter
    private String name;
    @Setter
    private String comment;
    @Setter
    private Boolean enabled = true;
    @Setter
    private String fileName;
    @Setter
    private String header;
    @Setter
    private String footer;
    @Setter
    private String columns;
    @Setter
    private Boolean overwrite;

    public TestElement build() {

        FlexibleFileWriter writer = new FlexibleFileWriter();
        writer.setName(name);
        writer.setEnabled(enabled);
        if (comment != null) {
            writer.setComment(comment);
        }
        writer.setProperty(FlexibleFileWriter.TEST_CLASS, FlexibleFileWriter.class.getName());
        writer.setProperty(FlexibleFileWriter.GUI_CLASS, FlexibleFileWriterGui.class.getName());
        if (columns != null) {
            writer.setColumns(columns);
        }
        if (footer != null) {
            writer.setFileFooter(footer);
        }
        if (header != null) {
            writer.setFileHeader(header);
        }
        if (overwrite != null) {
            writer.setOverwrite(overwrite);
        }
        if (fileName != null) {
            writer.setFilename(fileName);
        }
        return writer;
    }

}
