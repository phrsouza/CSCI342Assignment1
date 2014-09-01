/**
 * 
 */
package au.edu.uow.laptimer.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import au.edu.uow.laptimer.R;
import au.edu.uow.laptimer.controller.activity.TimerActivity;
import au.edu.uow.laptimer.model.LTModel;

/**
 * @author Pedro Henrique Ramos Souza
 * 
 */
public class ChallengesFragment extends Fragment {
	private LTModel lapTimeModel;
	private ListView listViewChallenges;
	private EditText dialogEditText;

	public interface FragmentListener {

		void onItemPressed(int position);

	}

	private FragmentListener listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (FragmentListener) activity;
		} catch (ClassCastException e) { // The app should crash if we don't
											// implement this interface in the
											// Activity.
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);

		return inflater.inflate(R.layout.fragment_challenges, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().setTitle(R.string.challenges);
		getReferences();
		updateUi();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (lapTimeModel.numberOfChallenges() == 0) {
			newChallengeDialog();
		}
	}

	public void updateUi() {
		lapTimeModel = ((TimerActivity) getActivity()).getModel();
		Log.v("teste", "Updating UI");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				lapTimeModel.getChallengeNames());

		listViewChallenges.setAdapter(adapter);

		listViewChallenges
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// getActivity()
						listener.onItemPressed(position);

					}
				});
	}

	// Set the visibility of the buttons in the ActionBar
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_timer_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
		menu.findItem(R.id.view_recorded).setVisible(false);
		menu.findItem(R.id.new_challenge).setVisible(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.new_challenge:
			// Log.v("teste", "New Pressed in Challenges");
			newChallengeDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void newChallengeDialog() {
		// Create and show the alert.
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Save Challenge");
		alert.setMessage("Please enter the Challenge Name");

		// Set an EditText view to get user input
		dialogEditText = new EditText(getActivity());
		alert.setView(dialogEditText);

		alert.setPositiveButton(R.string.save,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String challengeName = dialogEditText.getText()
								.toString();
						if (!challengeName.isEmpty()) {
							lapTimeModel.addChallenge(challengeName);
							updateUi();
						}
					}
				});

		alert.setNegativeButton(R.string.discard,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();
	}

	private void getReferences() {
		listViewChallenges = (ListView) getView().findViewById(
				R.id.listViewChallenges);
	}

}



