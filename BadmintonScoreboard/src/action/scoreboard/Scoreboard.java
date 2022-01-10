package action.scoreboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badmintonscoreboard.R;

import domain.competition.Game;


public class Scoreboard extends Activity {
    private String leftCompetitor = "林丹";
    private String rightCompetitor = "李宗伟";
    private Game game = new Game(leftCompetitor, rightCompetitor);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        initLeftCompetitorTextView();
        initRightCompetitorTextView();
    }
    
    private void initLeftCompetitorTextView() {
        TextView competitorTv = (TextView) findViewById(R.id.competitor_left);
        competitorTv.setText(leftCompetitor);
        competitorTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                game.leftGetPoint();
                updateView(game);
            }
        });
    }
    
    private void initRightCompetitorTextView() {
        TextView competitorTv = (TextView) findViewById(R.id.competitor_right);
        competitorTv.setText(rightCompetitor);
        competitorTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                game.rightGetPoint();
                updateView(game);
            }
        });
    }

    private void updateView(Game game) {
        TextView leftScoreTexv = (TextView) findViewById(R.id.score_left);
        TextView rightScoreTexv = (TextView) findViewById(R.id.score_right);
        rightScoreTexv.setText(String.valueOf(game.rightScore()));
        leftScoreTexv.setText(String.valueOf(game.leftScore()));

        ImageView leftServiceStampImgv = (ImageView) findViewById(R.id.service_stamp_left);
        ImageView rightServiceStampImgv = (ImageView) findViewById(R.id.service_stamp_right);
        if (leftCompetitor.equals(game.service())) {
            leftServiceStampImgv.setVisibility(View.VISIBLE);
            rightServiceStampImgv.setVisibility(View.INVISIBLE);
        } else {
            rightServiceStampImgv.setVisibility(View.VISIBLE);
            leftServiceStampImgv.setVisibility(View.INVISIBLE);
        }

        if (game.gameOver()) {
            String toastMsg = "对局已结束, " + game.service() + " 获胜";
            Toast.makeText(Scoreboard.this, toastMsg, Toast.LENGTH_SHORT).show();
        }
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
