package onlineTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SystemManager implements Manager, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Double> grades = new TreeMap<String, Double>();
	private Map<String, Map<Integer, ArrayList<Double>>> studentScores = new HashMap<String, Map<Integer, ArrayList<Double>>>();
	private Map<Integer, ArrayList<Questions>> exams = new HashMap<Integer, ArrayList<Questions>>();
	private Map<Integer, String> examTitles = new HashMap<Integer, String>();

	@Override
	public boolean addExam(int examId, String title) {
		

		if (!exams.containsKey(examId)) {
			examTitles.put(examId, title);
			exams.put(examId, new ArrayList<Questions>());
			return true;
		}
		return false;

	}



	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber,
			String text, double points, boolean answer) {
		if (!exams.containsKey(examId)) {
			return;
		}
		for (int i = 0; i < exams.get(examId).size(); i++) {
			if (i == questionNumber) {
				exams.get(examId).remove(i);
				exams.get(examId).add(new TFQuestions(questionNumber, text, points, answer));
				Collections.sort(exams.get(examId));
				return;
			}
		}
		exams.get(examId).add(
				new TFQuestions(questionNumber, text, points, answer));
		Collections.sort(exams.get(examId));
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		if (!exams.containsKey(examId)) {
			return;
		}
		for (int i = 0; i < exams.get(examId).size(); i++) {
			if (i == questionNumber) {
				exams.get(examId).remove(i);
				exams.get(examId).add(new MultipleChoiceQuestions(questionNumber, text, points, answer));
				Collections.sort(exams.get(examId));
				return;
			}
		}
		exams.get(examId).add(new MultipleChoiceQuestions(questionNumber, text, points,answer));
		Collections.sort(exams.get(examId));

	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		if (!exams.containsKey(examId)) {
			return;
		}
		for (int i = 0; i < exams.get(examId).size(); i++) {
			if (i == questionNumber) {
				exams.get(examId).remove(i);
				exams.get(examId).add(new FillInTheBlankQuestions(questionNumber, text, points,	answer));
				Collections.sort(exams.get(examId));
				return;
			}
		}
		exams.get(examId).add(new FillInTheBlankQuestions(questionNumber, text, points, answer));
		Collections.sort(exams.get(examId));

	}

	@Override
	public String getKey(int examId) {
		if (!exams.containsKey(examId)) {
			return "Exam not found";
		}
		String key = "";
		for (int i = 0; i < exams.get(examId).size(); i++) {
			key += exams.get(examId).get(i).toString() + "\n";
		}
		return key;

	}

	@Override
	public boolean addStudent(String name) {
		if (!studentScores.containsKey(name)) {
			studentScores.put(name, new HashMap<Integer, ArrayList<Double>>());
			return true;
		}
		return false;
	
	} 




	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		if (!studentScores.get(studentName).containsKey(examId)) {
			studentScores.get(studentName).put(examId, new ArrayList<Double>());
		}
		for (int i = 0; i < exams.get(examId).size(); i++) {
			Questions q = (Questions) exams.get(examId).get(i);
			if (q.getQuestionNumber() == questionNumber) {
				TFQuestions tfQ = (TFQuestions) q;
				if (answer == tfQ.getAnswer()) {
					studentScores.get(studentName).get(examId)
					.add(tfQ.getPoints());
				} else if (answer != tfQ.getAnswer()) {
					studentScores.get(studentName).get(examId).add(0.0);
				}
			}
		}
		
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {

		if (!studentScores.get(studentName).containsKey(examId)) {
			studentScores.get(studentName).put(examId, new ArrayList<Double>());
		}
		ArrayList<String> examKey = new ArrayList<String>();
		List<String> studentKey = Arrays.asList(answer);
		double points = 0.0;

		for (int i = 0; i < exams.get(examId).size(); i++) {
			Questions temp = (Questions) exams.get(examId).get(i);
			if (questionNumber == temp.getQuestionNumber()) {
				MultipleChoiceQuestions q = (MultipleChoiceQuestions) temp;
				points = q.getPoints();
				for (int j = 0; j < q.getAnswer().size(); j++) {
					examKey.add(q.getAnswer().get(j));
				}
			}
		}
		if (examKey.size() != studentKey.size()) {
			studentScores.get(studentName).get(examId).add(0.0);
			return;
		}
		for (int i = 0; i < examKey.size(); i++) {
			if (!studentKey.contains(examKey.get(i))) {
				studentScores.get(studentName).get(examId).add(0.0);
				return;
			}
		}
		studentScores.get(studentName).get(examId).add(points);
		
	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		if (!studentScores.get(studentName).containsKey(examId)) {
			studentScores.get(studentName).put(examId, new ArrayList<Double>());
		}
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> studentKey = new ArrayList<String>();
		double score = 0.0;
		double finalScore = 0.0;
		for (int i = 0; i < answer.length; i++) {
			studentKey.add(answer[i]);
		}

		for (int i = 0; i < exams.get(examId).size(); i++) {
			Questions q = (Questions) exams.get(examId).get(i);
			if (questionNumber == q.getQuestionNumber()) {
				FillInTheBlankQuestions fbQ = (FillInTheBlankQuestions) q;
				score = fbQ.getPoints() / fbQ.getAnswer().size();
				for (int j = 0; j < fbQ.getAnswer().size(); j++) {
					list.add(fbQ.getAnswer().get(j));
				}
				break;
			}
		}
		for (int i = 0; i < studentKey.size(); i++) {
			if (list.contains(studentKey.get(i))) {
				finalScore += score;
			}
		}
		studentScores.get(studentName).get(examId).add(finalScore);
		
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		double score = 0.0;
		for (int i = 0; i < studentScores.get(studentName).get(examId).size(); i++) {
			score += studentScores.get(studentName).get(examId).get(i);
		}

		return score;
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		String report = "";
		double studentScore = 0.0;
		double totalScore = 0.0;
		int counter = 1;

		for (int i = 0; i < studentScores.get(studentName).get(examId).size(); i++) {
			studentScore += studentScores.get(studentName).get(examId).get(i);
			totalScore += exams.get(examId).get(i).getPoints();
			report += "Question #" + counter++ + " "
					+ studentScores.get(studentName).get(examId).get(i);
			report += " points out of " + exams.get(examId).get(i).getPoints();
			report += "\n";
		}
		report += "Final Score: " + studentScore + " out of " + totalScore;
		return report;
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		for (int i = 0; i < letterGrades.length; i++) {
			grades.put(letterGrades[i], cutoffs[i]);
		}

	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		double raw = 0.0;
		double examTotal = 0.0;

		for (Integer e : studentScores.get(studentName).keySet()) {
			for(int i = 0; i < exams.get(e).size(); i++){
				examTotal += exams.get(e).get(i).getPoints(); 
			}
			raw +=(getExamScore(studentName, e)/examTotal);
			examTotal = 0;
		}
		return raw *100 / exams.size();
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		String grade = "";
		for (String g : grades.keySet()) {
			if (grades.get(g) <= getCourseNumericGrade(studentName)) {
				return g;
			}
		}
		return grade;
	}

	@Override
	public String getCourseGrades() {
		String grades = "";
		ArrayList<String> list = new ArrayList<String>();
		for (String student : studentScores.keySet()) {
			list.add(student);
		}
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			grades += list.get(i) + " " + getCourseNumericGrade(list.get(i))
			+ "" + getCourseLetterGrade(list.get(i)) + "\n";
		}
		return grades;
	}

	@Override
	public double getMaxScore(int examId) {
		double currentStudentScore = 0.0;
		ArrayList<Double> allScores = new ArrayList<Double>();

		for (String student : studentScores.keySet()) {
			for (int k = 0; k < studentScores.get(student).get(examId).size(); k++) {
				currentStudentScore += studentScores.get(student).get(examId).get(k);
			}
			allScores.add(currentStudentScore);
			currentStudentScore = 0;
		}
		return Collections.max(allScores);
	}

	@Override
	public double getMinScore(int examId) {
		double currentStudentScore = 0.0;
		ArrayList<Double> allScores = new ArrayList<Double>();

		for (String student : studentScores.keySet()) {
			for (int k = 0; k < studentScores.get(student).get(examId).size(); k++) {
				currentStudentScore += studentScores.get(student).get(examId).get(k);
			}
			allScores.add(currentStudentScore);
			currentStudentScore = 0;
		}
		return Collections.min(allScores);
	}

	@Override
	public double getAverageScore(int examId) {
		double average = 0.0;
		int studentCounter = 0;

		for (String student : studentScores.keySet()) {
			studentCounter++;
			for (int k = 0; k < studentScores.get(student).get(examId).size(); k++) {
				average += studentScores.get(student).get(examId).get(k);
			}
		}
		average = average / studentCounter;
		return average;
	}


	@Override
	public void saveManager(Manager manager,  java.lang.String fileName) {	
		try{
			FileOutputStream f = new FileOutputStream(fileName);
			BufferedOutputStream b = new BufferedOutputStream(f);
			ObjectOutputStream o = new ObjectOutputStream(b);   
			o.writeObject(manager);
			o.close();
			
		}  
		catch(IOException e){
		}

	}

	@Override
	public Manager restoreManager(String fileName) {
		Manager value = null;
		try {
			FileInputStream f = new FileInputStream(fileName);
	        BufferedInputStream b = new BufferedInputStream(f);
	        ObjectInputStream o = new ObjectInputStream(b);
	        Manager manager = (Manager) o.readObject();
	        o.close();
	        value = manager;
			
		}catch(IOException | ClassNotFoundException e){
			
		}
		return value;
	}



}
