package com.example.mp3player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.Entity.Song;
import com.example.utils.Constants;
import com.example.utils.DownloadUtils;

/**
 * Created by luzhiyuan on 16/6/18.
 */
public class DownloadMusicService extends Service {

    private NotificationManager manager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //调用文件下载的线程
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Song songInfo = (Song) intent.getSerializableExtra("songInfo");
        if (!DownloadUtils.checkExistFile(songInfo.getSong_name())) {
            new DownloadThread(songInfo).start();
        } else {
            System.out.println("文件已存在!");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    class DownloadThread extends Thread {
        private Song song;

        DownloadThread(Song song) {
            this.song = song;
        }

        @Override
        public void run() {
            //执行下载文件操作
            int flag = DownloadUtils.downloadSongAndLrc2SDcard(Constants.DOWNLOAD_URL + song.getSong_name_en(), song.getSong_name());
            Notification.Builder builder = new Notification.Builder(DownloadMusicService.this);
            if (flag == 1) {
                //下载成功
                builder.setContentText("文件下载成功");
            } else {
                //下载失败
                builder.setContentText("文件下载失败");
            }
            builder.setAutoCancel(true);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            Intent intent = new Intent(DownloadMusicService.this, Mp3ListActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(DownloadMusicService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            Notification noti = builder.build();
            manager.notify(0, noti);
        }
    }
}
