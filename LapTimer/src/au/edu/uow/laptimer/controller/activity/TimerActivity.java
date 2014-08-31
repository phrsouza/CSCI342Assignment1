package au.edu.uow.laptimer.controller.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import au.edu.uow.laptimer.R;
import au.edu.uow.laptimer.R.layout;
import au.edu.uow.laptimer.controller.fragment.TimerFragment;

public class TimerActivity extends Activity{
	private FragmentManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);

		// Adding a fragment to the mainLinearLayout view in the TimerActivity.
		manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.mainLinearLayout, new TimerFragment(), "timer");
		transaction.commit();

	}

	public void setActionBarTitle(String title) {
		setTitle(title);
	}

//	// Communicator Pattern
//	@Override
//	public void respond(String data) {
//		TimerFragment timerFragment = (TimerFragment) manager
//				.findFragmentById(R.id.fragment_timer);
//		Log.v("teste", "Achou o timer");
//		timerFragment.saveTime(data);
//	}
}
