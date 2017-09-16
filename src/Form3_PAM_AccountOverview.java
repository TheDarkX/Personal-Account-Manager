import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import net.miginfocom.swing.*; //MigLayout

public class Form3_PAM_AccountOverview extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;

    private JPanel panel1, panelHoldTable, panelHoldManage, panelDeleteData, panelUpdateData;
    private JButton btnPAMAORefresh, btnPAMAODDDelete, btnPAMAODDDeleteAll, btnPAMAOUDUpdate;
    private JTable table1;
    private JScrollPane scroll1;
    private JLabel lblPAMAODDTitle, lblPAMAOUDOldTitle,lblPAMAOUDTitle, lblPAMAOUDUsername, lblPAMAOUDPassword, lblPAMAOUDWebsite, lblPAMAOUDAdditionalInfo;
    private JTextField tfPAMAODDTitle, tfPAMAOUDOldTitle,tfPAMAOUDTitle, tfPAMAOUDUsername, tfPAMAOUDPassword, tfPAMAOUDWebsite;
    private JTextArea taPAMAOUDAdditionalInfo;

    private String $LoggedInUser;

    public Form3_PAM_AccountOverview()
    {
        initComponent();
    }

    private void initComponent()
    {
        panel1 = new JPanel();
        panel1.setLayout(new MigLayout());
        panel1.setBackground(Color.BLACK);

        // Icon
        ImageIcon ManagePNG = new ImageIcon("Manage.png"); // AccountOverviewFavicon
        ImageIcon RefreshPNG = new ImageIcon("Refresh.png"); // btnPAMAORefresh
        ImageIcon TagPNG = new ImageIcon("Tag.png"); // lblPAMAODDName
        ImageIcon DeletePNG = new ImageIcon("DeleteAllQuery.png"); // btnPAMAODDDelete
        ImageIcon DeleteAllPNG = new ImageIcon("DeleteAll.png"); // btnPAMAODDDeleteAll
        ImageIcon UserPNG = new ImageIcon("User.png"); // lblPAMAOUDUsername
        ImageIcon KeyPNG = new ImageIcon("Key.png"); // lblPAMAOUDPassword
        ImageIcon WebsitePNG = new ImageIcon("Website.png"); // lblPAMAOUDWebsite
        ImageIcon PlusPNG = new ImageIcon("Plus.png"); // lblPAMAOUDAdditionalInfor
        ImageIcon RightArrowPNG = new ImageIcon("RightArrow.png"); // btnPAMAOUDUpdate

        // <editor-fold defaultstate="collapsed" desc="HoldTable">
        panelHoldTable = new JPanel();
        panelHoldTable.setLayout(new MigLayout());
        panelHoldTable.setBackground(Color.BLACK);

        // Button
        btnPAMAORefresh = new JButton("Refresh", RefreshPNG);
            btnPAMAORefresh.setForeground(Color.WHITE);
            btnPAMAORefresh.setBackground(Color.BLACK);
            // Flat Button
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnPAMAORefresh.setBorder(compound);
            btnPAMAORefresh.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMAORefresh_actionPerformed(e);
                        }
                    }
            );

        // <editor-fold defaultstate="collapsed" desc="Retrieve Table">
        // Read Query

        Vector columnName = new Vector();
        Vector rowData = new Vector();

        try
        {
            vConnectDB();
            
            ResultSet r1 = null;
            PreparedStatement p1 = null;

            // Grab LoggedInUser for further process
            String mysql_statement = "SELECT * FROM Online";
            p1 = conn.prepareStatement(mysql_statement);
            r1 = p1.executeQuery();

            while(r1.next())
            {
                $LoggedInUser = (String)r1.getObject(1);
            }

            // Read data from a table
            String sql_statement = "SELECT * FROM AccountClass" + " WHERE LoggedInUser='" + $LoggedInUser + "'";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql_statement);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int iColumn = rsMetaData.getColumnCount();

            // Get Column Name
            for(int i = 1; i <= iColumn; i++)
            {
                columnName.addElement(rsMetaData.getColumnName(i));
            }

            // Get Row Data
            while(rs.next())
            {
                Vector row = new Vector(iColumn);

                for(int i = 1; i <= iColumn; i++)
                {
                    row.addElement(rs.getObject(i));
                }

                rowData.addElement(row);
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

        // Create a table with database data
        table1 = new JTable(rowData, columnName)
        {
            public Class getColumnClass(int iColumn)
            {
                for (int i = 0; i < getRowCount(); i++)
                {
                    Object obj = getValueAt(i, iColumn);

                    if (obj != null)
                    {
                        return obj.getClass();
                    }
                }

                return Object.class;
            }
        };

        //ScrollPane
        scroll1 = new JScrollPane(table1);

        // </editor-fold>

        // Panel
        panelHoldTable.add(btnPAMAORefresh, "span, center, wrap");
        panelHoldTable.add(scroll1, "span, grow, push");
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="HoldManage">
        panelHoldManage = new JPanel();
        panelHoldManage.setLayout(new GridLayout(2,1));
        panelHoldManage.setBackground(Color.BLACK);

        panelDeleteData = new JPanel();
        panelDeleteData.setBorder(BorderFactory.createTitledBorder(null, "Delete Data", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panelDeleteData.setLayout(new MigLayout());
        panelDeleteData.setBackground(Color.BLACK);

        panelUpdateData = new JPanel();
        panelUpdateData.setBorder(BorderFactory.createTitledBorder(null, "Update Data", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panelUpdateData.setLayout(new MigLayout());
        panelUpdateData.setBackground(Color.BLACK);

        // Label
        lblPAMAOUDOldTitle = new JLabel("Old Title : ", TagPNG, SwingConstants.LEFT);
            lblPAMAOUDOldTitle.setForeground(Color.WHITE);

        lblPAMAODDTitle = new JLabel("Title : ", TagPNG, SwingConstants.LEFT);
            lblPAMAODDTitle.setForeground(Color.WHITE);

        lblPAMAOUDTitle = new JLabel("Title : ", TagPNG, SwingConstants.LEFT);
            lblPAMAOUDTitle.setForeground(Color.WHITE);

        lblPAMAOUDUsername = new JLabel("Username : ", UserPNG, SwingConstants.LEFT);
            lblPAMAOUDUsername.setForeground(Color.WHITE);

        lblPAMAOUDPassword = new JLabel("Password : ", KeyPNG, SwingConstants.LEFT);
            lblPAMAOUDPassword.setForeground(Color.WHITE);

        lblPAMAOUDWebsite = new JLabel("Website : ", WebsitePNG, SwingConstants.LEFT);
            lblPAMAOUDWebsite.setForeground(Color.WHITE);

        lblPAMAOUDAdditionalInfo = new JLabel("Additional Information : ", PlusPNG, SwingConstants.LEFT);
            lblPAMAOUDAdditionalInfo.setForeground(Color.WHITE);

        // TextField
        tfPAMAODDTitle = new JTextField(20);
            tfPAMAODDTitle.setForeground(Color.WHITE);
            tfPAMAODDTitle.setBackground(Color.BLACK);
            tfPAMAODDTitle.setCaretColor(Color.CYAN);

        tfPAMAOUDOldTitle = new JTextField(20);
            tfPAMAOUDOldTitle.setForeground(Color.WHITE);
            tfPAMAOUDOldTitle.setBackground(Color.BLACK);
            tfPAMAOUDOldTitle.setCaretColor(Color.CYAN);

        tfPAMAOUDTitle = new JTextField(20);
            tfPAMAOUDTitle.setForeground(Color.WHITE);
            tfPAMAOUDTitle.setBackground(Color.BLACK);
            tfPAMAOUDTitle.setCaretColor(Color.CYAN);

        tfPAMAOUDUsername = new JTextField(20);
            tfPAMAOUDUsername.setForeground(Color.WHITE);
            tfPAMAOUDUsername.setBackground(Color.BLACK);
            tfPAMAOUDUsername.setCaretColor(Color.CYAN);

        tfPAMAOUDPassword = new JTextField(20);
            tfPAMAOUDPassword.setForeground(Color.WHITE);
            tfPAMAOUDPassword.setBackground(Color.BLACK);
            tfPAMAOUDPassword.setCaretColor(Color.CYAN);

        tfPAMAOUDWebsite = new JTextField(20);
            tfPAMAOUDWebsite.setForeground(Color.WHITE);
            tfPAMAOUDWebsite.setBackground(Color.BLACK);
            tfPAMAOUDWebsite.setCaretColor(Color.CYAN);

        // TextArea
        taPAMAOUDAdditionalInfo = new JTextArea(10,20);
            taPAMAOUDAdditionalInfo.setForeground(Color.WHITE);
            taPAMAOUDAdditionalInfo.setBackground(Color.BLACK);
            taPAMAOUDAdditionalInfo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            taPAMAOUDAdditionalInfo.setLineWrap(true);
            taPAMAOUDAdditionalInfo.setCaretColor(Color.CYAN);

        // Button
        btnPAMAODDDelete = new JButton("Delete", DeletePNG);
            btnPAMAODDDelete.setForeground(Color.WHITE);
            btnPAMAODDDelete.setBackground(Color.BLACK);
            btnPAMAODDDelete.setBorder(compound);
            btnPAMAODDDelete.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMAODDDelete_actionPerformed(e);
                        }
                    }
            );

        btnPAMAODDDeleteAll = new JButton("Delete All", DeleteAllPNG);
            btnPAMAODDDeleteAll.setForeground(Color.WHITE);
            btnPAMAODDDeleteAll.setBackground(Color.BLACK);
            btnPAMAODDDeleteAll.setBorder(compound);
            btnPAMAODDDeleteAll.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMAODDDeleteAll_actionPerformed(e);
                        }
                    }
            );

        btnPAMAOUDUpdate = new JButton("Update", RightArrowPNG);
            btnPAMAOUDUpdate.setForeground(Color.WHITE);
            btnPAMAOUDUpdate.setBackground(Color.BLACK);
            btnPAMAOUDUpdate.setBorder(compound);
            btnPAMAOUDUpdate.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMAOUDUpdate_actionPerformed(e);
                        }
                    }
            );

        // Panel
        panelDeleteData.add(lblPAMAODDTitle);
        panelDeleteData.add(tfPAMAODDTitle, "wrap");
        panelDeleteData.add(btnPAMAODDDelete, "span, split2, center");
        panelDeleteData.add(btnPAMAODDDeleteAll, "wrap");

        // Panel
        panelUpdateData.add(lblPAMAOUDOldTitle, "right");
        panelUpdateData.add(tfPAMAOUDOldTitle, "wrap");
        panelUpdateData.add(lblPAMAOUDTitle, "right");
        panelUpdateData.add(tfPAMAOUDTitle, "wrap");
        panelUpdateData.add(lblPAMAOUDUsername, "right");
        panelUpdateData.add(tfPAMAOUDUsername, "wrap");
        panelUpdateData.add(lblPAMAOUDPassword, "right");
        panelUpdateData.add(tfPAMAOUDPassword, "wrap");
        panelUpdateData.add(lblPAMAOUDWebsite, "right");
        panelUpdateData.add(tfPAMAOUDWebsite, "wrap");
        panelUpdateData.add(lblPAMAOUDAdditionalInfo, "top, right");
        panelUpdateData.add(taPAMAOUDAdditionalInfo, "grow, push, wrap");
        panelUpdateData.add(btnPAMAOUDUpdate, "span, grow");

        // HoldPanel
        panelHoldManage.add(panelDeleteData, "dock left");
        panelHoldManage.add(panelUpdateData);

        // </editor-fold>

        // Panel
        panel1.add(panelHoldTable);
        panel1.add(panelHoldManage);

        getContentPane().add(panel1);

        // Default Main
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //pack();
        setSize(900,700);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Account Overview");
        setIconImage(ManagePNG.getImage());
    }

    private void vConnectDB()
    {
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "user2", "user2");

            // CREATE TABLE AccountClass(LoggedInUser varchar(255) NOT NULL, Title varchar(255), Username varchar(255) NOT NULL, Password varchar(255) NOT NULL, Website varchar(255) NOT NULL, AdditionalInfo varchar(255) NOT NULL);
            // INSERT INTO AccountClass VALUES('Tester', 'Test', 'Test', 'Test', 'Test.com', 'Testing');
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnPAMAORefresh_actionPerformed(ActionEvent e)
    {
        this.dispose();
        new Form3_PAM_AccountOverview();
    }

    private void btnPAMAODDDelete_actionPerformed(ActionEvent e)
    {
        if(tfPAMAODDTitle.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Title text field should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMAODDDelete(tfPAMAODDTitle.getText());
        }
    }

    private void vPAMAODDDelete(String Title)
    {
        PreparedStatement p1 = null;

        int iSelect = JOptionPane.showConfirmDialog(null, "Are you sure want to delete?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(iSelect == JOptionPane.YES_OPTION)
        {
            try
            {
                vConnectDB();

                String mysql_statement = "DELETE FROM AccountClass WHERE Title = ?";
                p1 = conn.prepareStatement(mysql_statement);
                p1.setString(1, Title);
                p1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    private void btnPAMAODDDeleteAll_actionPerformed(ActionEvent e)
    {
        Statement statement = null;

        int iSelect = JOptionPane.showConfirmDialog(null, "Are you sure want to delete all data?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(iSelect == JOptionPane.YES_OPTION)
        {
            try
            {
                vConnectDB();

                String mysql_statement = "DELETE FROM AccountClass";
                statement = conn.createStatement();
                statement.executeUpdate(mysql_statement);

                JOptionPane.showMessageDialog(null, "All data deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    private void btnPAMAOUDUpdate_actionPerformed(ActionEvent e)
    {
        if(tfPAMAOUDOldTitle.getText().equals("") || tfPAMAOUDTitle.getText().equals("") || tfPAMAOUDUsername.getText().equals("") || tfPAMAOUDPassword.getText().equals("") || tfPAMAOUDWebsite.getText().equals("") || taPAMAOUDAdditionalInfo.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Text fields should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMAOUDUpdate(tfPAMAOUDTitle.getText(), tfPAMAOUDUsername.getText(), tfPAMAOUDPassword.getText(), tfPAMAOUDWebsite.getText(), taPAMAOUDAdditionalInfo.getText(), tfPAMAOUDOldTitle.getText());
        }
    }

    private void vPAMAOUDUpdate(String Title, String Username, String Password, String Website, String AdditionalInfo, String OldTitle)
    {
        PreparedStatement p1 = null;

        try
        {
            vConnectDB();

            String mysql_statement = "UPDATE AccountClass SET Title = ?, Username = ?, Password = ?, Website = ?, AdditionalInfo = ? WHERE Title = ?";
            p1 = conn.prepareStatement(mysql_statement);
            p1.setString(1, Title);
            p1.setString(2, Username);
            p1.setString(3, Password);
            p1.setString(4, Website);
            p1.setString(5, AdditionalInfo);
            p1.setString(6, OldTitle);
            p1.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
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