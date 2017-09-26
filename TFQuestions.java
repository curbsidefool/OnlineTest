package onlineTest;

public class TFQuestions extends Questions{

private boolean answer;
	
	public TFQuestions(int questionNumber, String text, double points, boolean answer) {
		super(questionNumber, text, points);
		this.answer = answer;
	}
	
	public int getQuestionNumber(){
		return super.getQuestionNumber();
	}
	
	public double getPoints(){
		return super.getPoints();
	}
	
	public boolean getAnswer(){
		return this.answer;
	}
	
	public String toString(){
		if(answer==true){
		return super.toString() + "Correct Answer: True" ;
		}else
			return super.toString() + "Correct Answer: False" ;
	}
	
	public int compareTo(TFQuestions obj) {
		return super.getQuestionNumber() - obj.getQuestionNumber();
	}

	

}
