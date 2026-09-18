package main;

public class QuestionManager {
    private String currentQuestion = "";
    private String[] currentChoices = new String[2];
    private boolean active = false;

    public void startQuestions(String[] questions, String[][] answers) {
        if (questions != null && answers != null && questions.length > 0) {
            this.currentQuestion = questions[0]; // แสดงคำถามเดียวก็พอสำหรับ logic นี้
            this.currentChoices = answers[0];
            this.active = true;
        }
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public String[] getCurrentChoices() {
        return currentChoices;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
