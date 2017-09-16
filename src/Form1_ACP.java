import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.Timer;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import net.miginfocom.swing.*; //MigLayout

public class Form1_ACP extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;
    String update_statement = "";
    PreparedStatement p1;

    private JComboBox cmbACPSelection, cmbACPPosition;
    private JLabel lblACPOriginalUsername, lblACPUsername, lblACPPassword, lblACPEmail, lblACPPosition;
    private JTextField tfACPOriginalUsername, tfACPUsername, tfACPPassword, tfACPEmail;
    private JButton btnACPSubmit, btnACPViewQuery, btnACPDeleteAllQuery;
    private JCheckBox cbACPUsername, cbACPPassword, cbACPEmail, cbACPPosition;
    private JPanel panel1;

    public Form1_ACP()
    {
        initComponent();
    }

    private void initComponent()
    {
        panel1 = new JPanel();
        panel1.setLayout(new MigLayout());
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Admin Control Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panel1.setBackground(Color.BLACK);

        // Icon
        ImageIcon OriginalPNG = new ImageIcon("Original.png"); // lblACPOriginal
        ImageIcon UsernamePNG = new ImageIcon("User.png"); // lblACPUsername
        ImageIcon PasswordPNG = new ImageIcon("Key.png"); // lblACPPassword
        ImageIcon RightArrowPNG = new ImageIcon("RightArrow.png"); // btnACPSubmit
        ImageIcon ACPAdminPNG = new ImageIcon("Admin.png"); // ACPFavicon
        ImageIcon ACPEmailPNG = new ImageIcon("Email.png"); // lblACPEmail
        ImageIcon ACPPositionPNG = new ImageIcon("Position.png"); // lblACPPosition
        ImageIcon ACPViewQueryPNG = new ImageIcon("ViewQuery.png"); // btnACPViewQuery
        ImageIcon ACPDeleteAllQueryPNG = new ImageIcon("DeleteAllQuery.png"); // btnACPDeleteAllQuery

        // Label
        lblACPOriginalUsername = new JLabel("Original Username : ", OriginalPNG, SwingConstants.LEFT);
            lblACPOriginalUsername.setForeground(Color.WHITE);

        lblACPUsername = new JLabel("Username : ", UsernamePNG, SwingConstants.LEFT);
            lblACPUsername.setForeground(Color.WHITE);

        lblACPPassword = new JLabel("Password : ", PasswordPNG, SwingConstants.LEFT);
            lblACPPassword.setForeground(Color.WHITE);

        lblACPEmail = new JLabel("Email : ", ACPEmailPNG, SwingConstants.LEFT);
            lblACPEmail.setForeground(Color.WHITE);

        lblACPPosition = new JLabel("Position : ", ACPPositionPNG, SwingConstants.LEFT);
            lblACPPosition.setForeground(Color.WHITE);

        // TextField
        tfACPOriginalUsername = new JTextField(20);
            tfACPOriginalUsername.setBackground(Color.BLACK);
            tfACPOriginalUsername.setForeground(Color.WHITE);
            tfACPOriginalUsername.setCaretColor(Color.ORANGE);

        tfACPUsername = new JTextField(20);
            tfACPUsername.setBackground(Color.BLACK);
            tfACPUsername.setForeground(Color.WHITE);
            tfACPUsername.setCaretColor(Color.ORANGE);

        tfACPPassword = new JTextField(20);
            tfACPPassword.setBackground(Color.BLACK);
            tfACPPassword.setForeground(Color.WHITE);
            tfACPPassword.setCaretColor(Color.ORANGE);

        tfACPEmail = new JTextField(20);
            tfACPEmail.setBackground(Color.BLACK);
            tfACPEmail.setForeground(Color.WHITE);
            tfACPEmail.setCaretColor(Color.ORANGE);

        // Button
        btnACPSubmit = new JButton("Submit", RightArrowPNG);
            btnACPSubmit.setHorizontalTextPosition(SwingConstants.LEFT);
            /*Flat Button*/
            btnACPSubmit.setForeground(Color.WHITE);
            btnACPSubmit.setBackground(Color.BLACK);
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnACPSubmit.setBorder(compound);
            btnACPSubmit.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnACPSubmit_actionPerformed(e);
                        }
                    }
            );

        btnACPViewQuery = new JButton("View Query", ACPViewQueryPNG);
            btnACPViewQuery.setHorizontalTextPosition(SwingConstants.LEFT);
            /*Flat Button*/
            btnACPViewQuery.setForeground(Color.WHITE);
            btnACPViewQuery.setBackground(Color.BLACK);
            btnACPViewQuery.setBorder(compound);
            btnACPViewQuery.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnACPViewQuery_actionPerformed(e);
                        }
                    }
            );

        btnACPDeleteAllQuery = new JButton("Delete All Query", ACPDeleteAllQueryPNG);
            btnACPDeleteAllQuery.setHorizontalTextPosition(SwingConstants.LEFT);
            /*Flat Button*/
            btnACPDeleteAllQuery.setForeground(Color.WHITE);
            btnACPDeleteAllQuery.setBackground(Color.BLACK);
            btnACPDeleteAllQuery.setBorder(compound);
            btnACPDeleteAllQuery.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnACPDeleteAllQuery_actionPerformed(e);
                        }
                    }
            );

        // ComboBox
        String aACPSelection [] = {"Select an option", "Insert Query", "Update Query", "Delete Query"};
        cmbACPSelection = new JComboBox(aACPSelection);
            cmbACPSelection.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            cmbACPSelection_actionPerformed(e);
                        }
                    }
            );

        String aACPPosition [] = {"Select an option" ,"User", "Admin"};
        cmbACPPosition = new JComboBox(aACPPosition);
            cmbACPPosition.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            cmbACPPosition_actionPerformed(e);
                        }
                    }
            );
            cmbACPPosition.setEnabled(false);

        // CheckBox
        cbACPUsername = new JCheckBox("Update Username?");
            cbACPUsername.setForeground(Color.WHITE);
            cbACPUsername.setBackground(Color.BLACK);
            cbACPUsername.addChangeListener
            (
                    new ChangeListener()
                    {
                        public void stateChanged(ChangeEvent e)
                        {
                            vCheckBoxUpdateSelection(e);
                        }
                    }
            );

        cbACPPassword = new JCheckBox("Update Password?");
            cbACPPassword.setForeground(Color.WHITE);
            cbACPPassword.setBackground(Color.BLACK);
            cbACPPassword.addChangeListener
            (
                    new ChangeListener()
                    {
                        public void stateChanged(ChangeEvent e)
                        {
                            vCheckBoxUpdateSelection(e);
                        }
                    }
            );

        cbACPEmail = new JCheckBox("Update Email?");
            cbACPEmail.setForeground(Color.WHITE);
            cbACPEmail.setBackground(Color.BLACK);
            cbACPEmail.addChangeListener
            (
                    new ChangeListener()
                    {
                        public void stateChanged(ChangeEvent e)
                        {
                            vCheckBoxUpdateSelection(e);
                        }
                    }
            );

        cbACPPosition = new JCheckBox("Update Position?");
            cbACPPosition.setForeground(Color.WHITE);
            cbACPPosition.setBackground(Color.BLACK);
            cbACPPosition.addChangeListener
            (
                    new ChangeListener()
                    {
                        public void stateChanged(ChangeEvent e)
                        {
                            vCheckBoxUpdateSelection(e);
                        }
                    }
            );

        // Panel
        panel1.add(cmbACPSelection, "span, center, grow, wrap");
        panel1.add(lblACPOriginalUsername, "wrap");
        panel1.add(tfACPOriginalUsername, "wrap, grow");
        panel1.add(lblACPUsername, "wrap");
        panel1.add(cbACPUsername, "wrap");
        panel1.add(tfACPUsername, "wrap, grow");
        panel1.add(lblACPPassword, "wrap");
        panel1.add(cbACPPassword, "wrap");
        panel1.add(tfACPPassword, "wrap, grow");
        panel1.add(lblACPEmail, "wrap");
        panel1.add(cbACPEmail, "wrap");
        panel1.add(tfACPEmail, "wrap, grow");
        panel1.add(lblACPPosition, "wrap");
        panel1.add(cbACPPosition, "wrap");
        panel1.add(cmbACPPosition, "span, center, grow, wrap");
        panel1.add(btnACPSubmit, "span, split 3");
        panel1.add(btnACPDeleteAllQuery);
        panel1.add(btnACPViewQuery);

        getContentPane().add(panel1);

        // Default Setting
        cmbACPSelection.setSelectedIndex(0);
        cmbACPPosition.setSelectedIndex(0);

        // Default Main
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
        //setLocationRelativeTo(null);
        setTitle("Admin Control Panel");
        setIconImage(ACPAdminPNG.getImage());
    }

    private String $ACPPosition = "";
    private void cmbACPPosition_actionPerformed(ActionEvent e)
    {
        // cmbACPPosition
        if(cmbACPPosition.getSelectedIndex() == 0) // Select an option
        {
            $ACPPosition = "";
        }
        else if(cmbACPPosition.getSelectedIndex() == 1) // User
        {
            $ACPPosition = "User";
        }
        else if(cmbACPPosition.getSelectedIndex() == 2) // Admin
        {
            $ACPPosition = "Admin";
        }
    }

    private void cmbACPSelection_actionPerformed(ActionEvent e)
    {
        if(cmbACPSelection.getSelectedIndex() == 0) // Select an option
        {
            cbACPUsername.setVisible(false);
            cbACPPassword.setVisible(false);
            cbACPEmail.setVisible(false);
            cbACPPosition.setVisible(false);

            tfACPOriginalUsername.setEditable(false);
            tfACPUsername.setEditable(false);
            tfACPPassword.setEditable(false);
            tfACPEmail.setEditable(false);
            cmbACPPosition.setEnabled(false);

            tfACPOriginalUsername.setText("");
            tfACPUsername.setText("");
            tfACPPassword.setText("");
            tfACPEmail.setText("");

            tfACPOriginalUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfACPUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfACPPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfACPEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
            cmbACPPosition.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        else if(cmbACPSelection.getSelectedIndex() == 1) // Insert Query
        {
            cbACPUsername.setVisible(false);
            cbACPPassword.setVisible(false);
            cbACPEmail.setVisible(false);
            cbACPPosition.setVisible(false);

            tfACPOriginalUsername.setEditable(false);
            tfACPUsername.setEditable(true);
            tfACPPassword.setEditable(true);
            tfACPEmail.setEditable(true);
            cmbACPPosition.setEnabled(true);

            tfACPOriginalUsername.setText("");
            tfACPUsername.setText("");
            tfACPPassword.setText("");
            tfACPEmail.setText("");

            tfACPOriginalUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfACPUsername.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfACPPassword.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfACPEmail.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            cmbACPPosition.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
        else if(cmbACPSelection.getSelectedIndex() == 2) // Update Query
        {
            cbACPUsername.setVisible(true);
            cbACPPassword.setVisible(true);
            cbACPEmail.setVisible(true);
            cbACPPosition.setVisible(true);

            tfACPOriginalUsername.setEditable(true);
            tfACPUsername.setEditable(false);
            tfACPPassword.setEditable(false);
            tfACPEmail.setEditable(false);
            cmbACPPosition.setEnabled(false);

            tfACPOriginalUsername.setText("");
            tfACPUsername.setText("");
            tfACPPassword.setText("");
            tfACPEmail.setText("");

            cbACPUsername.setForeground(Color.GREEN);
            cbACPPassword.setForeground(Color.GREEN);
            cbACPEmail.setForeground(Color.GREEN);
            cbACPPosition.setForeground(Color.GREEN);
            tfACPOriginalUsername.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfACPUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfACPPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfACPEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
            cmbACPPosition.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        else if(cmbACPSelection.getSelectedIndex() == 3) // Delete Query
        {
            cbACPUsername.setVisible(false);
            cbACPPassword.setVisible(false);
            cbACPEmail.setVisible(false);
            cbACPPosition.setVisible(false);

            tfACPOriginalUsername.setEditable(true);
            tfACPUsername.setEditable(false);
            tfACPPassword.setEditable(false);
            tfACPEmail.setEditable(false);
            cmbACPPosition.setEnabled(false);

            tfACPOriginalUsername.setText("");
            tfACPUsername.setText("");
            tfACPPassword.setText("");
            tfACPEmail.setText("");

            tfACPOriginalUsername.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            tfACPUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfACPPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
            tfACPEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
            cmbACPPosition.setBorder(BorderFactory.createLineBorder(Color.RED));
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

    private void vInsertQuery(String Username, String Password, String Email, String Position)
    {
        try
        {
            vConnectDB();

            String sql_statement = "INSERT INTO Login VALUES(?,?,?,?)";

            PreparedStatement p1 = conn.prepareStatement(sql_statement);
            p1.setString(1, Username);
            p1.setString(2, Password);
            p1.setString(3, Email);
            p1.setString(4, Position);
            p1.executeUpdate();

            JOptionPane.showMessageDialog(null, "Inserted Query Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    // <editor-fold defaultstate="collapsed" desc="vUpdate_Sections">

    private void vUpdate_UsernamePasswordEmailPosition(String Username, String Password, String Email, String Position, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == true) // Update UsernamePasswordEmailPosition?
            {
                update_statement = "UPDATE Login SET Username = ?, Password = ?, Email = ?, Position = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Username);
                p1.setString(2, Password);
                p1.setString(3, Email);
                p1.setString(4, Position);
                p1.setString(5, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Username, Password, Email, Position Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_PasswordEmailPosition(String Password, String Email, String Position, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == true) // Update PasswordEmailPosition?
            {
                update_statement = "UPDATE Login SET Password = ?, Email = ?, Position = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Password);
                p1.setString(2, Email);
                p1.setString(3, Position);
                p1.setString(4, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Password, Email, Position Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_UsernameEmailPosition(String Username, String Email, String Position, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == true) // Update PasswordEmailPosition?
            {
                update_statement = "UPDATE Login SET Username = ?, Email = ?, Position = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Username);
                p1.setString(2, Email);
                p1.setString(3, Position);
                p1.setString(4, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Username, Email, Position Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_UsernamePasswordPosition(String Username ,String Password, String Position, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == true) // Update PasswordEmailPosition?
            {
                update_statement = "UPDATE Login SET Username = ?, Password = ?, Position = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Username);
                p1.setString(2, Password);
                p1.setString(3, Position);
                p1.setString(4, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Username, Password, Position Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_UsernamePasswordEmail(String Username, String Password, String Email, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == false)
            {
                update_statement = "UPDATE Login SET Username = ?, Password = ?, Email = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Username);
                p1.setString(2, Password);
                p1.setString(3, Email);
                p1.setString(4, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Username, Password, Email Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_EmailPosition(String Email, String Position, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == true)
            {
                update_statement = "UPDATE Login SET Email = ?, Position = ?, WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Email);
                p1.setString(2, Position);
                p1.setString(3, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Email & Position Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_PasswordPosition(String Password, String Position, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == true)
            {
                update_statement = "UPDATE Login SET Password = ?, Position = ?, WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Password);
                p1.setString(2, Position);
                p1.setString(3, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Password & Position Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_PasswordEmail(String Password, String Email, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == false)
            {
                update_statement = "UPDATE Login SET Password = ?, Email = ?, WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Password);
                p1.setString(2, Email);
                p1.setString(3, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Password & Email Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_UsernamePosition(String Username, String Position, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == true)
            {
                update_statement = "UPDATE Login SET Username = ?, Position = ?, WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Username);
                p1.setString(2, Position);
                p1.setString(3, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Username & Position Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_UsernameEmail(String Username, String Email, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == false)
            {
                update_statement = "UPDATE Login SET Username = ?, Email = ?, WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Username);
                p1.setString(2, Email);
                p1.setString(3, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Username & Email Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_UsernamePassword(String Username, String Password, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == false)
            {
                update_statement = "UPDATE Login SET Username = ?, Password = ?, WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Username);
                p1.setString(2, Password);
                p1.setString(3, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Username & Password Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_Position(String Position, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == true)
            {
                update_statement = "UPDATE Login SET Position = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Position);
                p1.setString(2, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Position Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void vUpdate_Email(String Email, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == false)
            {
                update_statement = "UPDATE Login SET Email = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Email);
                p1.setString(2, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Email Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_Password(String Password, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == false)
            {
                update_statement = "UPDATE Login SET Password = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Password);
                p1.setString(2, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Password Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vUpdate_Username(String Username, String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == false)
            {
                update_statement = "UPDATE Login SET Username = ? WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, Username);
                p1.setString(2, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Username Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // </editor-fold>

    private void vUpdateQuery(String OriginalUsername, String Username, String Password, String Email, String Position)
    {
        try
        {
            if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == true) 
            {
                vUpdate_UsernamePasswordEmailPosition(Username, Password, Email, Position, OriginalUsername); // Update UsernamePassowordEmailPosition
            }
            else if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == true) 
            {
                vUpdate_PasswordEmailPosition(Password, Email, Position, OriginalUsername); // Update PasswordEmailPosition
            }
            else if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == true) 
            {
                vUpdate_UsernameEmailPosition(Username, Email, Position, OriginalUsername); // Update UserEmailPosition
            }
            else if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == true)
            {
                vUpdate_UsernamePasswordPosition(Username, Password, Position, OriginalUsername); // Update UsernamePassowrdPosition
            }
            else if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == false)
            {
                vUpdate_UsernamePasswordEmail(Username, Password, Email, OriginalUsername); // Update UsernamePasswordEmail
            }
            else if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == true)
            {
                vUpdate_EmailPosition(Email, Position, OriginalUsername); // Update EmailPosition
            }
            else if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == true)
            {
                vUpdate_PasswordPosition(Password, Position, OriginalUsername); // Update PasswordPosition
            }
            else if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == false)
            {
                vUpdate_PasswordEmail(Password, Email, OriginalUsername); // Update PasswordEmail
            }
            else if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == true)
            {
                vUpdate_UsernamePosition(Username, Position, OriginalUsername); // Update UserPosition
            }
            else if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == false)
            {
                vUpdate_UsernameEmail(Username, Email, OriginalUsername); // Update UsernameEmail
            }
            else if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == false)
            {
                vUpdate_UsernamePassword(Username, Password, OriginalUsername); // Update UsernamePassword
            }
            else if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == true)
            {
                vUpdate_Position(Position, OriginalUsername); // Update Position
            }
            else if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == true && cbACPPosition.isSelected() == false)
            {
                vUpdate_Email(Email, OriginalUsername); // Update Email?
            }
            else if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == true && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == false)
            {
                vUpdate_Password(Password, OriginalUsername); // Update Password?
            }
            else if(cbACPUsername.isSelected() == true && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == false)
            {
                vUpdate_Username(Password, OriginalUsername); // Update Username?
            }
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

    private void vDeleteQuery(String OriginalUsername)
    {
        try
        {
            vConnectDB();

            if(!tfACPOriginalUsername.getText().equals(""))
            {
                update_statement = "DELETE FROM Login WHERE Username = ?";

                PreparedStatement p1 = conn.prepareStatement(update_statement);
                p1.setString(1, OriginalUsername);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Query Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vCheckBoxUpdateSelection(ChangeEvent e) // CheckBox Changed
    {
        try
        {
            if(cbACPUsername.isSelected() == true) // CheckBox Username
            {
                tfACPUsername.setEditable(true);
                tfACPUsername.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }
            else
            {
                tfACPUsername.setEditable(false);
                tfACPUsername.setText("");
                tfACPUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
            }

            if(cbACPPassword.isSelected() == true) // CheckBox Password
            {
                tfACPPassword.setEditable(true);
                tfACPPassword.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }
            else
            {
                tfACPPassword.setEditable(false);
                tfACPPassword.setText("");
                tfACPPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
            }

            if(cbACPEmail.isSelected() == true) // CheckBox Email
            {
                tfACPEmail.setEditable(true);
                tfACPEmail.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }
            else
            {
                tfACPEmail.setEditable(false);
                tfACPEmail.setText("");
                tfACPEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
            }

            if(cbACPPosition.isSelected() == true) // CheckBox Position
            {
                cmbACPPosition.setEnabled(true);
                cmbACPPosition.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }
            else
            {
                cmbACPPosition.setEnabled(false);
                cmbACPPosition.setSelectedIndex(0);
                cmbACPPosition.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }
        catch(Exception e1)
        {
            JOptionPane.showMessageDialog(null, e1, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnACPSubmit_actionPerformed(ActionEvent e)
    {
        if(cmbACPSelection.getSelectedIndex() == 0) // Please select an option
        {
            JOptionPane.showMessageDialog(null, "Please select an option!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(cmbACPSelection.getSelectedIndex() == 1) // Insert Query
        {
            tfACPOriginalUsername.setText("");
            if(tfACPUsername.getText().equals("")) 
            {
                JOptionPane.showMessageDialog(null, "Username TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(tfACPPassword.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Password TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(tfACPEmail.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Email TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(cmbACPPosition.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(null, "Please select an option! [Position]", "Positon", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                vInsertQuery(tfACPUsername.getText(), tfACPPassword.getText(), tfACPEmail.getText(), $ACPPosition);
            }
        }
        else if(cmbACPSelection.getSelectedIndex() == 2) // Update Query
        {
            if(tfACPOriginalUsername.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Original Username TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(cbACPUsername.isSelected() == false && cbACPPassword.isSelected() == false && cbACPEmail.isSelected() == false && cbACPPosition.isSelected() == false)
            {
                JOptionPane.showMessageDialog(null, "At least one checkBox should be checked!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                vUpdateQuery(tfACPOriginalUsername.getText(), tfACPUsername.getText(), tfACPPassword.getText(), tfACPEmail.getText(), $ACPPosition);
            }
        }
        else if(cmbACPSelection.getSelectedIndex() == 3) // Delete Query
        {
            if(tfACPOriginalUsername.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Original Username TextField should not be blank!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                vDeleteQuery(tfACPOriginalUsername.getText());
            }
        }
    }

    private void btnACPDeleteAllQuery_actionPerformed(ActionEvent e) // Delete All User & Admin - Master won't be deleted
    {
        try
        {
            vConnectDB();

            String deleteAllQuery_statement = "DELETE FROM Login WHERE Position = \'Admin\' OR Position = \'User\'";

            int iReply = JOptionPane.showConfirmDialog(null, "Confirm permanently remove all query?", "Warning!!!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(iReply == JOptionPane.YES_OPTION)
            {
                statement = conn.createStatement();
                statement.executeUpdate(deleteAllQuery_statement);

                JOptionPane.showMessageDialog(null, "All Query Removed Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e1)
        {
            JOptionPane.showMessageDialog(null, e1, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try
            {
                if(conn != null);
                    conn.close();
            }
            catch(Exception e1)
            {
                JOptionPane.showMessageDialog(null, e1, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnACPViewQuery_actionPerformed(ActionEvent e)
    {
        Form1_Login_DatabaseQuery form1_DatabaseQuery = new Form1_Login_DatabaseQuery();
        form1_DatabaseQuery.setVisible(true);
    }
}