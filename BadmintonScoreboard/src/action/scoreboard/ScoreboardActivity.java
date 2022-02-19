package action.scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scoreboard.R;

import domain.competition.Game;
import domain.competition.Match;
import domain.info.Player;


public class ScoreboardActivity extends Activity {
    private Match match = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        
        initCompetitorTextView((TextView)findViewById(R.id.competitor_left));
        initCompetitorTextView((TextView)findViewById(R.id.competitor_right));
    }

    private void initCompetitorTextView(final TextView competitorTv) {
        competitorTv.setText("选手姓名");
        competitorTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (match == null) {
                    showCompetiterSettingsDialog();
                    Toast.makeText(ScoreboardActivity.this, "请填写选手信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                Game currentGame = match.currentGame();
                if (competitorTv.getId() == R.id.competitor_left) {
                    currentGame.leftWinRound();
                } else if (competitorTv.getId() == R.id.competitor_right) {
                    currentGame.rightWinRound();
                } else {
                    // TODO error branch
                }
                updateView(match);
            }
        });
    }

    private void updateView(Match match) {
        TextView leftCompetiterTexv = (TextView) findViewById(R.id.competitor_left);
        TextView rightCompetiterTexv = (TextView) findViewById(R.id.competitor_right);
        leftCompetiterTexv.setText(match.leftPlayer().getName());
        rightCompetiterTexv.setText(match.rightPlayer().getName());

        Map<Integer, Integer> miniSbMap = new HashMap<Integer, Integer>() { {
                put(0x00, R.id.mini_scoreboarder_left_1);
                put(0x10, R.id.mini_scoreboarder_right_1);
                put(0x01, R.id.mini_scoreboarder_left_2);
                put(0x11, R.id.mini_scoreboarder_right_2);
                put(0x02, R.id.mini_scoreboarder_left_3);
                put(0x12, R.id.mini_scoreboarder_right_3);
            }
        };
        List<Game> gameList = match.gameList();
        for (int index = 0; index < match.gameList().size(); ++index) {
            Game game = gameList.get(index);
            int leftMiniId = miniSbMap.get(index);
            int rightMiniId = miniSbMap.get(0x10 + index);
            TextView leftMiniScoreTexv = (TextView) findViewById(leftMiniId);
            TextView rightMiniScoreTexv = (TextView) findViewById(rightMiniId);
            leftMiniScoreTexv.setText(String.valueOf(game.leftCompetitor().getScore()));
            rightMiniScoreTexv.setText(String.valueOf(game.rightCompetitor().getScore()));
        }

        Game game = match.currentGame();
        TextView leftScoreTexv = (TextView) findViewById(R.id.score_left);
        TextView rightScoreTexv = (TextView) findViewById(R.id.score_right);
        rightScoreTexv.setText(String.valueOf(game.rightCompetitor().getScore()));
        leftScoreTexv.setText(String.valueOf(game.leftCompetitor().getScore()));

        ImageView leftServiceStampImgv = (ImageView) findViewById(R.id.service_stamp_left);
        ImageView rightServiceStampImgv = (ImageView) findViewById(R.id.service_stamp_right);
        if (game.currentServe() == game.leftCompetitor().getPlayer()) {
            leftServiceStampImgv.setVisibility(View.VISIBLE);
            rightServiceStampImgv.setVisibility(View.INVISIBLE);
        } else {
            rightServiceStampImgv.setVisibility(View.VISIBLE);
            leftServiceStampImgv.setVisibility(View.INVISIBLE);
        }

        if (match.isOver()) {
            String toastMsg = "比赛结束, " + match.winner().getName() + " 获胜";
            Toast.makeText(ScoreboardActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
        } else if (match.currentGame().isOver()) {
            String toastMsg = "本局结束, " + game.currentServe().getName() + " 获胜";
            Toast.makeText(ScoreboardActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
            
            match.createNewGame();
            updateView(match);
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
        if (id == R.id.competitor_settings) {
            showCompetiterSettingsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCompetiterSettingsDialog() {
        final Dialog editDialog = new Dialog(ScoreboardActivity.this);
        editDialog.setTitle("参赛者信息");
        editDialog.setContentView(R.layout.competitor_settings_dialog);
        Button okBtn = (Button)editDialog.findViewById(R.id.competitor_edit_ok);
        
        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Player leftPlayer = createLeftPlayer(editDialog);
                final Player rightPlayer = createRightPlayer(editDialog);
                ScoreboardActivity.this.match = new Match(leftPlayer, rightPlayer);

                updateView(match);
                editDialog.hide();
                String msg = "参赛选手 设置成功！\n" + leftPlayer.getName() + " vs " + rightPlayer.getName();
                Toast.makeText(ScoreboardActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        editDialog.show();
    }
    
    private Player createRightPlayer(final Dialog editDialog) {
        final EditText rightEditText = (EditText)editDialog.findViewById(R.id.competitor_edit_right);
        String rightPlayerName = rightEditText.getText().toString();
        return new Player(rightPlayerName);
    }

    private Player createLeftPlayer(final Dialog editDialog) {
        final EditText leftEditText = (EditText)editDialog.findViewById(R.id.competitor_edit_left);
        String leftPlayerName = leftEditText.getText().toString();
        return new Player(leftPlayerName);
    }

}
