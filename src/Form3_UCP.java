import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import net.miginfocom.swing.*; //MigLayout

public class Form3_UCP extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;
    String update_statement = "";
    PreparedStatement p1;

    private JComboBox cmbUCPSelection, cmbUCPPosition;
    private JLabel lblUCPOld, lblUCPNew, lblUCPConfirm;
    private JTextField tfUCPOld, tfUCPNew, tfUCPConfirm;
    private JButton btnUCPSubmit;
    private JPanel panel1;

    protected String $GetUsername;
    protected String $GetPassword;
    protected String $GetEmail;
    protected String $GetPosition;

    ImageIcon EmailPNG = new ImageIcon("Email.png"); // lblUCPEmail
    ImageIcon KeyPNG = new ImageIcon("Key.png"); // UCPFaviconPNG

    public Form3_UCP()
    {
        initComponent();
    }

    private void initComponent()
    {
        // User Control Panel
        panel1 = new JPanel();
        panel1.setLayout(new MigLayout());
        panel1.setBorder(BorderFactory.createTitledBorder(null, "User Control Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panel1.setBackground(Color.BLACK);

        // Icon
        ImageIcon UCPFaviconPNG = new ImageIcon("Admin.png"); // UCPFaviconPNG
        ImageIcon RightArrowPNG = new ImageIcon("RightArrow.png"); // btnUCPSubmit

        // ComboBox
        String aUCPSelection [] = {"Select an option", "Change Password", "Change Email"};
        cmbUCPSelection = new JComboBox(aUCPSelection);
            cmbUCPSelection.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            vUCPSelection(e);
                        }
                    }
            );

        // Label
        lblUCPOld = new JLabel("Old", KeyPNG, SwingConstants.LEFT);
            lblUCPOld.setForeground(Color.WHITE);

        lblUCPNew = new JLabel("New", KeyPNG, SwingConstants.LEFT);
            lblUCPNew.setForeground(Color.WHITE);

        lblUCPConfirm = new JLabel("Confirm", KeyPNG, SwingConstants.LEFT);
            lblUCPConfirm.setForeground(Color.WHITE);

        // TextField
        tfUCPOld = new JTextField(20);
            tfUCPOld.setForeground(Color.WHITE);
            tfUCPOld.setBackground(Color.BLACK);
            tfUCPOld.setCaretColor(Color.ORANGE);

        tfUCPNew = new JTextField(20);
            tfUCPNew.setForeground(Color.WHITE);
            tfUCPNew.setBackground(Color.BLACK);
            tfUCPNew.setCaretColor(Color.ORANGE);

        tfUCPConfirm = new JTextField(20);
            tfUCPConfirm.setForeground(Color.WHITE);
            tfUCPConfirm.setBackground(Color.BLACK);
            tfUCPConfirm.setCaretColor(Color.ORANGE);

        // Button
        btnUCPSubmit = new JButton("Update", RightArrowPNG);
            btnUCPSubmit.setHorizontalTextPosition(SwingConstants.LEFT);
            //Flat Button
            btnUCPSubmit.setForeground(Color.WHITE);
            btnUCPSubmit.setBackground(Color.BLACK);
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnUCPSubmit.setBorder(compound);
            btnUCPSubmit.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            vSubmit_actionPerformed(e);
                        }
                    }
            );

        // Panel
        panel1.add(cmbUCPSelection, "grow, wrap");
        panel1.add(lblUCPOld, "wrap");
        panel1.add(tfUCPOld, "wrap");
        panel1.add(lblUCPNew, "wrap");
        panel1.add(tfUCPNew, "wrap");
        panel1.add(lblUCPConfirm, "wrap");
        panel1.add(tfUCPConfirm, "wrap");
        panel1.add(btnUCPSubmit, "grow, span, wrap");

        getContentPane().add(panel1);

        // Default Setting
        cmbUCPSelection.setSelectedIndex(0);

        // Default Main
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
        setTitle("User Control Panel");
        setIconImage(UCPFaviconPNG.getImage());
    }

    private void vUCPSelection(ActionEvent e)
    {
        if(cmbUCPSelection.getSelectedIndex() == 0) // Select an option
        {
            lblUCPOld.setText("Old  : ");
            lblUCPNew.setText("New : ");
            lblUCPConfirm.setText("Confirm : ");

            lblUCPOld.setIcon(null);
            lblUCPNew.setIcon(null);
            lblUCPConfirm.setIcon(null);

            tfUCPOld.setEditable(false);
            tfUCPNew.setEditable(false);
            tfUCPConfirm.setEditable(false);

            tfUCPOld.setText("");
            tfUCPNew.setText("");
            tfUCPConfirm.setText("");

            tfUCPOld.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfUCPNew.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfUCPConfirm.setBorder(BorderFactory.createLineBorder(Color.RED));
            pack();
        }
        else if(cmbUCPSelection.getSelectedIndex() == 1) // Change Password
        {
            lblUCPOld.setText("Old Password : ");
            lblUCPNew.setText("New Password : ");
            lblUCPConfirm.setText("Please confirm your password : ");

            lblUCPOld.setIcon(KeyPNG);
            lblUCPNew.setIcon(KeyPNG);
            lblUCPConfirm.setIcon(KeyPNG);

            tfUCPOld.setEditable(true);
            tfUCPNew.setEditable(true);
            tfUCPConfirm.setEditable(true);

            tfUCPOld.setText("");
            tfUCPNew.setText("");
            tfUCPConfirm.setText("");

            tfUCPOld.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfUCPNew.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfUCPConfirm.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            pack();
        }
        else if(cmbUCPSelection.getSelectedIndex() == 2) // Change Email
        {
            lblUCPOld.setText("Password : ");
            lblUCPNew.setText("New Email : ");
            lblUCPConfirm.setText("Please confirm your email : ");

            lblUCPOld.setIcon(KeyPNG);
            lblUCPNew.setIcon(EmailPNG);
            lblUCPConfirm.setIcon(EmailPNG);

            tfUCPOld.setEditable(true);
            tfUCPNew.setEditable(true);
            tfUCPConfirm.setEditable(true);

            tfUCPOld.setText("");
            tfUCPNew.setText("");
            tfUCPConfirm.setText("");

            tfUCPOld.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfUCPNew.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfUCPConfirm.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            pack();
        }
    }

    private void vSubmit_actionPerformed(ActionEvent e)
    {
        try
        {
            vConnectDB();
            // Validate Online Database Query
            ResultSet r1 = null;

            String validate_statement = "SELECT * FROM Online WHERE Password = ?";
            PreparedStatement p2 = conn.prepareStatement(validate_statement);
            p2.setString(1, tfUCPOld.getText());
            r1 = p2.executeQuery();
            
            while(r1.next())
            {
                $GetUsername = (String)r1.getObject(1);
                $GetPassword = (String)r1.getObject(2);
                $GetEmail = (String)r1.getObject(3);
                $GetPosition = (String)r1.getObject(4);
            }
        }
        catch(Exception e1)
        {
            JOptionPane.showMessageDialog(null, e1, "Error", JOptionPane.ERROR_MESSAGE);
            e1.printStackTrace();
        }

        if(cmbUCPSelection.getSelectedIndex() == 0) // Select an option
        {
            JOptionPane.showMessageDialog(null, "Please select an option.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(cmbUCPSelection.getSelectedIndex() == 1) // Change Password
        {
            if(tfUCPOld.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Old Password TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(tfUCPNew.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "New Password TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(tfUCPConfirm.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Confirm Password TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(!tfUCPNew.getText().equals(tfUCPConfirm.getText()))
            {
                JOptionPane.showMessageDialog(null, "New Password & Confirmation Pasword not match.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                vChangePassword(tfUCPNew.getText(), $GetUsername, tfUCPOld.getText());
            }
        }
        else if(cmbUCPSelection.getSelectedIndex() == 2) // Change Email
        {
            if(tfUCPOld.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Password TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(tfUCPNew.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "New Email TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(tfUCPConfirm.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Confirm Email TextField should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(!tfUCPNew.getText().equals(tfUCPConfirm.getText()))
            {
                JOptionPane.showMessageDialog(null, "New Email & Confirmation Email not match.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                vChangeEmail(tfUCPNew.getText(), $GetUsername, tfUCPOld.getText());
            }
        }
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

    private void vChangePassword(String NewPassword, String GetUsername, String OldPassword)
    {
        try
        {
            if(!tfUCPOld.getText().equals($GetPassword))
            {
                JOptionPane.showMessageDialog(null, "Password incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                vConnectDB();

                String sql_statement = "UPDATE Login SET Password = ? WHERE Username = ? AND Password = ?";

                PreparedStatement p1 = conn.prepareStatement(sql_statement);
                p1.setString(1, NewPassword);
                p1.setString(2, GetUsername);
                p1.setString(3, OldPassword);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Password Changed Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
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

    private void vChangeEmail(String NewEmail, String GetUsername, String GetPassword)
    {
        try
        {
            vConnectDB();

            if(!tfUCPOld.getText().equals($GetPassword))
            {
                JOptionPane.showMessageDialog(null, "Password incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String update_statement = "UPDATE Login SET Email = ? WHERE Username = ? AND Password = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, NewEmail);
                p1.setString(2, GetUsername);
                p1.setString(3, GetPassword);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Email Changed Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(SQLException e1)
        {
            JOptionPane.showMessageDialog(null, "Password entered might be wrong.\n" + e1, "Error", JOptionPane.ERROR_MESSAGE);
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
}