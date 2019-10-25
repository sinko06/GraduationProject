package com.example.gogoooma.graduationproject;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    View v;
    private DBHelper dbHelper;
    DBMusicHelper musichelper;
    SQLiteDatabase database;
    MediaPlayer mp = new MediaPlayer();
    ListView audiolistView;
    ArrayAdapter<String> adapter;
    SharedPreferences auto;
    private ArrayList<Song_Item> songsList = null; // 데이터 리스트
    private ListViewAdapter listViewAdapter = null; // 리스트뷰에 사용되는 ListViewAdapter

    int emotion = 90;
    int musicnum = 0;
    List<Music> musicArrayList;
    List<String> musictitlelist;
    List<Float> musichappylist;
    ArrayList<String> musicFile = new ArrayList<>();
    FloatingActionButton fabbutton;

    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_music, container, false);
        audiolistView = (ListView) v.findViewById(R.id.musiclistview);
        TextView tv1 = (TextView) v.findViewById(R.id.music_profile_name);
        TextView tv2 = (TextView) v.findViewById(R.id.music_profile_phone);
        TextView musicnumText = (TextView)v.findViewById(R.id.my_music_1_2);
        TextView emotionScoreText = (TextView)v.findViewById(R.id.my_music_2_2);
        fabbutton = (FloatingActionButton) v.findViewById(R.id.stopFab);

        emotionScoreText.setText(String.valueOf(emotion) + " 점");
        auto = getActivity().getSharedPreferences("savefile", Activity.MODE_PRIVATE);
        tv1.setText(auto.getString("name", null));
        tv2.setText(auto.getString("phone", null));
        musichelper = new DBMusicHelper(getActivity(), "musiclist", null, 1);
        database = musichelper.getWritableDatabase();
        songsList = new ArrayList<Song_Item>(); // ArrayList 생성
        musicArrayList =  musichelper.getAllMusic();
        musictitlelist = new ArrayList();
        musichappylist = new ArrayList();
        for(int i=0; i< musicArrayList.size();i++){
            musictitlelist.add(musicArrayList.get(i).Title);
            musichappylist.add(musicArrayList.get(i).happy);
        }
        listViewAdapter = new ListViewAdapter(getActivity()); // Adapter 생성
        audiolistView.setAdapter(listViewAdapter); // 어댑터를 리스트뷰에 세팅
        audiolistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        audiolistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if(mp.isPlaying()) {
                        mp.stop();
                    }
                    mp = new MediaPlayer();
                    mp.setDataSource(musicFile.get(position));
                    mp.prepare();
                    mp.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        fabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()) {
                    fabbutton.setImageResource(android.R.drawable.ic_media_play);
                    mp.pause();
                }else{
                    fabbutton.setImageResource(android.R.drawable.ic_media_pause);
                    mp.start();
                }
            }
        });
        loadAudio();
        musicnumText.setText(String.valueOf(musicnum) + " 개");
        return v;
    }

    public void loadAudio() {

        ContentResolver contentResolver = getContext().getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            do {
                long track_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                long mDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String datapath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String wholedata[] = datapath.split("/");
                String realtitle = wholedata[wholedata.length-1].replace(".mp3","");
                // Save to audioList
                for(int i=0; i< musictitlelist.size();i++){
                    if(musictitlelist.get(i).equals(realtitle)){
                        if(musichappylist.get(i)*100 >= emotion-10 && musichappylist.get(i)*100 <= emotion+10){
                            listViewAdapter.addItem(track_id, albumId, title, artist, album, mDuration, datapath);
                            musicFile.add(datapath);
                            musicnum++;
                        }
                    }
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    class ViewHolder {
        public ImageView mImgAlbumArt;
        public TextView mTitle;
        public TextView mSubTitle;
        public TextView mDuration;
    }

    private class ListViewAdapter extends BaseAdapter {
        Context context;

        public ListViewAdapter(Context context) {
            this.context = context;
        }

        // 음악 데이터 추가를 위한 메소드
        public void addItem(long mId, long AlbumId, String Title, String Artist, String Album, long Duration, String DataPath) {
            Song_Item item = new Song_Item();
            item.setmId(mId);
            item.setAlbumId(AlbumId);
            item.setTitle(Title);
            item.setArtist(Artist);
            item.setAlbum(Album);
            item.setDuration(Duration);
            item.setDataPath(DataPath);
            songsList.add(item);
        }


        @Override
        public int getCount() {
            return songsList.size(); // 데이터 개수 리턴
        }

        @Override
        public Object getItem(int position) {
            return songsList.get(position);
        }

        // 지정한 위치(position)에 있는 데이터 리턴
        @Override
        public long getItemId(int position) {
            return position;
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            final Context context = parent.getContext();
            final Integer index = Integer.valueOf(position);

            // 화면에 표시될 View
            if (convertView == null) {
                viewHolder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.song_item, parent, false);

                convertView.setBackgroundColor(0x00FFFFFF);
                convertView.invalidate();

                // 화면에 표시될 View 로부터 위젯에 대한 참조 획득
                viewHolder.mImgAlbumArt = (ImageView) convertView.findViewById(R.id.album_Image);
                viewHolder.mTitle = (TextView) convertView.findViewById(R.id.txt_title);
                viewHolder.mSubTitle = (TextView) convertView.findViewById(R.id.txt_subtitle);
                viewHolder.mDuration = (TextView) convertView.findViewById(R.id.txt_duration);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // PersonData 에서 position 에 위치한 데이터 참조 획득
            final Song_Item songItem = songsList.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            Bitmap albumArt = MusicFragment.getArtworkQuick(context, (int) songItem.getAlbumId(), 100, 100);
            if (albumArt != null) {
                viewHolder.mImgAlbumArt.setImageBitmap(albumArt);
            }
            viewHolder.mTitle.setText(songItem.getTitle());
            viewHolder.mSubTitle.setText(songItem.getArtist());

            int dur = (int) songItem.getDuration();
            int hrs = (dur / 3600000);
            int mns = (dur / 60000) % 60000;
            int scs = dur % 60000 / 1000;
            String songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
            if (hrs == 0) {
                songTime = String.format("%02d:%02d", mns, scs);
            }
            viewHolder.mDuration.setText(songTime);


            return convertView;
        }
    }

    /* Album ID로 부터 Bitmap 이미지를 생성해 리턴해 주는 메소드 */
    public static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
    public static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

    // Get album art for specified album. This method will not try to
    // fall back to getting artwork directly from the file, nor will it attempt to repair the database.
    public static Bitmap getArtworkQuick(Context context, int album_id, int w, int h) {
        w -= 2;
        h -= 2;
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            ParcelFileDescriptor fd = null;
            try {
                fd = res.openFileDescriptor(uri, "r");
                int sampleSize = 1;

                sBitmapOptionsCache.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, sBitmapOptionsCache);
                int nextWidth = sBitmapOptionsCache.outWidth >> 1;
                int nextHeight = sBitmapOptionsCache.outHeight >> 1;
                while (nextWidth > w && nextHeight > h) {
                    sampleSize <<= 1;
                    nextWidth >>= 1;
                    nextHeight >>= 1;
                }

                sBitmapOptionsCache.inSampleSize = sampleSize;
                sBitmapOptionsCache.inJustDecodeBounds = false;
                Bitmap b = BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, sBitmapOptionsCache);

                if (b != null) {
                    // finally rescale to exactly the size we need
                    if (sBitmapOptionsCache.outWidth != w || sBitmapOptionsCache.outHeight != h) {
                        Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true);
                        b.recycle();
                        b = tmp;
                    }
                }

                return b;
            } catch (FileNotFoundException e) {
            } finally {
                try {
                    if (fd != null)
                        fd.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

}
