package com.example.mp3player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.utils.DownloadUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luzhiyuan on 16/6/19.
 */
public class LocalMusicFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_list, container, false);
        File files[] = DownloadUtils.getFileFromDir();
        System.out.println("=====" + files.length);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (File f : files) {
            map.put("name", f.getName());
            long length = f.length();
            BigDecimal a = new BigDecimal(length);
            BigDecimal b = new BigDecimal(1024 * 1024);
            float size = a.divide(b, 2, BigDecimal.ROUND_HALF_DOWN).floatValue();
            map.put("size", size + "M");
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), list, R.layout.song_list, new String[]{"name", "size"}, new int[]{R.id.song_name, R.id.song_size});
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
