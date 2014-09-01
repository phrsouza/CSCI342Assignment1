/**
 * 
 */
package au.edu.uow.laptimer.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import au.edu.uow.laptimer.R;
import au.edu.uow.laptimer.controller.activity.TimerActivity;
import au.edu.uow.laptimer.model.LTModel;

/**
 * @author Pedro Henrique Ramos Souza
 * 
 */
public class ChallengesFragment extends android.app.Fragment {
	private LTModel lapTimeModel;
	private ListView listViewChallenges;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_challenges, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().setTitle(R.string.challenges);
		getReferences();
		lapTimeModel = ((TimerActivity) getActivity()).getModel();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				lapTimeModel.getChallengeNames());

		listViewChallenges.setAdapter(adapter);

		listViewChallenges
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						//getActivity()
						
						Toast.makeText(
								getActivity().getApplicationContext(),
								"Name : "
										+ listViewChallenges
												.getItemAtPosition(position),
								Toast.LENGTH_LONG).show();
					}
				});
	}

	private void getReferences() {
		listViewChallenges = (ListView) getView().findViewById(
				R.id.listViewChallenges);
	}

}
