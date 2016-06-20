package com.example.mp3player;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Mp3ListActivity extends AppCompatActivity {
    private RadioButton net_btn;
    private RadioButton loc_btn;
    private RadioButton sch_btn;
    private RadioButton typ_btn;
    private RadioGroup rg;
    private Fragment currentFragment;
    private Fragment nextFragment;
    private Fragment[] fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_net);
        rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String tag = "";
                switch (checkedId) {
                    case R.id.net_btn:
                        /*getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1])
                                .show(fragments[0]).commit();*/
                        tag = "net_fragment";
                        nextFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_net);
                        switchFragment(currentFragment, nextFragment, tag);
                        currentFragment = nextFragment;
                        break;
                    case R.id.loc_btn:
//                        getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1])
//                                .show(fragments[1]).commit();
                        tag = "loc_fragment";
                        nextFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_loc);
                        switchFragment(currentFragment, nextFragment, tag);
                        currentFragment = nextFragment;
                        break;
                    case R.id.sch_btn:
                        break;
                    case R.id.typ_btn:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void switchFragment(Fragment from, Fragment to, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (from == null) {
            fragmentManager.beginTransaction().add(to, tag).commit();
            return;
        }
        if (currentFragment != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (to.isAdded()) {
                transaction.hide(from).show(to).commit();
            } else {
                transaction.hide(from).add(to, tag).commit();
            }
        }
    }

    public void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragments = new Fragment[2];
        fragments[0] = fragmentManager.findFragmentById(R.id.fragment_net);
        fragments[1] = fragmentManager.findFragmentById(R.id.fragment_loc);
    }
}

/*
public class Mp3ListActivity extends AppCompatActivity {
    private Handler myHandler;
    private List<Song> songs;
    private ListView listView;
    private List<Map<String, Object>> list;
    private Map<String, Song> songMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        */
/*init();
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                Song song = songMap.get(map.get("name"));
                Intent intent = new Intent();
                intent.setClass(Mp3ListActivity.this, DownloadMusicService.class);
                intent.putExtra("songInfo", song);
                startService(intent);
            }
        });

        myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.obj != null) {
                    songs = (List<Song>) msg.obj;
                    songMap = new HashMap<>();
                    for(Song song : songs) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", song.getSong_name());
                        songMap.put(song.getSong_name(), song);
                        float song_size = song.getSong_size();
                        BigDecimal a = new BigDecimal(song_size);
                        BigDecimal b = new BigDecimal(1024);
                        float result = a.divide(b).setScale(2, BigDecimal.ROUND_DOWN).floatValue();
                        map.put("size", result+"M");
                        list.add(map);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(Mp3ListActivity.this, list, R.layout.song_list, new String[]{"name", "size"}, new int[]{R.id.song_name, R.id.song_size});
                    listView.setAdapter(adapter);
                }
            }
        };*//*

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Constants.REFRESH:
                break;
            case Constants.ABOUT:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Constants.REFRESH, 1, R.string.refresh);
        menu.add(0, Constants.ABOUT, 2, R.string.about);
        return true;
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
*/
