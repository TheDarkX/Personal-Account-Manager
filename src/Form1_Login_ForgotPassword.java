import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import net.miginfocom.swing.*; //MigLayout

public class Form1_Login_ForgotPassword extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;
    String update_statement = "";
    PreparedStatement p1;

    private JButton btnFPBack, btnFPSubmit;
    private JLabel lblFPUsername, lblFPEmail, lblFPNewPassword, lblFPConfirmPassword;
    private JTextField tfFPUsername, tfFPEmail, tfFPNewPassword, tfFPConfirmPassword;
    private JPanel panel1;

    public Form1_Login_ForgotPassword()
    {
        initComponent();
    }

    private void initComponent()
    {
        panel1 = new JPanel();
        panel1.setLayout(new MigLayout());
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Reset Password", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panel1.setBackground(Color.BLACK);

        // Icon
        ImageIcon KeyPNG = new ImageIcon("Key.png"); // ForgotPasswordFavicon
        ImageIcon RightArrowPNG = new ImageIcon("RightArrow.png"); // btnFPSubmit
        ImageIcon BackPNG = new ImageIcon("Back.png"); // btnFPBack
        ImageIcon EmailPNG = new ImageIcon("Email.png"); // lblFPEmail
        ImageIcon UserPNG = new ImageIcon("User.png"); // lblFPUsername

        // Label
        lblFPUsername = new JLabel("Your Username : ", UserPNG, SwingConstants.LEFT);
            lblFPUsername.setForeground(Color.WHITE);

        lblFPEmail = new JLabel("Your Email : ", EmailPNG, SwingConstants.LEFT);
            lblFPEmail.setForeground(Color.WHITE);

        lblFPNewPassword = new JLabel("New Password : ", KeyPNG, SwingConstants.LEFT);
            lblFPNewPassword.setForeground(Color.WHITE);

        lblFPConfirmPassword = new JLabel("Please confirm your password : ", KeyPNG, SwingConstants.LEFT);
            lblFPConfirmPassword.setForeground(Color.WHITE);

        // TextField
        tfFPUsername = new JTextField(20);
            tfFPUsername.setForeground(Color.WHITE);
            tfFPUsername.setBackground(Color.BLACK);
            tfFPUsername.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        tfFPEmail = new JTextField(20);
            tfFPEmail.setForeground(Color.WHITE);
            tfFPEmail.setBackground(Color.BLACK);
            tfFPEmail.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        tfFPNewPassword = new JTextField(20);
            tfFPNewPassword.setForeground(Color.WHITE);
            tfFPNewPassword.setBackground(Color.BLACK);
            tfFPNewPassword.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        tfFPConfirmPassword = new JTextField(20);
            tfFPConfirmPassword.setForeground(Color.WHITE);
            tfFPConfirmPassword.setBackground(Color.BLACK);
            tfFPConfirmPassword.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // Button
        btnFPSubmit = new JButton("Submit", RightArrowPNG);
            btnFPSubmit.setForeground(Color.WHITE);
            btnFPSubmit.setBackground(Color.BLACK);
            btnFPSubmit.setHorizontalTextPosition(SwingConstants.LEFT);
            /*Flat Button*/
            btnFPSubmit.setForeground(Color.WHITE);
            btnFPSubmit.setBackground(Color.BLACK);
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnFPSubmit.setBorder(compound);
            btnFPSubmit.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnFPSubmit_actionPerformed(e);
                        }
                    }
            );

        btnFPBack = new JButton("Back", BackPNG);
            btnFPBack.setForeground(Color.WHITE);
            btnFPBack.setBackground(Color.BLACK);
            btnFPBack.setHorizontalTextPosition(SwingConstants.RIGHT);
            btnFPBack.setBorder(compound);
            btnFPBack.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnFPBack_actionPerformed(e);
                        }
                    }
            );

        // Panel
        panel1.add(btnFPBack, "left, wrap");
        panel1.add(lblFPUsername, "wrap");
        panel1.add(tfFPUsername, "grow, wrap");
        panel1.add(lblFPEmail, "wrap");
        panel1.add(tfFPEmail, "grow, wrap");
        panel1.add(lblFPNewPassword, "wrap");
        panel1.add(tfFPNewPassword, "grow, wrap");
        panel1.add(lblFPConfirmPassword, "wrap");
        panel1.add(tfFPConfirmPassword, "grow, wrap");
        panel1.add(btnFPSubmit, "span, grow, wrap");

        getContentPane().add(panel1);

        // Default Main
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Reset Password");
        setIconImage(KeyPNG.getImage());
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

    private void btnFPBack_actionPerformed(ActionEvent e)
    {
        Form1_Login form1_Login = new Form1_Login();
        form1_Login.setVisible(true);

        this.setVisible(false);
    }

    private void vChangePassword(String NewPassword, String Username, String Email)
    {
        try
        {
            vConnectDB();

            String sql_statement = "UPDATE Login SET Password = ? WHERE Username = ? AND Email = ?";

            PreparedStatement p1 = conn.prepareStatement(sql_statement);
            p1.setString(1, NewPassword);
            p1.setString(2, Username);
            p1.setString(3, Email);
            p1.executeUpdate();

            JOptionPane.showMessageDialog(null, "Password reset successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e1)
        {
            JOptionPane.showMessageDialog(null, "Old Password entered might be wrong.\n" + e1, "Error", JOptionPane.ERROR_MESSAGE);
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

    private void btnFPSubmit_actionPerformed(ActionEvent e)
    {
        if(tfFPUsername.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Username TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(tfFPEmail.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Email TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(tfFPNewPassword.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "New Password TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(tfFPConfirmPassword.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Confirm Password TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(!tfFPNewPassword.getText().equals(tfFPConfirmPassword.getText()))
        {
            JOptionPane.showMessageDialog(null, "Password doesn't match.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vChangePassword(tfFPNewPassword.getText(), tfFPUsername.getText(), tfFPEmail.getText());
        }
    }
}