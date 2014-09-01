package au.edu.uow.laptimer.model;

import java.util.ArrayList;

public class LTModel {
	private ArrayList<LTChallenge> challenges;

	public LTModel() {
		super();
		this.challenges = new ArrayList<LTChallenge>();
	}

	public void addChallenge(String challengeName) {
		this.challenges.add(new LTChallenge(challengeName));
	}

	public LTChallenge challengeAtIndex(Integer index) {
		return this.challenges.get(index);
	}

	public Integer numberOfChallenges() {
		return this.challenges.size();
	}

	public ArrayList<String> getChallengeNames() {
		ArrayList<String> challengeNames = new ArrayList<String>();
		for (LTChallenge challenge : challenges) {
			challengeNames.add(challenge.getChallengeName());
		}
		return challengeNames;
	}
}
