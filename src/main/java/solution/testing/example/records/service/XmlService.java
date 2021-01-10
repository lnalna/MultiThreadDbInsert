package solution.testing.example.records.service;

import solution.testing.example.records.exception.ServiceException;
import solution.testing.example.records.pojo.Entries;
import solution.testing.example.records.pojo.Entry;
import solution.testing.example.records.pojo.ResultEntries;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class XmlService {
    public static final String WORK_DIRECTORY = System.getProperty("user.home") + File.separator + "test-task";
    private static final String FIRST_XML_FILE = WORK_DIRECTORY + File.separator + "1.xml";
    private static final String TRANSFER_XSLT_FILE = WORK_DIRECTORY + File.separator + "transfer.xlst";
    private static final String SECOND_XML_FILE = WORK_DIRECTORY + File.separator + "2.xml";
    private static final String TEMPLATE_XSLT =
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
            "<xsl:template match=\"entries\">" +
            "<entries><xsl:apply-templates/></entries>" +
            "</xsl:template>" +
            "<xsl:template match=\"entry\">" +
            "<entry field=\"{field}\"/>" +
            "</xsl:template>" +
            "</xsl:stylesheet>";

    public void createWorkDirectoryIfNotExist() {
        Path workDir = Paths.get(WORK_DIRECTORY);
        if (Files.notExists(workDir)) {
            try {
                Files.createDirectory(workDir);
            } catch (IOException e) {
                System.err.println("\n ERROR: Can not create work directory [ " + WORK_DIRECTORY + " ] \n");
                throw new ServiceException(e);
            }
        }
    }

    public void createFirstXmlFile(List<Entry> entriesList) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Entries.class);
            Marshaller m = jc.createMarshaller();
            Entries entries = new Entries();
            entries.setEntries(entriesList);
            OutputStream os = new FileOutputStream(FIRST_XML_FILE);
            m.marshal(entries, os);
        } catch (JAXBException | FileNotFoundException e) {
            System.err.println("ERROR:  Can not create file 1.xml, " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void transformFirstXmlToSecondXml() {
        File file = new File(TRANSFER_XSLT_FILE);
        try {
            Files.write(Paths.get(file.toURI()), TEMPLATE_XSLT.getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("\n ERROR: Can not create working directory [ " + WORK_DIRECTORY + " ]\n");
            throw new ServiceException(e);
        }

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            Source xslt = new StreamSource(new File(TRANSFER_XSLT_FILE));
            transformer = factory.newTransformer(xslt);
            Source source = new StreamSource(new File(FIRST_XML_FILE));
            transformer.transform(source, new StreamResult(new File(SECOND_XML_FILE)));
        } catch (TransformerException e) {
            System.err.println("ERROR:  Can not transform 1.xml to 2.xml file, " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public ResultEntries getEntriesFromSecondXml() {
        ResultEntries entries = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(ResultEntries.class);
            Unmarshaller u = jc.createUnmarshaller();
            entries = (ResultEntries) u.unmarshal(new File(SECOND_XML_FILE));
        } catch (JAXBException e) {
            System.err.println("ERROR:  Can not unmarshal file 2.xml, " + e.getMessage());
            throw new ServiceException(e);
        }
        return entries;
    }
}
