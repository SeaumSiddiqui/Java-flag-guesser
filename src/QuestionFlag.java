import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionFlag {
    private final String imgPath;
    private final String correctAnswer;
    private final List<String> answerList;

    public String getImgPath()
    {
        return imgPath;
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public List<String> getAnswerList()
    {
        return answerList;
    }

    public QuestionFlag(int index)
    {
        this.imgPath = "Resources/Flags/flag" + (index + 1) + ".png";
        this.correctAnswer = findAnswerByIndex(index);
        this.answerList = loadAnswerPool();
    }

    private String findAnswerByIndex(int index)
    {
        String names = null;
        for (int i = 0; i < CountryNameDB.countryNameDB.length; i++)
        {
            if (i == index) {
                names = CountryNameDB.countryNameDB[index];
                break;
            }
        }
        return names;
    }

    private List<String> loadAnswerPool()
    {
        List<String> answers = new ArrayList<>();

        Random rand = new Random();

        // add correct answer to the list
        answers.add(correctAnswer);

        // store rest of the answer to the list
        for (int i = 0; i < 3; i++) {
            String options = CountryNameDB.countryNameDB[rand.nextInt(CountryNameDB.countryNameDB.length)];

            while (answers.contains(options))
            {
                options = CountryNameDB.countryNameDB[rand.nextInt(CountryNameDB.countryNameDB.length)];
            }
            answers.add(options);
        }
        randomizeAnswer(answers, rand.nextInt(answers.size()));
        return answers;
    }

    private void randomizeAnswer(List<String> answersList, int randIndex) {
        if (randIndex == 0) return; // no point with switching itself

        // switch with the correct answer only as the rest is already randomize
        answersList.set(0, answersList.get(randIndex));
        answersList.set(randIndex, correctAnswer);
    }
}
