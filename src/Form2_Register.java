import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.miginfocom.swing.*; //MigLayout

public class Form2_Register extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;
    PreparedStatement p1;

    private JPanel panel1;
    private JLabel lblRAvatarPNG, lblRUsername, lblRPassword, lblRPasswordConfirm, lblREmail, lblREmailConfirm, lblRAlreadyMember;
    private JTextField tfRUsername, tfREmail, tfREmailConfirm;
    private JPasswordField pfRPassword, pfRPasswordConfirm;
    private JButton btnRSignUp;
    ImageIcon RegisterFavicon = new ImageIcon("RegisterFavicon.png");

    public Form2_Register()
    {
        initComponent();
    }

    private void initComponent()
    {
        panel1 = new JPanel();
        panel1.setLayout(new MigLayout());
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Sign Up", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panel1.setBackground(Color.BLACK);

        // Icon
        ImageIcon AvatarPNG = new ImageIcon("RegisterAvatar.png");
        ImageIcon UsernamePNG = new ImageIcon("User.png");
        ImageIcon PasswordPNG = new ImageIcon("Key.png");
        ImageIcon EmailPNG = new ImageIcon("Email.png");
        ImageIcon RightArrowPNG = new ImageIcon("RightArrow.png");

        // Label
        lblRAvatarPNG = new JLabel(AvatarPNG);

        lblRUsername = new JLabel("Your Username : ", UsernamePNG, SwingConstants.LEFT);
            lblRUsername.setForeground(Color.WHITE);

        lblRPassword = new JLabel("Your Password : ", PasswordPNG, SwingConstants.LEFT);
            lblRPassword.setForeground(Color.WHITE);

        lblRPasswordConfirm = new JLabel("Please confirm you password : ", PasswordPNG, SwingConstants.LEFT);
            lblRPasswordConfirm.setForeground(Color.WHITE);

        lblREmail = new JLabel("Your Email : ", EmailPNG, SwingConstants.LEFT);
            lblREmail.setForeground(Color.WHITE);

        lblREmailConfirm = new JLabel("Please confirm your email : ", EmailPNG, SwingConstants.LEFT);
            lblREmailConfirm.setForeground(Color.WHITE);

        lblRAlreadyMember = new JLabel("<HTML><U>Already a member?<U><HTML>");
            lblRAlreadyMember.setForeground(Color.CYAN);
            lblRAlreadyMember.addMouseListener
            (
                    new MouseAdapter()
                    {
                        public void mouseClicked(MouseEvent e)
                        {
                            lblRAlreadyMember_mouseClicked(e);
                        }
                    }
            );

        // TextField
        tfRUsername = new JTextField(20);
            tfRUsername.setBackground(Color.BLACK);
            tfRUsername.setForeground(Color.WHITE);
            tfRUsername.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        tfREmail = new JTextField(20);
            tfREmail.setBackground(Color.BLACK);
            tfREmail.setForeground(Color.WHITE);
            tfREmail.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        tfREmailConfirm = new JTextField(20);
            tfREmailConfirm.setBackground(Color.BLACK);
            tfREmailConfirm.setForeground(Color.WHITE);
            tfREmailConfirm.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // PasswordField
        pfRPassword = new JPasswordField(20);
            pfRPassword.setBackground(Color.BLACK);
            pfRPassword.setForeground(Color.WHITE);
            pfRPassword.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        pfRPasswordConfirm = new JPasswordField(20);
            pfRPasswordConfirm.setBackground(Color.BLACK);
            pfRPasswordConfirm.setForeground(Color.WHITE);
            pfRPasswordConfirm.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // Button
        btnRSignUp = new JButton("Sign Up", RightArrowPNG);
            btnRSignUp.setHorizontalTextPosition(SwingConstants.LEFT);
            /*Flat Button*/
            btnRSignUp.setForeground(Color.WHITE);
            btnRSignUp.setBackground(Color.BLACK);
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnRSignUp.setBorder(compound);
            btnRSignUp.addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        btnRSignUp_actionPerformed(e);
                    }
                }
            );

        // Panel
        panel1.add(lblRAvatarPNG, "wrap,center");
        panel1.add(lblRUsername, "wrap, left");
        panel1.add(tfRUsername, "wrap, left");
        panel1.add(lblRPassword, "wrap, left");
        panel1.add(pfRPassword, "wrap, left");
        panel1.add(lblRPasswordConfirm, "wrap, left");
        panel1.add(pfRPasswordConfirm, "wrap, left");
        panel1.add(lblREmail, "wrap, left");
        panel1.add(tfREmail, "wrap, left");
        panel1.add(lblREmailConfirm, "wrap, left");
        panel1.add(tfREmailConfirm, "wrap, left");
        panel1.add(btnRSignUp, "wrap, center");
        panel1.add(lblRAlreadyMember, "center");

        getContentPane().add(panel1);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setTitle("PAM - Register");
        setIconImage(RegisterFavicon.getImage());
    }

    private void lblRAlreadyMember_mouseClicked(MouseEvent e)
    {
        Form1_Login form1 = new Form1_Login();
        form1.setVisible(true);
        form1.pack();
        form1.setLocationRelativeTo(null);

        this.setVisible(false);
    }

    private void vConnectDB()
    {
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "user2", "user2");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vSignUp(String Username, String Password, String Email)
    {
        try
        {
            vConnectDB();

            String sql_statement = "INSERT INTO Login VALUES(?,?,?,?)";
            PreparedStatement p1 = conn.prepareStatement(sql_statement);
            p1.setString(1, Username);
            p1.setString(2, Password);
            p1.setString(3, Email);
            p1.setString(4, "User");
            p1.executeUpdate();

            JOptionPane.showMessageDialog(null, "Register Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnRSignUp_actionPerformed(ActionEvent e)
    {
        if(tfRUsername.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Username TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(pfRPassword.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Password TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(pfRPasswordConfirm.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Password Confirmation TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(tfREmail.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Email TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(tfREmailConfirm.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Email Confirmation TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(!pfRPassword.getText().equals(pfRPasswordConfirm.getText()))
        {
            JOptionPane.showMessageDialog(null, "Password doesn't match!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(!tfREmail.getText().equals(tfREmailConfirm.getText()))
        {
            JOptionPane.showMessageDialog(null, "Email doesn't match!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vSignUp(tfRUsername.getText(), pfRPassword.getText(), tfREmail.getText());
        }
    }
}