import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlagGuesser extends JFrame {
    private static final int QUESTION_AMOUNT = 15;
    private List<QuestionFlag> questions;
    private QuestionFlag currentFlag;
    private final SpringLayout springLayout;
    private JLabel flagNumberLabel, currentFlagImage;
    private JButton nextButton;
    private boolean firstChoice;
    int currentFlagNumber, score;

    public FlagGuesser()
    {
        super("Flag Guesser");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 700));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        loadQuestion();

        //init vars
        score = 0;
        currentFlagNumber = 0;
        firstChoice = false;
        springLayout = new SpringLayout();
        currentFlag = questions.get(currentFlagNumber);

        addGuiComponents();
    }

    private void addGuiComponents()
    {
        JPanel panel = new JPanel();
        panel.setLayout(springLayout);
        panel.setBackground(Color.BLACK);

        //1. Banner Image
        JLabel bannerImg = ImageService.loadImage("Resources/Banner.png", true, 600, 100);
        panel.add(bannerImg);

        springLayout.putConstraint(SpringLayout.WEST, bannerImg, 100, SpringLayout.WEST, panel);
        springLayout.putConstraint(SpringLayout.NORTH, bannerImg, 25, SpringLayout.NORTH, panel);

        //2. Number Label
        flagNumberLabel = new JLabel((currentFlagNumber + 1) + ".");
        flagNumberLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        panel.add(flagNumberLabel);

        springLayout.putConstraint(SpringLayout.WEST, flagNumberLabel, 100, SpringLayout.WEST, panel);
        springLayout.putConstraint(SpringLayout.NORTH, flagNumberLabel, 175, SpringLayout.NORTH, panel);

        //3. Flag Image
        currentFlagImage = ImageService.loadImage(currentFlag.getImgPath(), true, 350, 225);
        panel.add(currentFlagImage);

        springLayout.putConstraint(SpringLayout.WEST, currentFlagImage, 230, SpringLayout.WEST, panel);
        springLayout.putConstraint(SpringLayout.NORTH, currentFlagImage, 175, SpringLayout.NORTH, panel);

        //4. Answer Button

        //5. Reset Button

        //6. Next Button
        this.getContentPane().add(panel);
    }

    private void loadQuestion()
    {
        questions = new ArrayList<QuestionFlag>();

        // create and store the flag questions
        for (int i = 0; i < QUESTION_AMOUNT; i++)
        {
            questions.add(new QuestionFlag(i));
        }

        // randomize questions
        for (int i = 0; i<QUESTION_AMOUNT; i++)
        {
            int randIndex = new Random().nextInt(QUESTION_AMOUNT);
            QuestionFlag temp = questions.get(i);
            questions.set(i, questions.get(randIndex));
            questions.set(randIndex, temp);
        }

    }
}
