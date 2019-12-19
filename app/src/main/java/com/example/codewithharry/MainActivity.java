package com.example.codewithharry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int gamestatus = 1;
    int block_count = 0;
    int[] tilestatus = {2,2,2,2,2,2,2,2,2};
    int win_position[][]={{0,1,2},{3,4,5},{6,7,8},{0,4,8},{0,3,6},{1,4,7},{2,5,8},{2,4,6}};

    Handler setDelay;

    ImageView tile;
    public void player_tap(View view){
        tile = (ImageView) view;
        int tile_no = Integer.parseInt(tile.getTag().toString());
        //Toast.makeText(this,"tile_no"+tile_no,Toast.LENGTH_LONG).show();
        if(tilestatus[tile_no]==2)
        {
            if(gamestatus == 0) {
                tile.setImageResource(R.drawable.zero);
                tilestatus[tile_no] = 0;
            }
            else{
                tile.setImageResource(R.drawable.cross);
                tilestatus[tile_no]=1;
            }
            gamestatus = 1-gamestatus;
            block_count++;
            for(int i=0;i<8;i++) {
                if (tile_no == win_position[i][0] || tile_no == win_position[i][1] || tile_no == win_position[i][2])
                    if (tilestatus[win_position[i][0]] == tilestatus[win_position[i][1]] && tilestatus[win_position[i][1]] == tilestatus[win_position[i][2]]) {
                        if (gamestatus == 0)
                            Toast.makeText(MainActivity.this, "X wins the game", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "O wins the game", Toast.LENGTH_LONG).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        //recreate();
                        // use recreate only when you try to retain the current state
                        // of activity in the new activity;
                    }
            }
        if(block_count==9)
        {
            Toast.makeText(MainActivity.this,"it's a tie",Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            Timer timer2 = new Timer();
            TimerTask testing = new TimerTask() {
                public void run(){
                    handler.post(new Runnable(){
                        public void run(){
                            String value;
                            for(int i=0;i<9;i++)
                            {
                                tilestatus[i]=2;
                                value = Integer.toString(i);
                                String View_name = "imageView" + value;
                                int resourceID = getResources().getIdentifier(View_name,"id",getPackageName());
                                tile= findViewById(resourceID);
                                tile.setImageResource(0);
                            }
                        }
                    });
                }
            };
            timer2.schedule(testing,2500);
        }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null)
        {
            gamestatus = savedInstanceState.getInt("gamestatus");
            tilestatus = savedInstanceState.getIntArray("tilestatus");
            block_count = savedInstanceState.getInt("block_count");
            for(int i=0;i<9;i++)
            {
                if(tilestatus[i]==2)
                    continue;
                String value = Integer.toString(i);
                String imageViewId = "imageView"+value;
                ImageView tile = (ImageView) findViewById(getResources().getIdentifier(imageViewId,"id",getPackageName()));
                if(tilestatus[i]==0){
                    tile.setImageResource(R.drawable.zero);
                }
                else {
                    tile.setImageResource(R.drawable.cross);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntArray("tilestatus",tilestatus);
        outState.putInt("gamestatus",gamestatus);
        outState.putInt("block_count",block_count);
    }
}
