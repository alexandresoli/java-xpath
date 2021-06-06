package com.soli.xpath;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class XPathTest {

	private static Document xmlDocument;
	private static XPath xpath;
	private XPathExpression expression;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream xmlInputStream = classLoader.getResourceAsStream("inventory.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		xmlDocument = builder.parse(xmlInputStream);

		xpath = XPathFactory.newDefaultInstance().newXPath();

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void listBookTitlesTest() throws XPathExpressionException {

		expression = xpath.compile("//book/title/text()");
		NodeList nodes = (NodeList) expression.evaluate(xmlDocument, XPathConstants.NODESET);

		for (int i = 0; i < nodes.getLength(); i++) {
			System.out.println(nodes.item(i).getNodeValue());
			assertNotNull(nodes.item(i).getNodeValue());
		}
	}

	@Test
	void listBooksWrittenAfter2021Test() throws XPathExpressionException {

		expression = xpath.compile("//book[@year>2001]");
		NodeList nodes = (NodeList) expression.evaluate(xmlDocument, XPathConstants.NODESET);

		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			NamedNodeMap map = node.getAttributes();
			Integer year = Integer.parseInt(map.getNamedItem("year").getNodeValue());
			assertTrue(year > 2001);
		}
	}
}
