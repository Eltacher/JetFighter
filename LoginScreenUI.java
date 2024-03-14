import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginScreenUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
    private JButton cancelButton, loginButton,rulesButton;
    private JTextField nameField;
    private JPasswordField passwordField;
    private static String GAMETITLE = " CSE212 Jet Fighter";
    private JLabel gameHeader;
    private UserDataManager dmanager;
    private Game gamemanager;
    protected JLabel passwordLabel;
    protected JLabel nameLabel;
    private static User currentplayer;
    public static Thread gameThread;
    public static final int WIDTH = 600;    // Game Screen Width
    public static final int HEIGHT = 800;    // Game Screen Height
    protected boolean LoginChecker = false;

    public LoginScreenUI() 
    {

        setTitle(GAMETITLE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        gameHeader = new JLabel("JET FIGHTER");
        Font headerFont = new Font("Times New Roman", Font.BOLD, 36);
        gameHeader.setFont(headerFont);
        gameHeader.setHorizontalAlignment(SwingConstants.CENTER);
        gameHeader.setSize(300, 30);
        gameHeader.setLocation(WIDTH / 2 - 150, HEIGHT / 2 - 150);
        gameHeader.setForeground(Color.white);
        
        rulesButton = new JButton("Rules");
        rulesButton.setBounds(WIDTH / 2 - 75, HEIGHT / 2 , 150, 20);
        add(rulesButton);

        JMenu fileMenu = new JMenu("File");
        JMenuItem RegisterItem = new JMenuItem("Register");
        JMenuItem LoginItem = new JMenuItem("Login");
        JMenuItem PlayGameItem = new JMenuItem("Play Game");
        JMenuItem ScoreTableItem = new JMenuItem("Score Table");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(RegisterItem);
        fileMenu.add(LoginItem);
        fileMenu.add(PlayGameItem);
        fileMenu.add(ScoreTableItem);
        fileMenu.add(exitItem);

        JMenu HelpMenu = new JMenu("Help");
        JMenuItem About = new JMenuItem("About");
        HelpMenu.add(About);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(HelpMenu);

        add(menuBar);
        add(gameHeader);

        menuBar.setBounds(0, 0, 600, 20);

        exitItem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });
        
        rulesButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        	 String rulesMessage ="At the beginning, you have 100 bullets and 100 health.\n"
                     + "Every time you kill an enemy, your health will increase by 1,\n"
                     + "and your bullets will increase by 4.";

             JOptionPane.showMessageDialog(null, rulesMessage, "RULES", JOptionPane.PLAIN_MESSAGE);
        	}
        });
        
        RegisterItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boolean registrationSuccessful = false;

                while (!registrationSuccessful) 
                {
                    dmanager = new UserDataManager();
                    JPasswordField passwordField = new JPasswordField();
                    JTextField nameField = new JTextField();

                    Object[] obj = {"Enter name for new user:\n", nameField, "Please enter the password:\n", passwordField};
                    int option = JOptionPane.showOptionDialog(null, obj, "Registration", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, null, null);

                    if (option == JOptionPane.OK_OPTION) 
                    {
                        String name = nameField.getText();
                        char[] charPassword = passwordField.getPassword();
                        String password = new String(charPassword);

                        if (!name.isEmpty() && !password.isEmpty()) 
                        {
                            dmanager.saveDataToTXT(name, password, "0");
                            JOptionPane.showMessageDialog(null, "Registration is successful for user " + name, "Registration Success!",JOptionPane.INFORMATION_MESSAGE);
                            registrationSuccessful = true;
                        }
                        else 
                        {
                            JOptionPane.showMessageDialog(null, "Both name and password are required.", "Warning!", JOptionPane.WARNING_MESSAGE);
                        }
                    } 
                    else 
                    {
                       break;
                    }
                }
            }
        });


        LoginItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFrame panel = new JFrame();
                panel.setTitle("Login Screen");
                panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                panel.setSize(350, 350);
                panel.setResizable(false);
                panel.setLocationRelativeTo(null);

                JPanel contentPane = new JPanel();
                contentPane.setLayout(null);

                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setBounds(40, 115, 75, 30);
                nameLabel.setForeground(Color.BLACK);
                contentPane.add(nameLabel);

                nameField = new JTextField();
                nameField.setBounds(125, 115, 150, 30);
                nameField.setBackground(Color.white);
                nameField.setForeground(Color.black);
                contentPane.add(nameField);

                JLabel passwordLabel = new JLabel("Password:");
                passwordLabel.setBounds(40, 165, 75, 30);
                passwordLabel.setForeground(Color.BLACK);
                contentPane.add(passwordLabel);

                passwordField = new JPasswordField();
                passwordField.setBounds(125, 165, 150, 30);
                passwordField.setBackground(Color.white);
                passwordField.setForeground(Color.black);
                contentPane.add(passwordField);

                loginButton = new JButton("Login");
                loginButton.setBounds(100, 215, 80, 20);
                loginButton.addActionListener(e -> handleLogin(panel));
                contentPane.add(loginButton);

                cancelButton = new JButton("Cancel");
                cancelButton.setBounds(200, 215, 80, 20);
                cancelButton.addActionListener(e -> panel.dispose());
                contentPane.add(cancelButton);

                panel.setContentPane(contentPane);
                panel.setVisible(true);
            }

            private void handleLogin(JFrame panel) 
            {
                dmanager = new UserDataManager();
                currentplayer = dmanager.UserChecker(nameField.getText(), new String(passwordField.getPassword()));
                if (currentplayer != null) {
                    JOptionPane.showMessageDialog(null, "Welcome " + nameField.getText() + " !",
                            "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                    LoginChecker = true;
                    panel.dispose();
                }
            }
        });



        PlayGameItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (LoginChecker) {
                    
                    dmanager = new UserDataManager();

                    gamemanager = new Game(WIDTH, HEIGHT, currentplayer);
                    gamemanager.setVisible(true);
                    gamemanager.setTitle("JET FIGHTER");
                    gamemanager.setSize(WIDTH, HEIGHT);
                    gamemanager.setResizable(false);
                    gamemanager.setLocationRelativeTo(null);
                    gamemanager.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                    gameThread = new Thread(gamemanager);
                    gameThread.start();

                    gamemanager.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            int result = JOptionPane.showConfirmDialog(gamemanager, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.YES_OPTION) 
                            {
                                gamemanager.dispose();
                                System.exit(0);
                            }
                        }
                    });
                } 
                else 
                {
                    JOptionPane.showMessageDialog(null, "You must be logged in!", "Not Logged In!", JOptionPane.WARNING_MESSAGE);
                    LoginItem.doClick();
                }
            }
        });

        About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null, "Emre Kaan Özkan \n20210702085", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        ScoreTableItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmanager = new UserDataManager();
                JOptionPane.showMessageDialog(null, dmanager.ScoreFromTXT(), "Score Table", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
}
