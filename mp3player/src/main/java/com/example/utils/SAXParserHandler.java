package com.example.utils;

import com.example.Entity.Song;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luzhiyuan on 16/6/15.
 */
public class SAXParserHandler extends DefaultHandler {
    private List<Song> list;
    private String currentTag;
    private Song song;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("start document");
        list = new ArrayList<Song>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch, start, length);
        if (currentTag == "id") {
            if (!str.equals("/n") && !str.trim().equals("")) {
                song.setId(Integer.parseInt(str));
            }
        }
        if (currentTag == "song.name") {
            if (!str.equals("/n") && !str.trim().equals("")) {
                song.setSong_name(str);
            }
        }
        if (currentTag == "song.size") {
            if (!str.equals("/n") && !str.trim().equals("")) {
                song.setSong_size(Float.parseFloat(str));
            }
        }


        if (currentTag == "lrc.name") {
            if (!str.equals("/n") && !str.trim().equals("")) {
                song.setLrc_name(str);
            }
        }


        if (currentTag == "lrc.size") {
            if (!str.equals("/n") && !str.trim().equals("")) {
                song.setLrc_size(Float.parseFloat(str));
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName == "resource") {
            list.add(song);
            currentTag = "";
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if("resource".equals(qName)) {
            song = new Song();
        }
        currentTag = qName;
    }

    public List<Song> getList() {
        return list;
    }
}
