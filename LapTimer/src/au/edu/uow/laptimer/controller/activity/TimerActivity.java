package au.edu.uow.laptimer.controller.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import au.edu.uow.laptimer.R;
import au.edu.uow.laptimer.controller.fragment.ChallengesFragment;
import au.edu.uow.laptimer.controller.fragment.ChallengesFragment.FragmentListener;
import au.edu.uow.laptimer.controller.fragment.TimerFragment;
import au.edu.uow.laptimer.controller.fragment.TimerFragment.TimerFragmentListener;
import au.edu.uow.laptimer.controller.fragment.TimesFragment;
import au.edu.uow.laptimer.model.LTChallenge;
import au.edu.uow.laptimer.model.LTModel;

public class TimerActivity extends Activity implements FragmentListener,
		TimerFragmentListener {
	private FragmentManager manager;
	private LTModel lapTimerModel;
	private ArrayList<LTChallenge> challenges;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		// Instantiating the challenges array in the onCreate method
		lapTimerModel = new LTModel();
		manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.mainLinearLayout, new ChallengesFragment(),
				"challenges");
		transaction.commit();

	}

	@Override
	public void onItemPressed(int position) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		TimerFragment timerFragment = new TimerFragment();
		Bundle bundle = new Bundle();
		// Passing position in a bundle
		bundle.putInt("position", position);
		timerFragment.setArguments(bundle);

		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.replace(R.id.mainLinearLayout, timerFragment);
		fragmentTransaction.commit();

	}

	@Override
	public void onViewTimes(int position) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		TimesFragment timesFragment = new TimesFragment();
		Bundle bundle = new Bundle();
		// Passing position in a bundle
		bundle.putInt("position", position);
		timesFragment.setArguments(bundle);

		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.replace(R.id.mainLinearLayout, timesFragment);
		fragmentTransaction.commit();
	}

	public ArrayList<String> getChallengeNames() {
		ArrayList<String> challengeNames = new ArrayList<String>();
		for (LTChallenge challenge : this.challenges) {
			challengeNames.add(challenge.getChallengeName());
		}

		return challengeNames;
	}

	public LTModel getModel() {
		return this.lapTimerModel;
	}

	public void setActionBarTitle(String title) {
		setTitle(title);
	}

}
