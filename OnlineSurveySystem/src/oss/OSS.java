package oss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// QuestionAnswer class to store question and answer pairs
class QuestionAnswer {
    private String question;
    private String answer;

    public QuestionAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}

// AdminInterface Class for adding questions and answers
class AdminInterface {
    private static List<QuestionAnswer> questionAnswers = new ArrayList<>();

    public AdminInterface(JFrame parentFrame) {
        JFrame frame = new JFrame("Admin Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel questionLabel = new JLabel("Enter Question: ");
        JTextField questionField = new JTextField(15);
        frame.add(questionLabel);
        frame.add(questionField);

        JLabel answerLabel = new JLabel("Enter Answer: ");
        JTextField answerField = new JTextField(15);
        frame.add(answerLabel);
        frame.add(answerField);

        JButton addButton = new JButton("Add Question");
        frame.add(addButton);

        // Action to add question and answer to the list
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = questionField.getText().trim();
                String answer = answerField.getText().trim();
                if (!question.isEmpty() && !answer.isEmpty()) {
                    questionAnswers.add(new QuestionAnswer(question, answer));
                    JOptionPane.showMessageDialog(frame, "Question Added!");
                    questionField.setText("");
                    answerField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter both question and answer.");
                }
            }
        });

        // "Back" button to go back to the main screen
        JButton backButton = new JButton("Back");
        frame.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 int option = JOptionPane.showOptionDialog(null, "Choose User or Admin Login", "Login",
                         JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Admin", "User"}, "User");

                 if (option == 0) {
                     new AdminLogin(); // Launch Admin Login Screen
                 } else if (option == 1) {
                     new UserLogin(); // Launch User Login Screen
                 }
             }
        });

        frame.setVisible(true);
        parentFrame.setVisible(false); // Hide the parent frame (option screen)
    }

    // Static method to get all questions and answers
    public static List<QuestionAnswer> getQuestionsAndAnswers() {
        return questionAnswers;
    }
}

// UserLogin Class to handle user login and display questions
class UserLogin {
    private JFrame frame;
    private JTextField answerField;
    private JLabel questionLabel;
    private JButton submitButton;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public UserLogin() {
        frame = new JFrame("User Login");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Username input for User login
        JLabel usernameLabel = new JLabel("Enter Username: ");
        JTextField usernameField = new JTextField(15);
        frame.add(usernameLabel);
        frame.add(usernameField);

        // Password input for User login
        JLabel passwordLabel = new JLabel("Enter Password: ");
        JPasswordField passwordField = new JPasswordField(15);
        frame.add(passwordLabel);
        frame.add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simple login check (hardcoded credentials)
                if (usernameField.getText().equals("user") && new String(passwordField.getPassword()).equals("user123")) {
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    frame.dispose(); // Close the login window
                    startQuestionnaire(); // Start the questionnaire after login
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Login");
                }
            }
        });

        frame.setVisible(true);
    }

    // Method to start the questionnaire after successful login
    private void startQuestionnaire() {
        JFrame questionFrame = new JFrame("Answer Questions");
        questionFrame.setLayout(new FlowLayout());
        questionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionFrame.setSize(400, 200);

        // Create question display and answer input
        questionLabel = new JLabel("Question will be here");
        questionFrame.add(questionLabel);

        answerField = new JTextField(20);
        questionFrame.add(answerField);

        submitButton = new JButton("Submit Answer");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                nextQuestion();
            }
        });
        questionFrame.add(submitButton);

        // Start with the first question
        displayQuestion();
        questionFrame.setVisible(true);
    }

    private void displayQuestion() {
        List<QuestionAnswer> questions = AdminInterface.getQuestionsAndAnswers();
        if (currentQuestionIndex < questions.size()) {
            QuestionAnswer currentQA = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQA.getQuestion());
        } else {
            // All questions answered, show the score and exit
            JOptionPane.showMessageDialog(null, "You have answered all the questions!");
            JOptionPane.showMessageDialog(null, "Your Score: " + score);
            System.exit(0);  // Close the application
        }
    }

    private void checkAnswer() {
        List<QuestionAnswer> questions = AdminInterface.getQuestionsAndAnswers();
        QuestionAnswer currentQA = questions.get(currentQuestionIndex);
        String userAnswer = answerField.getText().trim();

        // Check if the answer is correct
        if (userAnswer.equalsIgnoreCase(currentQA.getAnswer())) {
            score++;
        }
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        answerField.setText("");  // Clear the answer field
        displayQuestion();  // Display the next question
    }
}

// AdminLogin Class for admin login
class AdminLogin {
    public AdminLogin() {
        JFrame frame = new JFrame("Admin Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        // Admin login fields
        JLabel usernameLabel = new JLabel("Enter Admin Username: ");
        JTextField usernameField = new JTextField(15);
        frame.add(usernameLabel);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Enter Admin Password: ");
        JPasswordField passwordField = new JPasswordField(15);
        frame.add(passwordLabel);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simple login check (hardcoded credentials)
                if (usernameField.getText().equals("admin") && new String(passwordField.getPassword()).equals("admin123")) {
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    new AdminInterface(frame); // Redirect to admin interface
                    frame.dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Login");
                }
            }
        });

        frame.setVisible(true);
    }
}

// Main class to handle login choice and launch appropriate interfaces
public class OSS {
    public static void main(String[] args) {
        // Launch AdminLogin or UserLogin
        int option = JOptionPane.showOptionDialog(null, "Choose User or Admin Login", "Login",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Admin", "User"}, "User");

        if (option == 0) {
            new AdminLogin(); // Launch Admin Login Screen
        } else if (option == 1) {
            new UserLogin(); // Launch User Login Screen
        }
    }
}
