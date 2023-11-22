package dev.awlb.opt.xml;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileHandler {

	public static double getFileVersion(Document xmlDocument) {
		// get file version number from XML document version tags
		NodeList versionNodes = xmlDocument.getElementsByTagName("version");
		if (versionNodes.item(0) != null) {
			double version = Double.parseDouble(versionNodes.item(0)
					.getTextContent());
			return version;
		} else {
			return -1;
		}
	}

	public static String getPlotType(Document xmlDocument) {
		// get the plot type the file represents
		NodeList typeNodes = xmlDocument.getElementsByTagName("type");
		String type = typeNodes.item(0).getTextContent();
		return type;
	}

	public static Document openCompressedFile(File file) throws IOException,
			SAXException, ParserConfigurationException {
		// create buffered input stream
		BufferedInputStream inputStream = new BufferedInputStream(
				new GZIPInputStream(new FileInputStream(file)));
		// Create a builder factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringComments(true);
		// create XML document from input stream
		Document xmlDocument = factory.newDocumentBuilder().parse(inputStream);
		// close stream
		inputStream.close();
		return xmlDocument;
	}

	public static Document openPlainFile(File file) throws SAXException,
			IOException, ParserConfigurationException {
		// Create a builder factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringComments(true);
		// create XML document from input stream
		Document xmlDocument = factory.newDocumentBuilder().parse(file);
		return xmlDocument;
	}

	public static void saveCompressedFile(StringBuffer fileContentBuffer,
			File file) throws FileNotFoundException, IOException {
		// get data bytes to be written
		byte[] fileContentBytes = fileContentBuffer.toString().getBytes("UTF8");
		// create buffered output stream
		BufferedOutputStream outputStream = new BufferedOutputStream(
				new GZIPOutputStream(new FileOutputStream(file)));
		// write bytes to file
		outputStream.write(fileContentBytes);
		// close stream
		outputStream.close();
	}

	public static void savePlainFile(StringBuffer fileContentBuffer, File file)
			throws IOException {
		// get data bytes to be written
		byte[] fileContentBytes = fileContentBuffer.toString().getBytes("UTF8");
		// create buffered output stream
		BufferedOutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(file));
		// write bytes to file
		outputStream.write(fileContentBytes);
		// close stream
		outputStream.close();
	}
}
