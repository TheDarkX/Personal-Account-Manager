import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.miginfocom.swing.*; //MigLayout

public class Form1_Login extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;

    // Login Panel
    private JLabel lblLAvatarPNG, lblLUsername, lblLPassword, lblLRegister, lblLForgotPassword;
    private JTextField tfLUsername;
    private JPasswordField pfLPassword;
    private JButton btnLLogin;
    private JPanel panel1;

    // Icon
    ImageIcon Favicon = new ImageIcon("Home.png"); // btnLoginPNG
    ImageIcon AvatarPNG = new ImageIcon("LoginAvatar.png"); // AvatarPNG
    ImageIcon UsernamePNG = new ImageIcon("User.png"); // lblUserPNG
    ImageIcon PasswordPNG = new ImageIcon("Key.png"); // lblPasswordPNG
    ImageIcon RightArrowPNG = new ImageIcon("RightArrow.png"); // btnLoginPNG

    protected String $ResultSetUsername;
    protected String $ResultSetPassword;
    protected String $ResultSetEmail;
    protected String $ResultSetPosition;

    public Form1_Login()
    {
        initComponent();
    }

    public static void main(String[] args)
    {
        Form1_Login form1 = new Form1_Login();
    }

    private void initComponent()
    {
        // Login Panel
        panel1 = new JPanel();
        panel1.setLayout(new MigLayout());
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Admin/User Login Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panel1.setBackground(Color.BLACK);

        // Label
        lblLAvatarPNG = new JLabel(AvatarPNG);

        lblLUsername = new JLabel("Username:", UsernamePNG, SwingConstants.LEFT);
            lblLUsername.setForeground(Color.WHITE);

        lblLPassword = new JLabel("Password:", PasswordPNG, SwingConstants.LEFT);
            lblLPassword.setForeground(Color.WHITE);

        lblLRegister = new JLabel("<HTML><U>Create an account<U><HTML>");
            lblLRegister.setForeground(Color.CYAN);
            lblLRegister.addMouseListener
            (
                    new MouseAdapter()
                    {
                        public void mouseClicked(MouseEvent e)
                        {
                            lblLRegister_mouseClicked(e);
                        }
                    }
            );

        lblLForgotPassword = new JLabel("<HTML><U>Forgot Password?<U><HTML>");
            lblLForgotPassword.setForeground(Color.CYAN);
            lblLForgotPassword.addMouseListener
            (
                    new MouseAdapter()
                    {
                        public void mouseClicked(MouseEvent e)
                        {
                            lblLForgotPassword_mouseClicked(e);
                        }
                    }
            );

        // TextField
        tfLUsername = new JTextField(20);
            tfLUsername.setBackground(Color.BLACK);
            tfLUsername.setForeground(Color.WHITE);
            tfLUsername.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfLUsername.setCaretColor(Color.ORANGE);

        // PasswordField
        pfLPassword = new JPasswordField(20);
            pfLPassword.setBackground(Color.BLACK);
            pfLPassword.setForeground(Color.WHITE);
            pfLPassword.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            pfLPassword.setCaretColor(Color.ORANGE);

        // Button
        btnLLogin = new JButton("Login", RightArrowPNG);
            btnLLogin.setHorizontalTextPosition(SwingConstants.LEFT);
            /*Flat Button*/
            btnLLogin.setForeground(Color.WHITE);
            btnLLogin.setBackground(Color.BLACK);
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnLLogin.setBorder(compound);
            btnLLogin.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnLLogin_actionPerformed(e);
                        }
                    }
            );
            

        // Panel
        panel1.add(lblLAvatarPNG, "span, center, wrap");
        panel1.add(lblLUsername, "wrap");
        panel1.add(tfLUsername, "wrap");
        panel1.add(lblLPassword, "wrap");
        panel1.add(pfLPassword, "wrap");
        panel1.add(btnLLogin, "span, center wrap");
        panel1.add(lblLRegister, "span, center, wrap");
        panel1.add(lblLForgotPassword, "span, center");

        getContentPane().add(panel1);

        // Default Main
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("PAM - Login");
        setIconImage(Favicon.getImage());
    }
    
    private void lblLRegister_mouseClicked(MouseEvent e)
    {
        Form2_Register form2 = new Form2_Register();
        form2.setVisible(true);
        form2.pack();
        form2.setLocationRelativeTo(null);

        this.setVisible(false); // Hide current form
    }

    private void lblLForgotPassword_mouseClicked(MouseEvent e)
    {
        Form1_Login_ForgotPassword form1_Login_ForgotPassword = new Form1_Login_ForgotPassword();
        form1_Login_ForgotPassword.setVisible(true);

        this.setVisible(false);
    }
    
    private void btnLLogin_actionPerformed(ActionEvent e)
    {
        ResultSet r1 = null;
        PreparedStatement p1 = null;
        boolean bValidLogin = false;

        try
        {
            vClosedDeleteOnlineQuery(e);
            vConnectDB();

            // Login Check
            String sql_statement = "SELECT * FROM login WHERE Username = ? AND Password = ?";
            p1 = conn.prepareStatement(sql_statement);
            p1.setString(1, tfLUsername.getText());
            p1.setString(2, pfLPassword.getText());
            r1 = p1.executeQuery();
            
            String $Username = "";
            String $Password = "";
            String $Email = "";
            String $Position = "";
            String getUsername = tfLUsername.getText();
            String getPassword = pfLPassword.getText();

            while(r1.next())
            {
                $Username = (String)r1.getObject(1);
                $Password = (String)r1.getObject(2);
                $Email = (String)r1.getObject(3);
                $Position = (String)r1.getObject(4);
            }

            // Add into Online List - Database Table
                // ResultSet Email & Position
                String resultset_statement = "SELECT * FROM login WHERE Username = ? AND Password = ?";
                $ResultSetUsername = tfLUsername.getText();
                $ResultSetPassword = pfLPassword.getText();
                p1 = conn.prepareStatement(resultset_statement);
                p1.setString(1, $ResultSetUsername);
                p1.setString(2, $ResultSetPassword);
                r1 = p1.executeQuery();

                $ResultSetEmail = "";
                $ResultSetPosition = "";

                while(r1.next())
                {
                    $ResultSetEmail = (String)r1.getObject(3);
                    $ResultSetPosition = (String)r1.getObject(4);
                }

                // CREATE TABLE Online(Username varchar(255) NOT NULL UNIQUE, Password varchar(255) NOT NULL, Email varchar(255) NOT NULL, Position varchar(255) NOT NULL);
                String add_online_statement = "INSERT INTO Online VALUES(?,?,?,?) ";
                p1 = conn.prepareStatement(add_online_statement);
                p1.setString(1, $ResultSetUsername);
                p1.setString(2, $ResultSetPassword);
                p1.setString(3, $ResultSetEmail);
                p1.setString(4, $ResultSetPosition);
                p1.executeUpdate();

            // Check if tfLUsername & pfLPassword are blank
            if(tfLUsername.getText().equals("") || pfLPassword.getText().equals(""))
            {
                bValidLogin = false;
            }
            else
            {
                bValidLogin = true;
            }

            if(bValidLogin == true)
            {
                if(getUsername.equals($Username) && getPassword.equals($Password))
                {
                    JOptionPane.showMessageDialog(null, "Login Successfully!\nWelcome, " + $Username + ".", "Welcome", JOptionPane.INFORMATION_MESSAGE);

                    Form3_PAM form3 = new Form3_PAM();
                    form3.setVisible(true);

                    this.setVisible(false); // Hide current form
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Control Panel
                if($Position.equals("Admin") || $Position.equals("Master"))
                {
                    Form1_ACP form1_ACP = new Form1_ACP();
                    form1_ACP.setVisible(true);
                }

                if($Position.equals("User"))
                {
                    Form3_UCP form3_UCP = new Form3_UCP();
                    form3_UCP.setVisible(true);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e1)
        {
            JOptionPane.showMessageDialog(null, e1, "Error", JOptionPane.ERROR_MESSAGE);
            e1.printStackTrace();
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
            }
            catch(Exception e1)
            {
                JOptionPane.showMessageDialog(null, e1, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void vConnectDB()
    {
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "user2", "user2");

            // CREATE DATABASE pLogin;
            // Use pLogin;
            // CREATE TABLE Login(Username varchar(255) NOT NULL UNIQUE, Password varchar(255) NOT NULL, Email varchar(255) NOT NULL UNIQUE, Position varchar(255) NOT NULL);
            // INSERT INTO Login VALUES('Master', 'Master',"Master" , 'Master');
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vClosedDeleteOnlineQuery(ActionEvent e)
    {
        try
        {
            vConnectDB();

            String delete_online_query_statement = "DELETE FROM Online";
            PreparedStatement p1 = conn.prepareStatement(delete_online_query_statement);
            p1.executeUpdate();
        }
        catch(Exception e1)
        {
            JOptionPane.showMessageDialog(null, e1, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
            }
            catch(Exception e1)
            {
                JOptionPane.showMessageDialog(null, e1, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}