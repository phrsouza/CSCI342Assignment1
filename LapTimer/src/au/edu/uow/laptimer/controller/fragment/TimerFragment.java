/**
 * 
 */
package au.edu.uow.laptimer.controller.fragment;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import au.edu.uow.laptimer.R;
import au.edu.uow.laptimer.controller.activity.TimerActivity;
import au.edu.uow.laptimer.model.LTChallenge;
import au.edu.uow.laptimer.model.LTTime;

/**
 * @author Pedro Henrique Ramos Souza
 * 
 */

public class TimerFragment extends Fragment {
	private LTChallenge challenge;
	private long begin, end;
	private Handler handler;
	
	// Views of the interface
	public TextView time;
	public Button button;

	public TextView textBest;
	public LinearLayout barBest;

	public TextView textWorst;
	public LinearLayout barWorst;

	public TextView textAverage;
	public LinearLayout barAverage;

	public TextView textCurrent;
	public LinearLayout barCurrent;
	
	//Communicator
	public interface TimerFragmentListener {
		
		void onViewTimes(int position);
		
	}
	
	TimerFragmentListener listener;

	EditText editText;

	// Function that updates the UI Views
	Runnable updateIU = new Runnable() {

		@Override
		public void run() {
			try {

				handler.postDelayed(updateIU, 1);
				end = System.currentTimeMillis();
				time.setText(LTTime.parseTimeToString(end - begin));
				textCurrent.setText(getString(R.string.current)
						+ LTTime.parseTimeToString(System.currentTimeMillis()
								- begin));
				setBars();
			} catch (Exception e) {
				// To avoid the program to crash when the chronometer is running
				// and the user press back button
				handler.removeCallbacks(updateIU);
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		// Gets the selected challenge on the main Model based on the position
		// arg passar inside the Bundle
		challenge = ((TimerActivity) getActivity()).getModel()
				.challengeAtIndex(getArguments().getInt("position"));
		return inflater.inflate(R.layout.fragment_timer, container, false);

	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (TimerFragmentListener) activity;
		} catch (ClassCastException e) { // The app should crash if we don't
											// implement this interface in the
											// Activity.
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentListener");
		}
	}

	// Set the visibility of the buttons in the ActionBar
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_timer_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
		menu.findItem(R.id.view_recorded).setVisible(true);
		menu.findItem(R.id.new_challenge).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.view_recorded:
		//	Log.v("teste", "View Recorded in Timer");
			int position = getArguments().getInt("position");
			listener.onViewTimes(position);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().setTitle(challenge.getChallengeName());

		begin = 0;
		getReferences();
		eventRegister();
		resetBars();

	}

	public void getReferences() {
		time = (TextView) getView().findViewById(R.id.time);
		button = (Button) getView().findViewById(R.id.button1);

		textBest = (TextView) getView().findViewById(R.id.textViewBest);
		barBest = (LinearLayout) getView().findViewById(R.id.barBest);

		textWorst = (TextView) getView().findViewById(R.id.textViewWorst);
		barWorst = (LinearLayout) getView().findViewById(R.id.barWorst);

		textAverage = (TextView) getView().findViewById(R.id.textViewAverage);
		barAverage = (LinearLayout) getView().findViewById(R.id.barAverage);

		textCurrent = (TextView) getView().findViewById(R.id.textViewCurrent);
		barCurrent = (LinearLayout) getView().findViewById(R.id.barCurrent);
	}

	public void eventRegister() {
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (button.getText().equals(getString(R.string.start))) {
					startChronometer();
					button.setText(getString(R.string.stop));
				} else if (button.getText().equals(getString(R.string.stop))) {
					stopChronometer();
					button.setText(getString(R.string.clear));
				} else if (button.getText().equals(getString(R.string.clear))) {
					clearChronometer();
					button.setText(getString(R.string.start));
				}
			}
		});

	}

	public void resetBars() {
		LinearLayout.LayoutParams layoutParams;
		LTTime best, worst, average;
		long current;
		float ratio;
		if (challenge.getTimes().size() != 0) {
			best = challenge.getBestTime();
			worst = challenge.getWorstTime();
			average = challenge.average();
			current = begin == 0 ? 0 : (System.currentTimeMillis() - begin);
			textBest.setText("Best: "
					+ LTTime.parseTimeToString(best.getTime()) + " - "
					+ best.getComment());
			textWorst.setText("Worst: "
					+ LTTime.parseTimeToString(worst.getTime()) + " - "
					+ worst.getComment());
			textAverage.setText("Average: "
					+ LTTime.parseTimeToString(average.getTime()));
			textCurrent.setText(getString(R.string.current)
					+ LTTime.parseTimeToString(0));

			ratio = (100f / (float) worst.getTime());
			// Set the bar sizes based in the worst value;
			layoutParams = (LinearLayout.LayoutParams) barBest
					.getLayoutParams();
			layoutParams.weight = best.getTime() * ratio;
			barBest.setLayoutParams(layoutParams);

			layoutParams = (LinearLayout.LayoutParams) barWorst
					.getLayoutParams();
			layoutParams.weight = ratio * worst.getTime();
			barWorst.setLayoutParams(layoutParams);

			layoutParams = (LinearLayout.LayoutParams) barAverage
					.getLayoutParams();
			layoutParams.weight = average.getTime() * ratio;
			barAverage.setLayoutParams(layoutParams);

		}

		textCurrent.setText("Curret: " + getString(R.string.initial_time));
		layoutParams = (LinearLayout.LayoutParams) barCurrent.getLayoutParams();
		layoutParams.weight = 0;
		barCurrent.setLayoutParams(layoutParams);
	}

	public void setBars() {
		// Time not running
		LinearLayout.LayoutParams layoutParams;
		LTTime best, worst, average;
		long current;
		float ratio;

		if (challenge.getTimes().size() != 0) {
			best = challenge.getBestTime();
			worst = challenge.getWorstTime();
			average = challenge.average();
			current = (end - begin);
		} else {
			best = new LTTime(0L);
			worst = new LTTime(0L);
			average = new LTTime(0L);
			current = 1;
		}

		// Check who is bigger: Worst time or Current Time
		if (worst.getTime() >= current) {
			ratio = (100f / (float) worst.getTime());
			// Set the bar sizes based in the worst value;

			// The current is less than the worst time, then adjust the current
			// time
			layoutParams = (LinearLayout.LayoutParams) barCurrent
					.getLayoutParams();
			layoutParams.weight = current * ratio;
			barCurrent.setLayoutParams(layoutParams);
		} else {
			// Current is bigger than worst: scale down the other bars
			ratio = (100f / (float) current);
			// Set the bar sizes based in the current value;
			layoutParams = (LinearLayout.LayoutParams) barCurrent
					.getLayoutParams();
			layoutParams.weight = current * ratio;
			barCurrent.setLayoutParams(layoutParams);

			layoutParams = (LinearLayout.LayoutParams) barBest
					.getLayoutParams();
			layoutParams.weight = best.getTime() * ratio;
			barBest.setLayoutParams(layoutParams);

			layoutParams = (LinearLayout.LayoutParams) barWorst
					.getLayoutParams();
			layoutParams.weight = ratio * worst.getTime();
			barWorst.setLayoutParams(layoutParams);

			layoutParams = (LinearLayout.LayoutParams) barAverage
					.getLayoutParams();
			layoutParams.weight = average.getTime() * ratio;
			barAverage.setLayoutParams(layoutParams);
		}
	}

	public void startChronometer() {
		begin = System.currentTimeMillis();
		handler = new Handler();
		handler.postDelayed(updateIU, 100);

	}

	public void stopChronometer() {
		handler.removeCallbacks(updateIU);
		end = System.currentTimeMillis();
		showDialog();
	}

	void showDialog() {
		// Create and show the alert.
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Save Time");
		alert.setMessage("Please enter an optional comment");

		// Set an EditText view to get user input
		editText = new EditText(getActivity());
		alert.setView(editText);

		alert.setPositiveButton(R.string.save,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String comment = editText.getText().toString();
						saveTime(comment);
					}
				});

		alert.setNegativeButton(R.string.discard,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();
	}

	public void saveTime(String comment) {
		this.challenge.addTime(end - begin, new Date(), comment);
	}

	public void clearChronometer() {
		begin = 0;
		resetBars();
		time.setText(getString(R.string.initial_time));
	}
}
