package onlineTest;

import java.util.ArrayList;
import java.util.Collections;

public class MultipleChoiceQuestions extends Questions {

	protected String[] answer;

	public MultipleChoiceQuestions(int questionNumber, String text,
			double points, String[] answer) {
		super(questionNumber, text, points);
		this.answer = answer;
	}

	public int getQuestionNumber() {
		return super.getQuestionNumber();
	}

	public double getPoints() {
		return super.getPoints();
	}

	public ArrayList<String> getAnswer() {
		ArrayList<String> answ = new ArrayList<String>();
		for (int i = 0; i < answer.length; i++) {
			answ.add(answer[i]);
		}
		return answ;
	}

	public String toString() {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < answer.length; i++) {
			temp.add(answer[i]);
		}
		Collections.sort(temp);
		return super.toString() + "Correct Answer: " + temp;
	}

	public int compareTo(MultipleChoiceQuestions obj) {
		return super.getQuestionNumber() - obj.getQuestionNumber();
	}

}

