import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import net.miginfocom.swing.*; //MigLayout

public class Form3_PAM_DiaryOverview extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;

    private JPanel panel1, panelHoldTable, panelHoldManage, panelDeleteData, panelUpdateData;
    private JButton btnPAMDORefresh, btnPAMDODDDDelete, btnPAMDODDDeleteAll, btnPAMDOUDUpdate;
    private JTable table1;
    private JScrollPane scroll1;
    private JLabel lblPAMDODDDate, lblPAMDOUDOldDate, lblPAMDOUDDate, lblPAMDOUDWeather, lblPAMDOUDDiary;
    private JTextField tfPAMDODDDate, tfPAMDOUDOldDate, tfPAMDOUDDate, tfPAMDOUDWeather;
    private JTextArea taPAMDOUDDiary;

    private String $LoggedInUser;

    public Form3_PAM_DiaryOverview()
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
        ImageIcon DatePNG = new ImageIcon("Date.png"); // lblPAMDODDDate
        ImageIcon DeletePNG = new ImageIcon("DeleteAllQuery.png"); // btnPAMAODDDelete
        ImageIcon DeleteAllPNG = new ImageIcon("DeleteAll.png"); // btnPAMAODDDeleteAll
        ImageIcon UserPNG = new ImageIcon("User.png"); // lblPAMAOUDUsername
        ImageIcon KeyPNG = new ImageIcon("Key.png"); // lblPAMAOUDPassword
        ImageIcon WebsitePNG = new ImageIcon("Website.png"); // lblPAMAOUDWebsite
        ImageIcon PlusPNG = new ImageIcon("Plus.png"); // lblPAMAOUDAdditionalInfor
        ImageIcon RightArrowPNG = new ImageIcon("RightArrow.png"); // btnPAMAOUDUpdate
        ImageIcon WeatherPNG = new ImageIcon("Weather.png"); // lblPAMDOUDWeather
        ImageIcon DiaryPNG = new ImageIcon("Diary1.png"); // lblPAMDOUDDiary

        // <editor-fold defaultstate="collapsed" desc="HoldTable">
        panelHoldTable = new JPanel();
        panelHoldTable.setLayout(new MigLayout());
        panelHoldTable.setBackground(Color.BLACK);

        // Button
        btnPAMDORefresh = new JButton("Refresh", RefreshPNG);
            btnPAMDORefresh.setForeground(Color.WHITE);
            btnPAMDORefresh.setBackground(Color.BLACK);
            // Flat Button
            Border line = new LineBorder(Color.WHITE);
            Border margin = new EmptyBorder(5,15,5,15);
            Border compound = new CompoundBorder(line, margin);
            btnPAMDORefresh.setBorder(compound);
            btnPAMDORefresh.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMDORefresh_actionPerformed(e);
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
            String sql_statement = "SELECT * FROM DiaryClass" + " WHERE LoggedInUser='" + $LoggedInUser + "'";
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
        panelHoldTable.add(btnPAMDORefresh, "span, center, wrap");
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
        lblPAMDODDDate = new JLabel("Date : ", DatePNG, SwingConstants.LEFT);
            lblPAMDODDDate.setForeground(Color.WHITE);

        lblPAMDOUDOldDate = new JLabel("Old Date : ", DatePNG, SwingConstants.LEFT);
            lblPAMDOUDOldDate.setForeground(Color.WHITE);

        lblPAMDOUDDate = new JLabel("Date : ", DatePNG, SwingConstants.LEFT);
            lblPAMDOUDDate.setForeground(Color.WHITE);

        lblPAMDOUDWeather = new JLabel("Weather : ", WeatherPNG, SwingConstants.LEFT);
            lblPAMDOUDWeather.setForeground(Color.WHITE);

        lblPAMDOUDDiary = new JLabel("Diary : ", DiaryPNG, SwingConstants.LEFT);
            lblPAMDOUDDiary.setForeground(Color.WHITE);

        // TextField
        tfPAMDODDDate = new JTextField(20);
            tfPAMDODDDate.setForeground(Color.WHITE);
            tfPAMDODDDate.setBackground(Color.BLACK);
            tfPAMDODDDate.setCaretColor(Color.CYAN);

        tfPAMDOUDOldDate = new JTextField(20);
            tfPAMDOUDOldDate.setForeground(Color.WHITE);
            tfPAMDOUDOldDate.setBackground(Color.BLACK);
            tfPAMDOUDOldDate.setCaretColor(Color.CYAN);

        tfPAMDOUDDate = new JTextField(20);
            tfPAMDOUDDate.setForeground(Color.WHITE);
            tfPAMDOUDDate.setBackground(Color.BLACK);
            tfPAMDOUDDate.setCaretColor(Color.CYAN);

        tfPAMDOUDWeather = new JTextField(20);
            tfPAMDOUDWeather.setForeground(Color.WHITE);
            tfPAMDOUDWeather.setBackground(Color.BLACK);
            tfPAMDOUDWeather.setCaretColor(Color.CYAN);

        // TextArea
        taPAMDOUDDiary = new JTextArea(10,20);
            taPAMDOUDDiary.setForeground(Color.WHITE);
            taPAMDOUDDiary.setBackground(Color.BLACK);
            taPAMDOUDDiary.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            taPAMDOUDDiary.setLineWrap(true);
            taPAMDOUDDiary.setCaretColor(Color.CYAN);

        // Button
        btnPAMDODDDDelete = new JButton("Delete", DeletePNG);
            btnPAMDODDDDelete.setForeground(Color.WHITE);
            btnPAMDODDDDelete.setBackground(Color.BLACK);
            btnPAMDODDDDelete.setBorder(compound);
            btnPAMDODDDDelete.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMDODDDDelete_actionPerformed(e);
                        }
                    }
            );

        btnPAMDODDDeleteAll = new JButton("Delete All", DeleteAllPNG);
            btnPAMDODDDeleteAll.setForeground(Color.WHITE);
            btnPAMDODDDeleteAll.setBackground(Color.BLACK);
            btnPAMDODDDeleteAll.setBorder(compound);
            btnPAMDODDDeleteAll.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMDODDDeleteAll_actionPerformed(e);
                        }
                    }
            );

        btnPAMDOUDUpdate = new JButton("Update", RightArrowPNG);
            btnPAMDOUDUpdate.setForeground(Color.WHITE);
            btnPAMDOUDUpdate.setBackground(Color.BLACK);
            btnPAMDOUDUpdate.setBorder(compound);
            btnPAMDOUDUpdate.addActionListener
            (
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            btnPAMDOUDUpdate_actionPerformed(e);
                        }
                    }
            );

        // Panel
        panelDeleteData.add(lblPAMDODDDate);
        panelDeleteData.add(tfPAMDODDDate, "wrap");
        panelDeleteData.add(btnPAMDODDDDelete, "span, split2, center");
        panelDeleteData.add(btnPAMDODDDeleteAll, "wrap");

        // Panel
        panelUpdateData.add(lblPAMDOUDOldDate, "right");
        panelUpdateData.add(tfPAMDOUDOldDate, "wrap");
        panelUpdateData.add(lblPAMDOUDDate, "right");
        panelUpdateData.add(tfPAMDOUDDate, "wrap");
        panelUpdateData.add(lblPAMDOUDWeather, "right");
        panelUpdateData.add(tfPAMDOUDWeather, "wrap");
        panelUpdateData.add(lblPAMDOUDDiary, "top, right");
        panelUpdateData.add(taPAMDOUDDiary, "grow, push, wrap");
        panelUpdateData.add(btnPAMDOUDUpdate, "span, grow");

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
        setTitle("Diary Overview");
        setIconImage(ManagePNG.getImage());
    }

    private void vConnectDB()
    {
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "user2", "user2");

            // CREATE TABLE DiaryClass(LoggedInUser varchar(255), Date varchar(255), Weather varchar(255), Diary LONGTEXT);
            // INSERT INTO DiaryClass VALUES('E', '1/1/2013', 'Rainy', 'Bored');
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnPAMDORefresh_actionPerformed(ActionEvent e)
    {
        this.dispose();
        new Form3_PAM_DiaryOverview();
    }

    private void btnPAMDODDDDelete_actionPerformed(ActionEvent e)
    {
        if(tfPAMDODDDate.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Text Field should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMDODDDelete(tfPAMDODDDate.getText());
        }
    }

    private void vPAMDODDDelete(String Date)
    {
        PreparedStatement p1 = null;

        int iSelect = JOptionPane.showConfirmDialog(null, "Are you sure want to delete?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(iSelect == JOptionPane.YES_OPTION)
        {
            try
            {
                vConnectDB();

                String mysql_statement = "DELETE FROM DiaryClass WHERE Date = ?";
                p1 = conn.prepareStatement(mysql_statement);
                p1.setString(1, Date);
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
    
    private void btnPAMDODDDeleteAll_actionPerformed(ActionEvent e)
    {
        Statement s1 = null;
        
        int iSelect = JOptionPane.showConfirmDialog(null, "Are you sure want to delete all data?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(iSelect == JOptionPane.YES_OPTION)
        {
            try
            {
                vConnectDB();
            
                String mysql_statement = "DELETE FROM DiaryClass";
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

    private void btnPAMDOUDUpdate_actionPerformed(ActionEvent e)
    {
        if(tfPAMDOUDOldDate.getText().equals("") || tfPAMDOUDDate.getText().equals("") || tfPAMDOUDWeather.getText().equals("") || taPAMDOUDDiary.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Text Fields should not be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            vPAMFOUDUpdate(tfPAMDOUDDate.getText(), tfPAMDOUDWeather.getText(), taPAMDOUDDiary.getText(), tfPAMDOUDOldDate.getText());
        }
    }

    private void vPAMFOUDUpdate(String Date, String Weather, String Diary, String OldDate)
    {
        PreparedStatement p1 = null;

        try
        {
            vConnectDB();

            String mysql_statement = "UPDATE DiaryClass SET Date = ?, Weather = ?, Diary = ? WHERE Date = ?";
            p1 = conn.prepareStatement(mysql_statement);
            p1.setString(1, Date);
            p1.setString(2, Weather);
            p1.setString(3, Diary);
            p1.setString(4, OldDate);
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
