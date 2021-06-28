package orak.uzeyir.geyikchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    TextView Progress;
    int ProgressValue;

    protected Handler handler;
    Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bu kısımda action bardaki yazı ve rengi degistiriyoruz değiştiriyoruz.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
        actionBar.setTitle("GeYik");

        Progress = findViewById(R.id.textView1);
        myThread = new Thread(new CountingThread(0));
        myThread.start();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0) {
                    Progress.setText("Uygulama Açılıyor... \n" +
                            " " + ProgressValue + "%");
                } else if (msg.what == 1) {
                    Progress.setText("Yönlendiriliyor... \n" +
                            " " + ProgressValue + "%");

                    Intent intent = new Intent(MainActivity.this, GirisActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });

    }

    class CountingThread implements Runnable {
        int i = 0;

        CountingThread(int start) {
            i = start;
        }

        @Override
        public void run() {
            while (i < 100) {
                SystemClock.sleep(50);
                i++;
                if (i % 10 == 0) {
                    ProgressValue = i;
                    handler.sendEmptyMessage(0);
                }
            }
            ProgressValue = i;
            handler.sendEmptyMessage(1);
        }
    }
}