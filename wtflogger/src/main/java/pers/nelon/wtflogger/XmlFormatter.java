package pers.nelon.wtflogger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by 李冰锋 on 2017/3/9 14:30.
 * E-mail:libf@ppfuns.com
 * Package: pers.nelon.wtflogger.refactor
 */

public class XmlFormatter {
    private static String INTENT = "\t";

    public static String format(String pXml) {
        String result = "";
        try {
            Document document = DocumentHelper.parseText(pXml);
            OutputFormat prettyPrint = OutputFormat.createPrettyPrint();
            StringWriter stringWriter = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(stringWriter, prettyPrint);
            xmlWriter.write(document);
            xmlWriter.close();

            result = stringWriter.toString();
            result = INTENT + result.replaceAll("\n", "\n" + INTENT);

        } catch (DocumentException | IOException pE) {
            pE.printStackTrace();
        }

        return result;
    }
}
