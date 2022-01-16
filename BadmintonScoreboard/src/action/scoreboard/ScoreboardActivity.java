package action.scoreboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scoreboard.R;

import domain.competition.Game;


public class ScoreboardActivity extends Activity {
    private Game game = null;
    
    private OnLongClickListener competiterCheckinDialog = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        
        competiterCheckinDialog = competiterCheckinDialog();
        initLeftCompetitorTextView();
        initRightCompetitorTextView();
    }

    private void initLeftCompetitorTextView() {
        TextView competitorTv = (TextView) findViewById(R.id.competitor_left);
        competitorTv.setText("选手姓名");
        competitorTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game == null) {
                    Toast.makeText(ScoreboardActivity.this, "请完善选手信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                game.leftGetPoint();
                updateView(game);
            }
        });
        competitorTv.setOnLongClickListener(competiterCheckinDialog);
    }
    
    private void initRightCompetitorTextView() {
        TextView competitorTv = (TextView) findViewById(R.id.competitor_right);
        competitorTv.setText("选手姓名");
        competitorTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game == null) {
                    Toast.makeText(ScoreboardActivity.this, "请完善选手信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                game.rightGetPoint();
                updateView(game);
            }
        });
        competitorTv.setOnLongClickListener(competiterCheckinDialog);
    }

    private void updateView(Game game) {
        TextView leftCompetiterTexv = (TextView) findViewById(R.id.competitor_left);
        TextView rightCompetiterTexv = (TextView) findViewById(R.id.competitor_right);
        leftCompetiterTexv.setText(game.leftCompetiter());
        rightCompetiterTexv.setText(game.rightCompetiter());
        
        TextView leftScoreTexv = (TextView) findViewById(R.id.score_left);
        TextView rightScoreTexv = (TextView) findViewById(R.id.score_right);
        rightScoreTexv.setText(String.valueOf(game.rightScore()));
        leftScoreTexv.setText(String.valueOf(game.leftScore()));

        ImageView leftServiceStampImgv = (ImageView) findViewById(R.id.service_stamp_left);
        ImageView rightServiceStampImgv = (ImageView) findViewById(R.id.service_stamp_right);
        if (game.service().equals(game.leftCompetiter())) {
            leftServiceStampImgv.setVisibility(View.VISIBLE);
            rightServiceStampImgv.setVisibility(View.INVISIBLE);
        } else {
            rightServiceStampImgv.setVisibility(View.VISIBLE);
            leftServiceStampImgv.setVisibility(View.INVISIBLE);
        }

        if (game.gameOver()) {
            String toastMsg = "对局已结束, " + game.service() + " 获胜";
            Toast.makeText(ScoreboardActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private OnLongClickListener competiterCheckinDialog() {
        return new OnLongClickListener() {
            String leftCompetiter = null;
            String rightCompetiter = null;
            
            @Override
            public boolean onLongClick(View v) {
                final TextView tv = (TextView)v;
                final EditText editText = new EditText(ScoreboardActivity.this);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(ScoreboardActivity.this);
                inputDialog.setTitle("请输入参赛者姓名").setView(editText);
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String competitorName = editText.getText().toString();
                        tv.setText(competitorName);
                        if (tv.getId() == R.id.competitor_left) {
                            leftCompetiter = competitorName;
                        } else if (tv.getId() == R.id.competitor_right) {
                            rightCompetiter = competitorName;
                        }
                        
                        if (leftCompetiter != null && rightCompetiter != null) {
                            Game newGame = new Game(leftCompetiter, rightCompetiter);
                            ScoreboardActivity.this.game = newGame;
                            updateView(game);
                            
                            String msg = "参赛选手 设置成功！\n" + leftCompetiter + " vs " + rightCompetiter;
                            Toast.makeText(ScoreboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
                return true;
            }
        };
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
