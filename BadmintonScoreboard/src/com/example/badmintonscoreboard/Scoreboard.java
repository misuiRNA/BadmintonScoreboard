package com.example.badmintonscoreboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Scoreboard extends Activity {
    boolean inGaming = true;
    
    ImageView leftServiceStamp = null;
    ImageView rightServiceStamp = null;
    
    int leftScoreValue = 0;
    TextView leftScore = null;
    TextView leftCompetitor = null;
    
    int rightScoreValue = 0;
    TextView rightScore = null;
    TextView rightCompetitor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        
        leftServiceStamp = (ImageView) findViewById(R.id.service_stamp_left);
        rightServiceStamp = (ImageView) findViewById(R.id.service_stamp_right);
        
        leftScore = (TextView) findViewById(R.id.score_left);
        leftCompetitor = (TextView) findViewById(R.id.competitor_left);
        leftCompetitor.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (!inGaming) {
                    Toast.makeText(Scoreboard.this, "对局已结束", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                leftScoreValue++;
                leftScore.setText(String.valueOf(leftScoreValue));
                
                leftServiceStamp.setVisibility(View.VISIBLE);
                rightServiceStamp.setVisibility(View.INVISIBLE);
                
                if ((leftScoreValue >= 21 && leftScoreValue - rightScoreValue >= 2) || leftScoreValue == 30){
                    inGaming = false;
                    String toastMsg = "对局结束，" + leftCompetitor.getText() + "获胜";
                    Toast.makeText(Scoreboard.this, toastMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
        

        rightScore = (TextView) findViewById(R.id.score_right);
        rightCompetitor = (TextView) findViewById(R.id.competitor_right);
        rightCompetitor.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (!inGaming) {
                    Toast.makeText(Scoreboard.this, "对局已结束", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                rightScoreValue++;
                rightScore.setText(String.valueOf(rightScoreValue));
                
                leftServiceStamp.setVisibility(View.INVISIBLE);
                rightServiceStamp.setVisibility(View.VISIBLE);
                
                if ((rightScoreValue >= 21 && rightScoreValue - leftScoreValue >= 2) || rightScoreValue == 30) {
                    inGaming = false;
                    String toastMsg = "对局结束，" + rightCompetitor.getText() + " 获胜";
                    Toast.makeText(Scoreboard.this, toastMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scoreboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
