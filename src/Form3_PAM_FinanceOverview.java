import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import net.miginfocom.swing.*; //MigLayout

public class Form3_PAM_FinanceOverview extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;

    private JPanel panel1, panelHoldTable, panelHoldManage, panelDeleteData, panelUpdateData;
    private JButton btnPAMFORefresh, btnPAMFODDDDelete, btnPAMFODDDeleteAll, btnPAMFOUDUpdate;
    private JTable table1;
    private JScrollPane scroll1;
    private JLabel lblPAMFODDTitle, lblPAMFOUDOldTitle, lblPAMFOUDTitle, lblPAMFOUDPassword, lblPAMFOUDWebsite, lblPAMFOUDAdditionalInfo;
    private JTextField tfPAMFODDTitle, tfPAMFOUDOldTitle, tfPAMFOUDTitle, tfPAMFOUDPassword, tfPAMFOUDWebsite;
    private JTextArea taPAMFOUDAdditionalInfo;

    private String $LoggedInUser;

    public Form3_PAM_FinanceOverview()
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
        btnPAMFORefresh = new JButton("Refresh", RefreshPNG);
            btnPAMFORefresh.setForeground(Color.WHITE);
            btnPAMFORefresh.setBackground(Color.BLACK);
            // Flat Button
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnPAMFORefresh.setBorder(compound);
            btnPAMFORefresh.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMFORefresh_actionPerformed(e);
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
            String sql_statement = "SELECT * FROM FinanceClass" + " WHERE LoggedInUser='" + $LoggedInUser + "'";
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
        panelHoldTable.add(btnPAMFORefresh, "span, center, wrap");
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
        lblPAMFODDTitle = new JLabel("Title : ", TagPNG, SwingConstants.LEFT);
            lblPAMFODDTitle.setForeground(Color.WHITE);

        lblPAMFOUDOldTitle = new JLabel("Old Title : ", TagPNG, SwingConstants.LEFT);
            lblPAMFOUDOldTitle.setForeground(Color.WHITE);

        lblPAMFOUDTitle = new JLabel("Title : ", TagPNG, SwingConstants.LEFT);
            lblPAMFOUDTitle.setForeground(Color.WHITE);

        lblPAMFOUDPassword = new JLabel("Password : ", KeyPNG, SwingConstants.LEFT);
            lblPAMFOUDPassword.setForeground(Color.WHITE);

        lblPAMFOUDWebsite = new JLabel("Website : ", WebsitePNG, SwingConstants.LEFT);
            lblPAMFOUDWebsite.setForeground(Color.WHITE);

        lblPAMFOUDAdditionalInfo = new JLabel("Additional Information : ", PlusPNG, SwingConstants.LEFT);
            lblPAMFOUDAdditionalInfo.setForeground(Color.WHITE);

        // TextField
        tfPAMFODDTitle = new JTextField(20);
            tfPAMFODDTitle.setForeground(Color.WHITE);
            tfPAMFODDTitle.setBackground(Color.BLACK);
            tfPAMFODDTitle.setCaretColor(Color.CYAN);

        tfPAMFOUDOldTitle = new JTextField(20);
            tfPAMFOUDOldTitle.setForeground(Color.WHITE);
            tfPAMFOUDOldTitle.setBackground(Color.BLACK);
            tfPAMFOUDOldTitle.setCaretColor(Color.CYAN);

        tfPAMFOUDTitle = new JTextField(20);
            tfPAMFOUDTitle.setForeground(Color.WHITE);
            tfPAMFOUDTitle.setBackground(Color.BLACK);
            tfPAMFOUDTitle.setCaretColor(Color.CYAN);

        tfPAMFOUDPassword = new JTextField(20);
            tfPAMFOUDPassword.setForeground(Color.WHITE);
            tfPAMFOUDPassword.setBackground(Color.BLACK);
            tfPAMFOUDPassword.setCaretColor(Color.CYAN);

        tfPAMFOUDWebsite = new JTextField(20);
            tfPAMFOUDWebsite.setForeground(Color.WHITE);
            tfPAMFOUDWebsite.setBackground(Color.BLACK);
            tfPAMFOUDWebsite.setCaretColor(Color.CYAN);

        // TextArea
        taPAMFOUDAdditionalInfo = new JTextArea(10,20);
            taPAMFOUDAdditionalInfo.setForeground(Color.WHITE);
            taPAMFOUDAdditionalInfo.setBackground(Color.BLACK);
            taPAMFOUDAdditionalInfo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            taPAMFOUDAdditionalInfo.setLineWrap(true);
            taPAMFOUDAdditionalInfo.setCaretColor(Color.CYAN);

        // Button
        btnPAMFODDDDelete = new JButton("Delete", DeletePNG);
            btnPAMFODDDDelete.setForeground(Color.WHITE);
            btnPAMFODDDDelete.setBackground(Color.BLACK);
            btnPAMFODDDDelete.setBorder(compound);
            btnPAMFODDDDelete.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMFODDDDelete_actionPerformed(e);
                        }
                    }
            );

        btnPAMFODDDeleteAll = new JButton("Delete All", DeleteAllPNG);
            btnPAMFODDDeleteAll.setForeground(Color.WHITE);
            btnPAMFODDDeleteAll.setBackground(Color.BLACK);
            btnPAMFODDDeleteAll.setBorder(compound);
            btnPAMFODDDeleteAll.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMFODDDeleteAll_actionPerformed(e);
                        }
                    }
            );

        btnPAMFOUDUpdate = new JButton("Update", RightArrowPNG);
            btnPAMFOUDUpdate.setForeground(Color.WHITE);
            btnPAMFOUDUpdate.setBackground(Color.BLACK);
            btnPAMFOUDUpdate.setBorder(compound);
            btnPAMFOUDUpdate.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMFOUDUpdate_actionPerformed(e);
                        }
                    }
            );

        // Panel
        panelDeleteData.add(lblPAMFODDTitle);
        panelDeleteData.add(tfPAMFODDTitle, "wrap");
        panelDeleteData.add(btnPAMFODDDDelete, "span, split2, center");
        panelDeleteData.add(btnPAMFODDDeleteAll, "wrap");

        // Panel
        panelUpdateData.add(lblPAMFOUDOldTitle, "right");
        panelUpdateData.add(tfPAMFOUDOldTitle, "wrap");
        panelUpdateData.add(lblPAMFOUDTitle, "right");
        panelUpdateData.add(tfPAMFOUDTitle, "wrap");
        panelUpdateData.add(lblPAMFOUDWebsite, "right");
        panelUpdateData.add(tfPAMFOUDWebsite, "wrap");
        panelUpdateData.add(lblPAMFOUDPassword, "right");
        panelUpdateData.add(tfPAMFOUDPassword, "wrap");
        panelUpdateData.add(lblPAMFOUDAdditionalInfo, "top, right");
        panelUpdateData.add(taPAMFOUDAdditionalInfo, "grow, push, wrap");
        panelUpdateData.add(btnPAMFOUDUpdate, "span, grow");

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
        setSize(900,600);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Finance Overview");
        setIconImage(ManagePNG.getImage());
    }

    private void vConnectDB()
    {
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "user2", "user2");

            // CREATE TABLE FinanceClass(LoggedInUser varchar(255) NOT NULL, Title varchar(255), Website varchar(255) NOT NULL, Password varchar(255) NOT NULL, AdditionalInfo varchar(255) NOT NULL);
            // INSERT INTO FinanceClass VALUES('Tester', 'Test', 'Test', 'Test', 'Test.com');
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnPAMFORefresh_actionPerformed(ActionEvent e)
    {
        this.dispose();
        new Form3_PAM_FinanceOverview();
    }

    private void btnPAMFODDDDelete_actionPerformed(ActionEvent e)
    {
        if(tfPAMFODDTitle.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Text Field should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMFODDDelete(tfPAMFODDTitle.getText());
        }
    }

    private void vPAMFODDDelete(String Title)
    {
        PreparedStatement p1 = null;

        int iSelect = JOptionPane.showConfirmDialog(null, "Are you sure want to delete?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(iSelect == JOptionPane.YES_OPTION)
        {
            try
            {
                vConnectDB();

                String mysql_statement = "DELETE FROM FinanceClass WHERE Title = ?";
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
    
    private void btnPAMFODDDeleteAll_actionPerformed(ActionEvent e)
    {
        Statement s1 = null;
        
        int iSelect = JOptionPane.showConfirmDialog(null, "Are you sure want to delete all data?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(iSelect == JOptionPane.YES_OPTION)
        {
            try
            {
                vConnectDB();
            
                String mysql_statement = "DELETE FROM FinanceClass";
                s1 = conn.createStatement();
                s1.executeUpdate(mysql_statement);

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

    private void btnPAMFOUDUpdate_actionPerformed(ActionEvent e)
    {
        if(tfPAMFOUDOldTitle.getText().equals("") || tfPAMFOUDTitle.getText().equals("") || tfPAMFOUDWebsite.getText().equals("") || tfPAMFOUDPassword.getText().equals("") || taPAMFOUDAdditionalInfo.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Text Fields should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMFOUDUpdate(tfPAMFOUDTitle.getText(), tfPAMFOUDWebsite.getText(), tfPAMFOUDPassword.getText(), taPAMFOUDAdditionalInfo.getText(), tfPAMFOUDOldTitle.getText());
        }
    }

    private void vPAMFOUDUpdate(String Title, String Website, String Password, String AdditionalInfo, String OldTitle)
    {
        PreparedStatement p1 = null;

        try
        {
            vConnectDB();

            String mysql_statement = "UPDATE FinanceClass SET Title = ?, Website = ?, Password = ?, AdditionalInfo = ? WHERE Title = ?";
            p1 = conn.prepareStatement(mysql_statement);
            p1.setString(1, Title);
            p1.setString(2, Website);
            p1.setString(3, Password);
            p1.setString(4, AdditionalInfo);
            p1.setString(5, OldTitle);
            p1.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
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