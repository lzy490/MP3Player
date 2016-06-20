package com.example.mp3player;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.Entity.Song;
import com.example.utils.Constants;
import com.example.utils.DownloadUtils;
import com.example.utils.SAXParserHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by luzhiyuan on 16/6/19.
 */
public class NetMusicFragment extends Fragment {
    private Handler myHandler;
    private List<Song> songs;
    private ListView listView;
    private List<Map<String, Object>> list;
    private Map<String, Song> songMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        View view = inflater.inflate(R.layout.net_list, container, false);
        listView = (ListView) view.findViewById(R.id.net_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                Song song = songMap.get(map.get("name"));
                Intent intent = new Intent();
                intent.setClass(getActivity(), DownloadMusicService.class);
                intent.putExtra("songInfo", song);
                getActivity().startService(intent);
            }
        });


        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj != null) {
                    songs = (List<Song>) msg.obj;
                    songMap = new HashMap<>();
                    for (Song song : songs) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", song.getSong_name());
                        songMap.put(song.getSong_name(), song);
                        float song_size = song.getSong_size();
                        BigDecimal a = new BigDecimal(song_size);
                        BigDecimal b = new BigDecimal(1024);
                        float result = a.divide(b).setScale(2, BigDecimal.ROUND_DOWN).floatValue();
                        map.put("size", result + "M");
                        list.add(map);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.song_list, new String[]{"name", "size"}, new int[]{R.id.song_name, R.id.song_size});
                    listView.setAdapter(adapter);
                }
            }
        };
        return view;
    }

    class downloadThread extends Thread {
        @Override
        public void run() {
            String result = DownloadUtils.download(Constants.SERVER_URL);
            List<Song> list = saxparse(result);
            Message message = myHandler.obtainMessage();
            message.obj = list;
            message.sendToTarget();
        }
    }

    public List<Song> saxparse(String xmlStr) {
        List<Song> list = new ArrayList<Song>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            SAXParserHandler handler = new SAXParserHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(new StringReader(xmlStr)));
            list = handler.getList();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void init() {
        new downloadThread().start();
        list = new ArrayList<>();
    }
}
