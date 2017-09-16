import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import net.miginfocom.swing.*; //MigLayout

public class Form3_PAM extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;

    protected String $LoggedInUser;

    private JTabbedPane tab1;
    private JPanel panel1;
    private JButton btnPAMLogout;

    // PanelAccount Component
    private JPanel panelAccount;
    private JLabel lblPAMP1Name, lblPAMP1Username, lblPAMP1Password, lblPAMP1Website, lblPAMP1AdditionalInfo;
    private JTextField tfPAMP1Title, tfPAMP1Username, tfPAMP1Password, tfPAMP1Website;
    private JTextArea taPAMP1AdditionalInfo;
    private JScrollPane scrollPAMP1AdditionalInfo;
    private JButton btnPAMP1AddToList, btnPAMP1Manage;

    // PanelFinance Component
    private JPanel panelFinance;
    private JLabel lblPAMP2Title, lblPAMP2Website, lblPAMP2Password, lblPAMP2AdditionalInfo;
    private JTextField tfPAMP2Title, tfPAMP2Website, tfPAMP2Password;
    private JTextArea taPAMP2AdditionalInfo;
    private JButton btnPAMP2AddToList, btnPAMP2Manage;

    // PanelDiary Component
    private JPanel panelDiary;
    private JLabel lblPAMP3Date, lblPAMP3Weather;
    private JTextField tfPAMP3Date, tfPAMP3Weather;
    private JTextArea taPAMP3Diary;
    private JButton btnPAMP3AddToList, btnPAMP3Manage;

    public Form3_PAM()
    {
        initComponent();
    }

    private void initComponent()
    {
        panel1 = new JPanel();
        panel1.setLayout(new MigLayout());
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Personal Account Manager", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panel1.setBackground(Color.BLACK);

        // Icon
        ImageIcon PAMFaviconPNG = new ImageIcon("Home.png"); // PAM Favicon
        ImageIcon LogoutPNG = new ImageIcon("Logout.png"); // btnPAMLogout
        ImageIcon Tab1PNG = new ImageIcon("Add.png"); // Tab1 Icon
        ImageIcon Tab2PNG = new ImageIcon("FinanceAdd.png"); // Tab2 Icon
        ImageIcon Tab3PNG = new ImageIcon("Diary.png"); // Tab3 Icon
        ImageIcon TagPNG = new ImageIcon("Tag.png"); // lblPAMP1Name
        ImageIcon UserPNG = new ImageIcon("User.png"); // lblPAMP1Username
        ImageIcon KeyPNG = new ImageIcon("Key.png"); // lblPAMP1Password
        ImageIcon WebsitePNG = new ImageIcon("Website.png"); // lblPAMP1Website
        ImageIcon PlusPNG = new ImageIcon("Plus.png"); // lblPAMP1AdditionalInfo
        ImageIcon Add1PNG = new ImageIcon("Add1.png"); // btnPAMP1AddToList
        ImageIcon ManagePNG = new ImageIcon("Manage.png"); // btnPAMP1ViewQuery
        ImageIcon DatePNG = new ImageIcon("Date.png"); // lblPAMP3Date
        ImageIcon WeatherPNG = new ImageIcon("Weather.png"); // lblPAMP3Weather
        ImageIcon SavePNG = new ImageIcon("Save.png"); // btnPAMP3Save
        ImageIcon RightArrowPNG = new ImageIcon("RightArrow.png"); // btnPAMP3Update
        ImageIcon DeletePNG = new ImageIcon("DeleteAllQuery.png"); // btnPAMP3Delete

        // <editor-fold defaultstate="collapsed" desc="Panel1">
        btnPAMLogout = new JButton("Logout", LogoutPNG);
            btnPAMLogout.setForeground(Color.BLACK);
            btnPAMLogout.setBackground(Color.WHITE);
            //btnPAMLogout.setHorizontalTextPosition(SwingConstants.LEFT);
            /*Flat Button*/
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnPAMLogout.setBorder(compound);
            btnPAMLogout.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMLogout_actionPerformed(e);
                        }
                    }
            );
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="TabAccount">
        panelAccount = new JPanel();
        panelAccount.setLayout(new MigLayout());
        panelAccount.setBorder(BorderFactory.createTitledBorder(null, "Add Account - New Account Settings", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panelAccount.setBackground(Color.BLACK);

        // Label
        lblPAMP1Name = new JLabel("Title : ", TagPNG, SwingConstants.LEFT);
            lblPAMP1Name.setForeground(Color.WHITE);

        lblPAMP1Username = new JLabel("Username : ", UserPNG, SwingConstants.LEFT);
            lblPAMP1Username.setForeground(Color.WHITE);

        lblPAMP1Password = new JLabel("Password : ", KeyPNG, SwingConstants.LEFT);
            lblPAMP1Password.setForeground(Color.WHITE);

        lblPAMP1Website = new JLabel("Website : ", WebsitePNG, SwingConstants.LEFT);
            lblPAMP1Website.setForeground(Color.WHITE);

        lblPAMP1AdditionalInfo = new JLabel("Additional Information : ", PlusPNG, SwingConstants.LEFT);
            lblPAMP1AdditionalInfo.setForeground(Color.WHITE);

        // TextField
        tfPAMP1Title = new JTextField(20);
            tfPAMP1Title.setForeground(Color.WHITE);
            tfPAMP1Title.setBackground(Color.BLACK);
            tfPAMP1Title.setCaretColor(Color.CYAN);

        tfPAMP1Username = new JTextField(20);
            tfPAMP1Username.setForeground(Color.WHITE);
            tfPAMP1Username.setBackground(Color.BLACK);
            tfPAMP1Username.setCaretColor(Color.CYAN);

        tfPAMP1Password = new JTextField(20);
            tfPAMP1Password.setForeground(Color.WHITE);
            tfPAMP1Password.setBackground(Color.BLACK);
            tfPAMP1Password.setCaretColor(Color.CYAN);

        tfPAMP1Website = new JTextField(20);
            tfPAMP1Website.setForeground(Color.WHITE);
            tfPAMP1Website.setBackground(Color.BLACK);
            tfPAMP1Website.setCaretColor(Color.CYAN);

        // TextArea
        taPAMP1AdditionalInfo = new JTextArea(10,20);
            taPAMP1AdditionalInfo.setForeground(Color.WHITE);
            taPAMP1AdditionalInfo.setBackground(Color.BLACK);
            taPAMP1AdditionalInfo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            taPAMP1AdditionalInfo.setLineWrap(true);
            taPAMP1AdditionalInfo.setCaretColor(Color.CYAN);

        // ScrollPane
        scrollPAMP1AdditionalInfo = new JScrollPane();
            scrollPAMP1AdditionalInfo.setViewportView(taPAMP1AdditionalInfo);

        // Button
        btnPAMP1AddToList = new JButton("Add To List", Add1PNG);
            btnPAMP1AddToList.setForeground(Color.WHITE);
            btnPAMP1AddToList.setBackground(Color.BLACK);
            btnPAMP1AddToList.setBorder(compound); // Flat Button
            btnPAMP1AddToList.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMP1AddToList_actionPerformed(e);
                        }
                    }
            );

        btnPAMP1Manage = new JButton("Manage", ManagePNG);
            btnPAMP1Manage.setForeground(Color.WHITE);
            btnPAMP1Manage.setBackground(Color.BLACK);
            btnPAMP1Manage.setBorder(compound); // Flat Button
            btnPAMP1Manage.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMP1Manage_actionPerfored(e);
                        }
                    }
            );

        // Panel
        panelAccount.add(lblPAMP1Name, "right");
        panelAccount.add(tfPAMP1Title, "wrap");
        panelAccount.add(lblPAMP1Username, "right");
        panelAccount.add(tfPAMP1Username, "wrap");
        panelAccount.add(lblPAMP1Password, "right");
        panelAccount.add(tfPAMP1Password, "wrap");
        panelAccount.add(lblPAMP1Website, "right");
        panelAccount.add(tfPAMP1Website, "wrap");
        panelAccount.add(lblPAMP1AdditionalInfo, "right");
        panelAccount.add(scrollPAMP1AdditionalInfo, "wrap");
        panelAccount.add(btnPAMP1AddToList, "skip, split2");
        panelAccount.add(btnPAMP1Manage);

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="TabFinance">
        panelFinance = new JPanel();
        panelFinance.setLayout(new MigLayout());
        panelFinance.setBorder(BorderFactory.createTitledBorder(null, "Add Finance - New Finance Settings", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panelFinance.setBackground(Color.BLACK);

        // Label
        lblPAMP2Title = new JLabel("Title : ", TagPNG, SwingConstants.LEFT);
            lblPAMP2Title.setForeground(Color.WHITE);

        lblPAMP2Website = new JLabel("Website : ", WebsitePNG, SwingConstants.LEFT);
            lblPAMP2Website.setForeground(Color.WHITE);

        lblPAMP2Password = new JLabel("Password : ", KeyPNG, SwingConstants.LEFT);
            lblPAMP2Password.setForeground(Color.WHITE);

        lblPAMP2AdditionalInfo = new JLabel("Additional Information : ", PlusPNG, SwingConstants.LEFT);
            lblPAMP2AdditionalInfo.setForeground(Color.WHITE);

        // TextField
        tfPAMP2Title = new JTextField(20);
            tfPAMP2Title.setForeground(Color.WHITE);
            tfPAMP2Title.setBackground(Color.BLACK);
            tfPAMP2Title.setCaretColor(Color.CYAN);

        tfPAMP2Website = new JTextField(20);
            tfPAMP2Website.setForeground(Color.WHITE);
            tfPAMP2Website.setBackground(Color.BLACK);
            tfPAMP2Website.setCaretColor(Color.CYAN);

        tfPAMP2Password = new JTextField(20);
            tfPAMP2Password.setForeground(Color.WHITE);
            tfPAMP2Password.setBackground(Color.BLACK);
            tfPAMP2Password.setCaretColor(Color.CYAN);

        // TextArea
        taPAMP2AdditionalInfo = new JTextArea(10,20);
            taPAMP2AdditionalInfo.setForeground(Color.WHITE);
            taPAMP2AdditionalInfo.setBackground(Color.BLACK);
            taPAMP2AdditionalInfo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            taPAMP2AdditionalInfo.setLineWrap(true);
            taPAMP2AdditionalInfo.setCaretColor(Color.CYAN);

        // Button
        btnPAMP2AddToList = new JButton("Add To List", Add1PNG);
            btnPAMP2AddToList.setForeground(Color.WHITE);
            btnPAMP2AddToList.setBackground(Color.BLACK);
            btnPAMP2AddToList.setBorder(compound); // Flat Button
            btnPAMP2AddToList.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMP2AddToList_actionPerformed(e);
                        }
                    }
            );

        btnPAMP2Manage = new JButton("Manage", ManagePNG);
            btnPAMP2Manage.setForeground(Color.WHITE);
            btnPAMP2Manage.setBackground(Color.BLACK);
            btnPAMP2Manage.setBorder(compound); // Flat Button
            btnPAMP2Manage.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMP2Manage_actionPerformed(e);
                        }
                    }
            );

        panelFinance.add(lblPAMP2Title, "right");
        panelFinance.add(tfPAMP2Title, "wrap");
        panelFinance.add(lblPAMP2Website, "right");
        panelFinance.add(tfPAMP2Website, "wrap");
        panelFinance.add(lblPAMP2Password, "right");
        panelFinance.add(tfPAMP2Password, "wrap");
        panelFinance.add(lblPAMP2AdditionalInfo, "right");
        panelFinance.add(taPAMP2AdditionalInfo, "wrap");
        panelFinance.add(btnPAMP2AddToList, "skip, span, split2");
        panelFinance.add(btnPAMP2Manage);

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="TabDiary">
        panelDiary = new JPanel();
        panelDiary.setLayout(new MigLayout());
        panelDiary.setBackground(Color.BLACK);

        // Label
        lblPAMP3Date = new JLabel("<html><font color=\"red\">Date* : </font></html>", DatePNG, SwingConstants.LEFT);
            lblPAMP3Date.setForeground(Color.WHITE);

        lblPAMP3Weather = new JLabel("Weather : ", WeatherPNG, SwingConstants.LEFT);
            lblPAMP3Weather.setForeground(Color.WHITE);

        // TextField
        tfPAMP3Date = new JTextField(20);
            tfPAMP3Date.setForeground(Color.WHITE);
            tfPAMP3Date.setBackground(Color.BLACK);
            tfPAMP3Date.setCaretColor(Color.CYAN);

        tfPAMP3Weather = new JTextField(20);
            tfPAMP3Weather.setForeground(Color.WHITE);
            tfPAMP3Weather.setBackground(Color.BLACK);
            tfPAMP3Weather.setCaretColor(Color.CYAN);

        // TextArea
        taPAMP3Diary = new JTextArea(10,20);
            taPAMP3Diary.setForeground(Color.WHITE);
            taPAMP3Diary.setBackground(Color.BLACK);
            taPAMP3Diary.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Diary", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 0)));
            taPAMP3Diary.setLineWrap(true);
            taPAMP3Diary.setCaretColor(Color.CYAN); // Cursor Color

        // Button
        btnPAMP3AddToList = new JButton("Add To List", Add1PNG);
            btnPAMP3AddToList.setForeground(Color.WHITE);
            btnPAMP3AddToList.setBackground(Color.BLACK);
            btnPAMP3AddToList.setBorder(compound); // Flat Button
            btnPAMP3AddToList.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMP3AddToList_actionPerformed(e);
                        }
                    }
            );

        btnPAMP3Manage = new JButton("Manage", ManagePNG);
            btnPAMP3Manage.setForeground(Color.WHITE);
            btnPAMP3Manage.setBackground(Color.BLACK);
            btnPAMP3Manage.setBorder(compound); // Flat Button
            btnPAMP3Manage.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMP3Manage_actionPerformed(e);
                        }
                    }
            );

        // Panel
        panelDiary.add(lblPAMP3Date);
        panelDiary.add(tfPAMP3Date);
        panelDiary.add(lblPAMP3Weather);
        panelDiary.add(tfPAMP3Weather, "wrap");
        panelDiary.add(taPAMP3Diary, "span, grow, push, wrap");
        panelDiary.add(btnPAMP3AddToList, "span, split 2, right");
        panelDiary.add(btnPAMP3Manage);

        // </editor-fold>

        // Tab
        tab1 = new JTabbedPane();
        tab1.addTab("Add Account", Tab1PNG, panelAccount);
        tab1.addTab("Add Finance", Tab2PNG, panelFinance );
        tab1.addTab("Diary", Tab3PNG, panelDiary);

        // Panel
        panel1.add(btnPAMLogout, "right, wrap");
        panel1.add(tab1);

        getContentPane().add(panel1);

        // Default Main
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener
        (
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        int iExit = JOptionPane.showConfirmDialog(null, "Are you sure want to exit?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if(iExit == JOptionPane.YES_OPTION)
                        {
                            vClosedDeleteOnlineQuery(e);
                            System.exit(0);
                        }
                    }
                }
        );
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Personal Account Manager");
        setIconImage(PAMFaviconPNG.getImage());
    }
    
    private void vConnectDB()
    {
        try
        {
            // CREATE TABLE DiaryClass(LoggedInUser varchar(255), Date varchar(255), Weather varchar(255), Diary LONGTEXT);
            // INSERT INTO DiaryClass VALUES('E', '1/1/2013', 'Rainy', 'Bored');

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "user2", "user2");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vClosedDeleteOnlineQuery(WindowEvent e)
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

    //********************************************************************
    // <editor-fold defaultstate="collapsed" desc="TabAccountListener">

    private void btnPAMP1Manage_actionPerfored(ActionEvent e)
    {
        Form3_PAM_AccountOverview form3_AccountOverview = new Form3_PAM_AccountOverview();
        form3_AccountOverview.setVisible(true);
    }

    private void btnPAMP1AddToList_actionPerformed(ActionEvent e)
    {
        if(tfPAMP1Title.getText().equals("") || tfPAMP1Username.getText().equals("") || tfPAMP1Password.getText().equals("") || tfPAMP1Website.getText().equals("") || taPAMP1AdditionalInfo.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "All text fields have to fill in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMP1AddToList(tfPAMP1Title.getText(), tfPAMP1Username.getText(), tfPAMP1Password.getText(), tfPAMP1Website.getText(), taPAMP1AdditionalInfo.getText());
        }
    }

    private void vPAMP1AddToList(String Title, String Username, String Password, String Website, String AdditionalInfo)
    {
        ResultSet r1 = null;
        PreparedStatement p1 = null;
        PreparedStatement p2 = null;

        try
        {
            vConnectDB();

            // Grab LoggedInUser for further process
            String mysql_statement = "SELECT * FROM Online";
            p1 = conn.prepareStatement(mysql_statement);
            r1 = p1.executeQuery();

            while(r1.next())
            {
                $LoggedInUser = (String)r1.getObject(1);
            }

            // (Insert) AddToList
            String mysql_statement1 = "INSERT INTO AccountClass VALUES(?,?,?,?,?,?)";
            p2 = conn.prepareStatement(mysql_statement1);
            p2.setString(1, $LoggedInUser);
            p2.setString(2, Title);
            p2.setString(3, Username);
            p2.setString(4, Password);
            p2.setString(5, Website);
            p2.setString(6, AdditionalInfo);
            p2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Successfully Added To List.", "Successfully!", JOptionPane.INFORMATION_MESSAGE);
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

    // </editor-fold>
    //********************************************************************

    //********************************************************************
    // <editor-fold defaultstate="collapsed" desc="TabFinanceListener">

    private void btnPAMP2Manage_actionPerformed(ActionEvent e)
    {
        Form3_PAM_FinanceOverview form3_FinanceOverview = new Form3_PAM_FinanceOverview();
        form3_FinanceOverview.setVisible(true);
    }

    private void btnPAMP2AddToList_actionPerformed(ActionEvent e)
    {
        if(tfPAMP2Title.getText().equals("") || tfPAMP2Website.getText().equals("") || tfPAMP2Password.getText().equals("") || taPAMP2AdditionalInfo.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Text fields should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMP2AddToList(tfPAMP2Title.getText(), tfPAMP2Website.getText(), tfPAMP2Password.getText(), taPAMP2AdditionalInfo.getText());
        }
    }

    private void vPAMP2AddToList(String Title, String Website, String Password, String AdditionalInfo)
    {
        ResultSet r1 = null;
        PreparedStatement p1 = null;
        PreparedStatement p2 = null;
        try
        {
            vConnectDB();

            // Grab LoggedInUser for further process
            String mysql_statement = "SELECT * FROM Online";
            p1 = conn.prepareStatement(mysql_statement);
            r1 = p1.executeQuery();

            while(r1.next())
            {
                $LoggedInUser = (String)r1.getObject(1);
            }

            // (Insert) Add To List
            String mysql_statement1 = "INSERT INTO FinanceClass VALUES(?,?,?,?,?)";
            p2 = conn.prepareStatement(mysql_statement1);
            p2.setString(1, $LoggedInUser);
            p2.setString(2, Title);
            p2.setString(3, Website);
            p2.setString(4, Password);
            p2.setString(5, AdditionalInfo);
            p2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data inserted.", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    // </editor-fold>
    //********************************************************************

    //********************************************************************
    // <editor-fold defaultstate="collapsed" desc="TabDiaryListener">

    private void btnPAMP3AddToList_actionPerformed(ActionEvent e)
    {
        if(tfPAMP3Date.getText().equals("") || tfPAMP3Weather.getText().equals("") || taPAMP3Diary.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Text Field / Text Area should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMP3AddToList(tfPAMP3Date.getText(), tfPAMP3Weather.getText(), taPAMP3Diary.getText());
        }
    }

    private void vPAMP3AddToList(String Date, String Weather, String Diary)
    {
        ResultSet r1 = null;
        PreparedStatement p1 = null;

        try
        {
            vConnectDB();

            // Grab LoggedInUser for further process
            String mysql_statement = "SELECT * FROM Online";
            p1 = conn.prepareStatement(mysql_statement);
            r1 = p1.executeQuery();

            while(r1.next())
            {
                $LoggedInUser = (String)r1.getObject(1);
            }

            // (Insert) Add To List
            String mysql_statement1 = "INSERT INTO DiaryClass VALUES(?,?,?,?)";
            p1 = conn.prepareStatement(mysql_statement1);
            p1.setString(1, $LoggedInUser);
            p1.setString(2, Date);
            p1.setString(3, Weather);
            p1.setString(4, Diary);
            p1.executeUpdate();

            // Set Values into object
            DiaryClass cls_diary = new DiaryClass();
            cls_diary.setLoggedInUser($LoggedInUser);
            cls_diary.setDate(tfPAMP3Date.getText());
            cls_diary.setWeather(tfPAMP3Weather.getText());
            cls_diary.setDiary(taPAMP3Diary.getText());

            /*
            try
            {
                ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream("FPDiary_S.txt", true));
                OOS.writeObject(cls_diary);
                OOS.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            DiaryClass cls_diary1 = new DiaryClass();
            try
            {
                ObjectInputStream OIS = new ObjectInputStream(new FileInputStream("FPDiary_S.txt"));
                cls_diary1 = (DiaryClass)OIS.readObject();

                OIS.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
             */

            JOptionPane.showMessageDialog(null, "Data Added.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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

    private void btnPAMP3Manage_actionPerformed(ActionEvent e)
    {
        new Form3_PAM_DiaryOverview();
    }

    // </editor-fold>
    //********************************************************************

    private void btnPAMLogout_actionPerformed(ActionEvent e)
    {
        int iExit = JOptionPane.showConfirmDialog(null, "Confirm logout?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(iExit == JOptionPane.YES_OPTION)
        {
            // Close all JFrame/Forms
            for(Window window : Window.getWindows())
            {
                window.dispose();
            }

            Form1_Login form1_Login = new Form1_Login();
            form1_Login.setVisible(true);
        }
    }
}