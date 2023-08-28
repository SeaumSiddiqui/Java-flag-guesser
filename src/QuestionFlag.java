import java.util.List;

public class QuestionFlag {
    private String imgPath;
    private String correctAnswer;
    private List<String> answerList;

    public String getImgPath() {
        return imgPath;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public QuestionFlag(int index){
        this.imgPath = "Resources/Flags/flag" + (index + 1) + ".png";
        this.correctAnswer = findAnswerByIndex(index);
        this.answerList = loadAnswerPool();
    }

    private String findAnswerByIndex(int index){
        return null;
    }

    private List<String> loadAnswerPool() {
        return null;
    }
}
