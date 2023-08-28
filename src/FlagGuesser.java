import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlagGuesser extends JFrame implements ActionListener {
    private static final int QUESTION_AMOUNT = 15;
    private List<QuestionFlag> questions;
    private List<JButton> optionButtons;
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
        springLayout.putConstraint(SpringLayout.NORTH, currentFlagImage, 150, SpringLayout.NORTH, panel);

        //4. Answer Buttons
        JPanel answerPanel = loadButtons();
        panel.add(answerPanel);

        springLayout.putConstraint(SpringLayout.WEST, answerPanel, 80, SpringLayout.WEST, panel);
        springLayout.putConstraint(SpringLayout.NORTH, answerPanel, 400, SpringLayout.NORTH, panel);

        //5. Reset Button
        JButton resetButton = ImageService.createImageButton("Resources/resetButton.png", "Reset");

        assert resetButton != null;
        resetButton.setToolTipText("Reset Quiz");
        resetButton.setPreferredSize(new Dimension(60, 50));
        resetButton.addActionListener(this);
        resetButton.setOpaque(false);
        resetButton.setBorderPainted(false);
        resetButton.setBackground(null);
        resetButton.setFocusable(false);

        panel.add(resetButton);

        springLayout.putConstraint(SpringLayout.WEST, resetButton, 15, SpringLayout.WEST, panel);
        springLayout.putConstraint(SpringLayout.NORTH, resetButton, 50, SpringLayout.NORTH, panel);


        //6. Next Button
        this.getContentPane().add(panel);
    }

    private JPanel loadButtons()
    {
        JPanel buttonPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(2, 2);
        buttonPanel.setLayout(gridLayout);
        buttonPanel.setBackground(Color.BLACK);
        gridLayout.setHgap(25);
        gridLayout.setVgap(25);

        optionButtons = new ArrayList<>();

        for (int i = 0; i < 4; i++)
        {
            JButton button = new JButton(currentFlag.getAnswerList().get(i));
            button.addActionListener(this);
            button.setFocusable(false);
            button.setBackground(null);
            button.setFont(new Font("Dialog", Font.BOLD, 18));
            button.setPreferredSize(new Dimension(300, 100));

            optionButtons.add(button);
            buttonPanel.add(optionButtons.get(i));
        }
        return buttonPanel;
    }

    private void loadQuestion() {
        questions = new ArrayList<QuestionFlag>();

        // create and store the flag questions
        for (int i = 0; i < QUESTION_AMOUNT; i++) {
            questions.add(new QuestionFlag(i));
        }

        // randomize questions
        for (int i = 0; i < QUESTION_AMOUNT; i++) {
            int randIndex = new Random().nextInt(QUESTION_AMOUNT);
            QuestionFlag temp = questions.get(i);
            questions.set(i, questions.get(randIndex));
            questions.set(randIndex, temp);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Reset"))
        {
            // reset score
            score = 0;

            // reset question number
            currentFlagNumber = 0;

            // load new batch of question
            loadQuestion();

            // update GUI
            updateGUI();
        }
        else if (command.equals("next"))
        {
            nextButton.setVisible(false);
            firstChoice = false;

            // go to the next question
            currentFlagNumber ++;

            // update GUI
            updateGUI();
        }
        else{
            // option button
            JButton button = (JButton) e.getSource();

            if (currentFlag.getCorrectAnswer().equals(command))
            {
                // indicate correct answer
                button.setBackground(Color.GREEN);

                // update score
                if (!firstChoice)
                {
                    ++score;
                }
            }
            else {
                button.setBackground(Color.RED);
            }

            // set next button
            if (currentFlagNumber < QUESTION_AMOUNT - 1 && !nextButton.isVisible())
            {
                nextButton.setVisible(true);
            }
            firstChoice = true;
        }
        if (firstChoice && currentFlagNumber == QUESTION_AMOUNT - 1)
        {
            launchFinishedDialog();
        }
    }

    private void updateGUI() {
        // reset color of option buttons
        for (int i = 0; i < optionButtons.size(); i++)
        {
            optionButtons.get(i).setBackground(null);
        }

        //update question number
        flagNumberLabel.setText((currentFlagNumber + 1) + ".");

        //update flag image and answer
        currentFlag = questions.get(currentFlagNumber);
        ImageService.updateImage(currentFlagImage, currentFlag.getImgPath(), true, 350, 225);

        for (int i = 0; i < 4; i++)
        {
            optionButtons.get(i).setText(currentFlag.getAnswerList().get(i));

        }
    }

    private void launchFinishedDialog() {

    }
}
