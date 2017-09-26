package onlineTest;

public class Questions implements Comparable<Questions>{

	private int questionNumber;
	private double points;
	private String text;

	public Questions(int questionNumber, String text, double points) {
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;
	}

	public int getQuestionNumber() {
		return this.questionNumber;
	}

	public double getPoints() {
		return this.points;
	}

	public String toString() {
		String results = "";
		results = "Question Text: " + this.text + "\n";
		results += "Points: " + this.points + "\n";
		return results;
	}

	public int compareTo(Questions obj) {
		return this.questionNumber - obj.questionNumber;
	}
}
