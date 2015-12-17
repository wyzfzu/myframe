package com.myframe.core.util;

import com.google.common.base.Optional;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * XML工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class XmlUtils {

    private static final Logger logger = LogUtils.get();

    public static interface ElementCallback {
        public boolean doCallback(Element element);
    }

    public static Optional<Document> fromFile(String fileName) {
        InputStream is = ResourceUtils.getResourceStream(fileName);
        if (is == null) {
            logger.error("文件“{}”不存在！", fileName);
            return Optional.absent();
        }
        return fromStream(is);
    }

    public static Optional<Document> fromFile(File file) {
        if (file == null || !file.exists()) {
            logger.error("文件不存在！");
            return Optional.absent();
        }
        if (file.isDirectory()) {
            logger.error("文件”{}“是一个文件夹！", file.getName());
            return Optional.absent();
        }

        return fromStream(FileUtils.newInputStream(file));
    }

    public static Optional<Document> fromStream(InputStream is) {
        Reader reader = StreamUtils.toBufferedReader(is);
        return fromReader(reader);
    }

    public static Optional<Document> fromReader(Reader reader) {
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding(Encoding.S_UTF8);
        try {
            return Optional.fromNullable(saxReader.read(reader));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.absent();
    }

    public static Optional<Document> fromString(String dom) {
        try {
            return Optional.fromNullable(DocumentHelper.parseText(dom));
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.absent();
    }

    public static Document create() {
        return DocumentHelper.createDocument();
    }

    public static Document create(Element element) {
        return DocumentHelper.createDocument(element);
    }

    public static Optional<Element> getRoot(Document doc) {
        return doc == null ? Optional.<Element>absent()
                : Optional.fromNullable(doc.getRootElement());
    }

    public static Optional<Element> getChild(Element element, String name) {
        return element == null ? Optional.<Element>absent()
                : Optional.fromNullable(element.element(name));
    }

    public static List<Element> getChildren(Element element) {
        return element == null ? Collections.emptyList() : element.elements();
    }

    public static List<Element> getElements(Element element, String xpath) {
        return element == null || xpath == null
                ? Collections.emptyList()
                : element.selectNodes(xpath);
    }

    public static Optional<Attribute> getAttr(Element element, String name) {
        return element == null ? Optional.<Attribute>absent()
                : Optional.fromNullable(element.attribute(name));
    }

    public static List<Attribute> getAttrs(Element element) {
        return element == null ? Collections.emptyList() : element.attributes();
    }

    public static String getChildText(Element element, String name) {
        return element == null ? "" : element.elementTextTrim(name);
    }

    public static String getAttrText(Element element, String name) {
        return element == null ? "" : element.attributeValue(name);
    }

    public static void forEach(Element element, final ElementCallback ec) {
        if (ec == null || element == null) {
            return ;
        }
        if (element.isTextOnly()) {
            ec.doCallback(element);
            return ;
        }
        List<Element> children = getChildren(element);
        for (Element e : children) {
            if (!ec.doCallback(e)) {
                break;
            }
        }
    }

    public static Optional<Element> addChild(Document doc, String name, String text) {
        if (doc == null) {
            return Optional.absent();
        }
        Element child = doc.addElement(name);
        child.setText(text);
        return Optional.of(child);
    }

    public static Optional<Element> addChildCDATA(Document doc, String name, String text) {
        if (doc == null) {
            return null;
        }
        Element child = doc.addElement(name);
        child.addCDATA(text);
        return Optional.of(child);
    }

    public static Optional<Element> addChild(Element element, String name, String text) {
        if (element == null) {
            return Optional.absent();
        }
        Element child = element.addElement(name);
        child.setText(text);
        return Optional.of(child);
    }

    public static Optional<Element> addChildCDATA(Element element, String name, String text) {
        if (element == null) {
            return Optional.absent();
        }
        Element child = element.addElement(name);
        child.addCDATA(text);
        return Optional.of(child);
    }

    public static Optional<Element> addAttr(Element element, String name, String text) {
        if (element == null) {
            return Optional.absent();
        }
        return Optional.of(element.addAttribute(name, text));
    }

    public static void write(Document doc, OutputStream os) {
        write(doc, os, true);
    }

    public static void write(Document doc, OutputStream os, boolean pretty) {
        write(doc, StreamUtils.toBufferedWriter(os), pretty);
    }

    public static void write(Document doc, Writer writer) {
        write(doc, writer, true);
    }

    public static void write(Document doc, Writer writer, boolean pretty) {
        if (doc == null) {
            return ;
        }
        OutputFormat of = null;
        if (pretty) {
            of = OutputFormat.createPrettyPrint();
        } else {
            of = OutputFormat.createCompactFormat();
        }
        of.setEncoding(Encoding.S_UTF8);
        XMLWriter xmlWriter = new XMLWriter(writer, of);
        try {
            xmlWriter.write(doc);
            xmlWriter.flush();
            xmlWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
