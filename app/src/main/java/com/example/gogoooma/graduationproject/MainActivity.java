package com.example.gogoooma.graduationproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_man:
                    manager.beginTransaction().replace(R.id.content_main, new ManFragment()).commit();
                    return true;
                case R.id.navigation_emotion:
                    manager.beginTransaction().replace(R.id.content_main, new Emotion_Fragment()).commit();
                    return true;
                case R.id.navigation_chat:
                    manager.beginTransaction().replace(R.id.content_main, new ChatFragment()).commit();
                    return true;
                case R.id.navigation_music:
                    manager.beginTransaction().replace(R.id.content_main, new MusicFragment()).commit();
                    return true;
                case R.id.navigation_setting:
                    manager.beginTransaction().replace(R.id.content_main, new SettingFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
