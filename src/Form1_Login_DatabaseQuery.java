import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import net.miginfocom.swing.*; //MigLayout

public class Form1_Login_DatabaseQuery extends JFrame
{
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_URL = "jdbc:mysql://localhost/pLogin";
    Connection conn = null;
    Statement statement = null;

    private JPanel panel1;
    private JTable tableDatabaseQuery;
    private JScrollPane scrollDatabaseQuery;

    public Form1_Login_DatabaseQuery()
    {
        initComponent();
    }

    private void initComponent()
    {
        panel1 = new JPanel();
        panel1.setLayout(new MigLayout());
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Database Query Data", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.YELLOW));
        panel1.setBackground(Color.BLACK);

        // Icon
        ImageIcon ViewQueryPNG = new ImageIcon("ViewQuery.png"); // DatabaseQueryFavicon

        // Read Query
        Vector columnName = new Vector();
        Vector rowData = new Vector();

        try
        {
            vConnectDB();

            // Read data from a table
            String sql_statement = "SELECT * FROM Login;";
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
        tableDatabaseQuery = new JTable(rowData, columnName)
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

        // ScrollPane
        scrollDatabaseQuery = new JScrollPane(tableDatabaseQuery);

        // Pabel
        panel1.add(scrollDatabaseQuery);

        getContentPane().add(panel1);

        // Default Main
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
        //setLocationRelativeTo(null);
        setTitle("MySQL Database Query");
        setIconImage(ViewQueryPNG.getImage());
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
}