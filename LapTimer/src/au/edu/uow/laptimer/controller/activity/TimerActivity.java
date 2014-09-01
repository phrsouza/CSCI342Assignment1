package au.edu.uow.laptimer.controller.activity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import au.edu.uow.laptimer.R;
import au.edu.uow.laptimer.R.layout;
import au.edu.uow.laptimer.controller.fragment.ChallengesFragment;
import au.edu.uow.laptimer.controller.fragment.TimerFragment;
import au.edu.uow.laptimer.model.LTChallenge;
import au.edu.uow.laptimer.model.LTModel;
import au.edu.uow.laptimer.model.LTTime;

public class TimerActivity extends Activity{
	private FragmentManager manager;
	private LTModel lapTimerModel;
	private ArrayList<LTChallenge> challenges;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		//Instantiating the challenges array in the onCreate method
		lapTimerModel = new LTModel();
		
		//Adding new Challenges
		lapTimerModel.addChallenge("Clap 20 Times Challenge");
		lapTimerModel.addChallenge("Say the Alphabet Challenge");
		lapTimerModel.addChallenge("100 Meter Sprint Challenge");
		lapTimerModel.addChallenge("Read the csci342 A1 spec challenge");
		
		
		// Adding a fragment to the mainLinearLayout view in the TimerActivity.
		manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.mainLinearLayout, new ChallengesFragment(), "challenges");
		transaction.commit();
		
////		 Adding a fragment to the mainLinearLayout view in the TimerActivity.
//		manager = getFragmentManager();
//		FragmentTransaction transaction = manager.beginTransaction();
//		transaction.add(R.id.mainLinearLayout, new TimerFragment(), "timer");
//		transaction.commit();

	}

	public ArrayList<String> getChallengeNames(){
		ArrayList<String> challengeNames = new ArrayList<String>();
		for (LTChallenge challenge: this.challenges) {
			challengeNames.add(challenge.getChallengeName());
		}
		
		return challengeNames;
	}
	
	public LTModel getModel(){
		return this.lapTimerModel;
	}
	
	public void setActionBarTitle(String title) {
		setTitle(title);
	}

}
