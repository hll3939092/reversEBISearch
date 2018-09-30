package cn.ncbsp.omicsdi.solr.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlHelper {
    public static <T> T xmlToObject(String xml, T t) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new File(xml));
            return t;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
