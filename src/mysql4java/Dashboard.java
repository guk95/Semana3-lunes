/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql4java;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author jorge.vasquez
 */
public class Dashboard extends JFrame {

    public NanosoftLayOut ns;
    private final SQL sql;

    public Dashboard() {
        this.ns = new NanosoftLayOut(800, 600, 4);
        this.sql = new SQL();

        setTitle("Dashboard Module");
        setLayout(ns.getNanosoftLayOut());
        setPreferredSize(ns.getJFrameDimension());
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel Menu = new JPanel();
        JLabel lbl_change = new JLabel();
        JTextField txt_change = new JTextField();
        JLabel lbl_nombre = new JLabel();
        JTextField txt_nombrenuevo = new JTextField();

        JButton btn_updateSQL = new JButton();
        JButton btnupdate = new JButton();
        JButton btnselect = new JButton();
        JButton btndelete = new JButton();
         JTable table = new JTable();
         table.setBounds(1000,1000,1000,1000);
         table.show(true);

        NanosoftLayOut nsPanel = new NanosoftLayOut(160, 600, 4);
        Menu.setLayout(nsPanel.getNanosoftLayOut());
        Menu.setSize(nsPanel.getJFrameDimension());
        Menu.setBackground(new Color(48, 44, 43));

        JButton btn_usuarios = new JButton();
        btn_usuarios.setText("Usuarios");
        btn_usuarios.setBounds(nsPanel.getRectangle(140, 30));

        btn_usuarios.addActionListener((ae) -> {
            NanosoftLayOut nstmp = new NanosoftLayOut(300, 300, 4);
            JFrame tmpFrame = new JFrame();
            tmpFrame.setLayout(nstmp.getNanosoftLayOut());
            tmpFrame.setSize(nstmp.getJFrameDimension());
           
            sql.FillTable(table, "Select* from Mario_Login");
            tmpFrame.add(table);

//            btnUsuarios_mouseClicked();
//            ArrayList<String> cols = new ArrayList<>();
//            cols.add("1");
//            cols.add("2");
//            ArrayList<String> rows = new ArrayList<>();
//            rows.add("rows");
//            rows.add("gg");
//
//            JFrame nn = new JFrame();
//            nn.setSize(100, 100);
//
//            JTable table = new JTable(CreateTableModel(cols, rows));
//            JScrollPane scrollPane = new JScrollPane(table);
//            Object[] obj = {"2", "asdfasdf"};
//            nn.add(scrollPane);
//            nn.setVisible(true);
//            addrow(table, obj);
        });

        JButton btn_update = new JButton();
        btn_update.setText("Update User");
        btn_update.setBounds(nsPanel.getRectangle(140, 30));

        btn_update.addActionListener((ae) -> {
            NanosoftLayOut nstmp = new NanosoftLayOut(300, 300, 4);
            JFrame tmpFrame = new JFrame();
            tmpFrame.setLayout(nstmp.getNanosoftLayOut());
            tmpFrame.setSize(nstmp.getJFrameDimension());

            Object[][] obj
                    = {
                        {lbl_change, 80, 30, "User ID"},
                        {txt_change, 140, 30}
                    };
            nstmp.setRow(obj);

            Object[][] obj1
                    = {
                        {lbl_nombre, 80, 30, "Nuevo nombre"},
                        {txt_nombrenuevo, 140, 30,}
                    };
            nstmp.setRow(obj1);

            btn_updateSQL.setBounds(nstmp.getRectangle(220, 30));
            btn_updateSQL.addActionListener((a) -> {
                ArrayList<Object> objs = new ArrayList<>();
                objs.addAll(Arrays.asList(txt_nombrenuevo.getText(), txt_change.getText()));
                boolean result = sql.exec("UPDATE `Mario_Login` SET `Nombre`=? WHERE `Nombre`=?", objs);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Usuario modificado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }
            });

            tmpFrame.add(lbl_change);
            tmpFrame.add(txt_change);
            tmpFrame.add(lbl_nombre);
            tmpFrame.add(txt_nombrenuevo);
            tmpFrame.add(btn_updateSQL);
            tmpFrame.add(table);

            tmpFrame.setVisible(true);
        });

        Menu.add(btn_usuarios);
        Menu.add(btn_update);
        add(Menu);
        pack();
        setVisible(true);
    }

    public void btnUsuarios_mouseClicked() {

    }

    public void addrow(JTable table, Object[] obj) {
        DefaultTableModel tb = (DefaultTableModel) table.getModel();
        tb.addRow(obj);
    }

    public DefaultTableModel CreateTableModel(ArrayList cols, ArrayList rows) {
        Vector<Object> VCOLS = new Vector<>(cols);
        Vector<Vector<String>> VROWS = new Vector<Vector<String>>();
        Vector<String> tmp = new Vector<>();

        for (int i = 0; i < rows.size(); i++) {
            tmp.add((String) rows.get(i));
        }

        VROWS.add(tmp);

        return new DefaultTableModel(VROWS, VCOLS);
    }

}
