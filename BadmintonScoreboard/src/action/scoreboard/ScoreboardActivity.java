package action.scoreboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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


public class ScoreboardActivity extends Activity {
    private Game game = null;
    
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
                if (game == null) {
                    showCompetiterSettingsDialog();
                    Toast.makeText(ScoreboardActivity.this, "请填写选手信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                if (competitorTv.getId() == R.id.competitor_left) {
                    game.leftGetPoint();
                } else if (competitorTv.getId() == R.id.competitor_right) {
                    game.rightGetPoint();
                } else {
                    // TODO error branch
                }
                updateView(game);
            }
        });
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
                final EditText leftEditText = (EditText)editDialog.findViewById(R.id.competitor_edit_left);
                final EditText rightEditText = (EditText)editDialog.findViewById(R.id.competitor_edit_right);
                final String leftCompetitorName = leftEditText.getText().toString();
                final String rightCompetitorName = rightEditText.getText().toString();
                
                Game newGame = new Game(leftCompetitorName, rightCompetitorName);
                ScoreboardActivity.this.game = newGame;
                updateView(game);
                editDialog.hide();
                
                String msg = "参赛选手 设置成功！\n" + leftCompetitorName + " vs " + rightCompetitorName;
                Toast.makeText(ScoreboardActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        editDialog.show();
    }

}
