package com.example.nubilityanimation.Modal;

public class Quiz_Question {
    private String  quiz_id , Quiz_question,Option_1,Option_2,Option_3,Correct_Answer;
    public Quiz_Question()
    {

    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public Quiz_Question(String quiz_id, String quiz_question, String option_1, String option_2, String option_3, String correct_Answer) {
        this.quiz_id = quiz_id;
        Quiz_question = quiz_question;
        Option_1 = option_1;
        Option_2 = option_2;
        Option_3 = option_3;
        Correct_Answer = correct_Answer;
    }

    public String getQuiz_question() {
        return Quiz_question;
    }

    public void setQuiz_question(String quiz_question) {
        Quiz_question = quiz_question;
    }

    public String getOption_1() {
        return Option_1;
    }

    public void setOption_1(String option_1) {
        Option_1 = option_1;
    }

    public String getOption_2() {
        return Option_2;
    }

    public void setOption_2(String option_2) {
        Option_2 = option_2;
    }

    public String getOption_3() {
        return Option_3;
    }

    public void setOption_3(String option_3) {
        Option_3 = option_3;
    }

    public String getCorrect_Answer() {
        return Correct_Answer;
    }

    public void setCorrect_Answer(String correct_Answer) {
        Correct_Answer = correct_Answer;
    }
}
