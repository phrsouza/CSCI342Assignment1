package au.edu.uow.laptimer.controller.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import au.edu.uow.laptimer.R;
import au.edu.uow.laptimer.controller.activity.TimerActivity;
import au.edu.uow.laptimer.customrow.CustomRow;
import au.edu.uow.laptimer.customrow.CustomRowAdapter;
import au.edu.uow.laptimer.model.LTChallenge;
import au.edu.uow.laptimer.model.LTTime;

public class TimesFragment extends Fragment {
	private LTChallenge challenge;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		challenge = ((TimerActivity) getActivity()).getModel()
				.challengeAtIndex(getArguments().getInt("position"));

		getActivity().setTitle(challenge.getChallengeName());
		return inflater.inflate(R.layout.fragment_times, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().setTitle(challenge.getChallengeName());
		updateUI();
	}

	public void updateUI() {
		ArrayList<CustomRow> customRows = new ArrayList<CustomRow>();

		for (LTTime time : challenge.getTimes()) {
			CustomRow customRow = new CustomRow(LTTime.parseTimeToString(time
					.getTime()), time.getComment(),
					LTTime.parseDateToString(time.getDateRecord()));

			customRows.add(customRow);
		}

		CustomRowAdapter customRowAdapter = new CustomRowAdapter(getActivity(),
				customRows);

		ListView listView = (ListView) getActivity().findViewById(R.id.times_list_view);
		listView.setAdapter(customRowAdapter);
		
	}
}
