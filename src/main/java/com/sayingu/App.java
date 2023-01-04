package com.sayingu;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Map<String, Object> extractedMap = processRecord("공공데이터 제공 표준 현황(2022.10월).pdf");
        System.out.println(extractedMap.toString());
    }

    public static Map<String, Object> processRecord(String file) {
        Map<String, Object> map = new HashMap<String, Object>();
        InputStream input = null;
        try {
            input = App.class.getResourceAsStream(file);
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            ParseContext parseContext = new ParseContext();
            parser.parse(input, handler, metadata, parseContext);
            map.put("text", handler);
            map.put("title", metadata.get(TikaCoreProperties.TITLE));
            map.put("pageCount", metadata.get("xmpTPg:NPages"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
