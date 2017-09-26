package onlineTest;

import java.util.ArrayList;
import java.util.Collections;

public class FillInTheBlankQuestions extends Questions {

	protected String[] answer;
	
	public FillInTheBlankQuestions(int questionNumber, String text, double points, String[] answer) {
		super(questionNumber, text, points);
		this.answer=answer;
	}
	
	public int getQuestionNumber(){
		return super.getQuestionNumber();
	}
	
	public double getPoints(){
		return super.getPoints();
	}
	
	public ArrayList<String> getAnswer() {
		ArrayList<String> answ = new ArrayList<String>();
		for (int i = 0; i < answer.length; i++) {
			answ.add(answer[i]);
		}
		return answ;
	}
	
	public String toString(){
		ArrayList<String> answ = new ArrayList<String>();
		for(int i =0; i < answer.length; i++){
			answ.add(answer[i]);
		}
		Collections.sort(answ);
		return super.toString() + "Correct Answer: " + answ;
	}
	
	public int compareTo(FillInTheBlankQuestions q) {
		return super.getQuestionNumber() - q.getQuestionNumber();
	}


}
