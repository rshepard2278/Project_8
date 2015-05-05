package com.rshepard.littleleagueapp;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Creates a Baseball score activity
 */
public class MainActivity extends Activity {

	private Button strikeButton;
	private Button ballButton;
	private Button outButton;
	private Button runButton;
	private Button foulButton;

	private TextView strikeText;
	private TextView ballText;
	private TextView outText;
	private TextView homeTitle;
	private TextView visitorTitle;
	private TextView homeScore;
	private TextView visitorScore;

	private Team home;
	private Team visitor;
	private Team currentTeam;

	private boolean isVisitorAtBat;

	private int outs;
	private int strikes;
	private int balls;

	private final MediaPlayer mp = new MediaPlayer();

	private final static String TAG = "Little_League_App";

	//

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Begins the application and initializes variables
	 * and onClickListeners
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initLocalVariables();
		setTeams();
		intiButtonsAndText();
		updateText();
	}
	
	/**
	 * Initializes outs, strikes, and
	 * balls to zero
	 */
	private void initLocalVariables() {
		outs = 0;
		strikes = 0;
		balls = 0;
	}

	/**
	 * Sets the two teams:
	 * Home and Visitor
	 * Sets the current team to visitor and 
	 * the isVisitorAtBat control variable to
	 * true
	 */
	private void setTeams() {
		home = new Team("Home");
		visitor = new Team("Visitor");
		currentTeam = visitor;
		isVisitorAtBat = true;
	}

	/**
	 * Initializes all the buttons and
	 * call the registerOnClickListeners
	 * method
	 */
	private void intiButtonsAndText() {
		strikeButton = (Button) findViewById(R.id.button_strike);
		ballButton = (Button) findViewById(R.id.button_ball);
		outButton = (Button) findViewById(R.id.button_out);
		foulButton = (Button) findViewById(R.id.button_foul);
		runButton = (Button) findViewById(R.id.button_run);

		strikeText = (TextView) findViewById(R.id.strike_count);
		ballText = (TextView) findViewById(R.id.ball_count);
		outText = (TextView) findViewById(R.id.out_count);
		homeTitle = (TextView) findViewById(R.id.home_team);
		visitorTitle = (TextView) findViewById(R.id.visitor_team);
		homeScore = (TextView) findViewById(R.id.home_team_score);
		visitorScore = (TextView) findViewById(R.id.visitor_team_score);

		registerOnClickListeners();
	}

	/**
	 * Registers onClickListeners with anonymous inner classes
	 */
	private void registerOnClickListeners() {
		strikeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onStrikeButtonClicked();
			}
		});

		ballButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBallButtonClicked();
			}
		});

		outButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onOutButtonClicked();
			}
		});
		
		foulButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onFoulButtonClicked();
			}
		});
		
		runButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onRunButtonClicked();
			}
		});
	}

	/**
	 * Increments strikes by one, plays a sound,
	 * and displays appropriate messages. If a strike
	 * out occurs, strikes and balls are reset to 
	 * zero.
	 */
	private void onStrikeButtonClicked() {
		strikes++;
		playSound("click");
		if (strikes == 3) {
			showToast("Player Struck Out!!");
			playSound("boo");
			strikes = 0;
			balls = 0;
			onOutButtonClicked();
		}
		updateText();
	}

	/**
	 * Increments balls by one, plays a sound,
	 * and displays appropriate messages. If a walk
	 * occurs, strikes and balls are reset to 
	 * zero.
	 */
	private void onBallButtonClicked() {
		balls++;
		playSound("click");
		if (balls == 4) {
			showToast("Player Walked");
			strikes = 0;
			balls = 0;
		}
		updateText();
	}

	/**
	 * Increments outs by one, plays a sound,
	 * and displays appropriate messages. If outs
	 * reaches 3 teams are switched
	 */
	private void onOutButtonClicked() {
		outs++;
		playSound("click");
		if (outs == 3) {
			switchTeams();
		}
		updateText();
	}
	
	/**
	 * Increments stikes by one as long as
	 * strikes are less than 2, plays a sound,
	 * and displays appropriate messages.d
	 */
	private void onFoulButtonClicked() {
		playSound("click");
		showToast("Foul");
		if(strikes < 2) {
			strikes++;
		}
		updateText();
	}

	/**
	 *  Plays a sound and increments the current
	 *  team's score by one
	 */
	private void onRunButtonClicked() {
		playSound("cheer");
		currentTeam.setRuns(currentTeam.getRuns() + 1);
		updateText();
	}

	/**
	 * Updates all of the text on the screen depending
	 * upon the state of the game. Sets the title of the
	 * team at bat to bold.
	 */
	private void updateText() {
		if (isVisitorAtBat) {
			currentTeam = visitor;
			visitorTitle.setTypeface(null, Typeface.BOLD);
			visitorScore.setTypeface(null, Typeface.BOLD);
			visitorScore.setText(String.valueOf(visitor.getRuns()));
			homeScore.setTypeface(null, Typeface.NORMAL);
			homeTitle.setTypeface(null, Typeface.NORMAL);
		} else {
			currentTeam = home;
			visitorScore.setTypeface(null, Typeface.NORMAL);
			visitorTitle.setTypeface(null, Typeface.NORMAL);
			homeScore.setTypeface(null, Typeface.BOLD);
			homeScore.setText(String.valueOf(home.getRuns()));
			homeTitle.setTypeface(null, Typeface.BOLD);
		}
		strikeText.setText(String.valueOf(strikes));
		ballText.setText(String.valueOf(balls));
		outText.setText(String.valueOf(outs));
		log("Text Updated");
	}

	/**
	 * Switches the currentTeam, resets balls,
	 * strikes, and outs to zero, and 
	 * notifies the user that the teams have
	 * been switched
	 */
	private void switchTeams() {
		strikes = 0;
		balls = 0;
		outs = 0;
		showToast("  3 outs\nSwitching Teams");
		currentTeam.setBalls(balls);
		currentTeam.setOuts(outs);
		currentTeam.setStrikes(strikes);
		isVisitorAtBat = !isVisitorAtBat;
	}

	/**
	 * Used for debugging
	 * @param text The text to be displayed
	 * in the LogCat
	 */
	private void log(String text) {
		Log.i(TAG, text);
	}

	/**
	 * Used for displaying pop-up
	 * notifications
	 * @param text The string of text
	 * to display
	 */
	private void showToast(String text) {
		Toast toast = Toast.makeText(getApplicationContext(), text,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 250);
		toast.show();
	}

	/**
	 * Used for playing sounds
	 * @param sound The string value
	 * of the .mp3 stored in the
	 * assets folder
	 */
	private void playSound(String sound) {
		sound += ".mp3";
		if (mp.isPlaying()) {
			mp.stop();
		}
		try {
			mp.reset();
			AssetFileDescriptor afd;
			afd = getAssets().openFd(sound);
			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			mp.prepare();
			mp.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
