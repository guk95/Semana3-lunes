package mysql4java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public final class SQL extends funciones {

    private final String DATABASE_URL = "jdbc:mysql://nanosoft.tech:3306/nanosoft_ulatina";
    private final String USERNAME = "nanosoft_ulatina";
    private final String PASSWORD = "guido17";

    private Connection connection;
    private Properties properties;

    public SQL() {
        super();
        this.connect();
    }

    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
        }
        return properties;
    }

    // connect database
    public Connection connect() {
        if (this.connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection(this.DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public boolean Exists(HashMap<String, Object> result) {
        return !(result == null || result.isEmpty());
    }

    public boolean exec(String preparedQuery, ArrayList<Object> objs) {
        PreparedStatement ps;
        try {
            ps = this.connection.prepareStatement(preparedQuery);

            Iterator it = objs.iterator();
            int poc = 1;
            while (it.hasNext()) {
                Object element = it.next();
                if (this.isNumeric(element)) {
                    ps.setInt(poc, Integer.parseInt(element.toString()));
                } else {
                    ps.setString(poc, element.toString());
                }
                poc++;
            }
            System.out.println(preparedQuery);
            System.out.println(objs);
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
    }



    public HashMap<String, Object> SELECT(String preparedQuery, ArrayList<Object> objs) {
        HashMap<String, Object> hmap = new HashMap<>();

        PreparedStatement ps;
        try {
            ps = this.connection.prepareStatement(preparedQuery);

            Iterator it = objs.iterator();
            int poc = 1;
            while (it.hasNext()) {
                Object element = it.next();
                if (this.isNumeric(element)) {
                    ps.setInt(poc, Integer.parseInt(element.toString()));
                } else {
                    ps.setString(poc, element.toString());
                }
                poc++;
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        hmap.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                    }
                }
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            hmap = null;
        }
        return hmap;
    }

//    public static DefaultTableModel buildTableModel(ResultSet rs)
//            throws SQLException {
//
//        ResultSetMetaData metaData = rs.getMetaData();
//
//        // names of columns
//        Vector<String> columnNames = new Vector<String>();
//        int columnCount = metaData.getColumnCount();
//        for (int column = 1; column <= columnCount; column++) {
//            columnNames.add(metaData.getColumnName(column));
//        }
//
//        // data of the table
//        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
//        while (rs.next()) {
//            Vector<Object> vector = new Vector<Object>();
//            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//                vector.add(rs.getObject(columnIndex));
//            }
//            data.add(vector);
//        }
//
//        return new DefaultTableModel(data, columnNames);
//
//    }

    public void FillTable(JTable table, String Query) {

        PreparedStatement ps;
        try {
            ps=this.connection.prepareStatement(Query);

            ResultSet rs = ps.executeQuery(Query);

            //To remove previously added rows
            while (table.getRowCount() > 0) {
                ((DefaultTableModel) table.getModel()).removeRow(0);
            }
            int columns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                ((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
            }

        } catch (SQLException e) {
            System.out.println("Error"+e);
        }
    }
}
