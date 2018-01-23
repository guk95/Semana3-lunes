/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql4java;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author Telematica-2-0
 */
public final class MySQL4Java extends JFrame {

    private final SQL sql;
    private final NanosoftLayOut ns;
    public JLabel lbl_error_msg = new JLabel();

    public MySQL4Java() {
        this.ns = new NanosoftLayOut(300, 200, 4, 20);
        this.sql = new SQL();

        setTitle("Clase 1 Progamación 3");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(ns.getNanosoftLayOut());
        setPreferredSize(ns.getJFrameDimension());
        setResizable(false);

        initComponents();
    }

    public void initComponents() {
        //delete, update, select informacion a una tabla

        JButton btn = new JButton();

        JTextField txt_username = new JTextField();
        JTextField txt_password = new JTextField();
        JLabel lbl_username = new JLabel();
        JLabel lbl_pass = new JLabel();

        lbl_error_msg.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        lbl_error_msg.setForeground(Color.red);

        Object[][] obj1
                = {
                    {lbl_username, 100, 30, "Nombre"},
                    {txt_username, 100, 30}
                };
        ns.setRow(obj1);

        Object[][] obj
                = {
                    {lbl_pass, 100, 30, "Password"},
                    {txt_password, 100, 30}
                };
        ns.setRow(obj);

        btn.setBounds(ns.getRectangle(200, 30));

        btn.setText("Login");
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                btnMouseClicked(txt_username.getText(), txt_password.getText());
            }
        });

        lbl_error_msg.setBounds(ns.getRectangle(200, 25));

        add(lbl_username);
        add(txt_username);
        add(lbl_pass);
        add(txt_password);
        add(btn);
        add(lbl_error_msg);
        pack();
    }

    public void btnMouseClicked(String nom, String pass) {
        if (nom.isEmpty() || pass.isEmpty()) {
            lbl_error_msg.setText("Empty user or Password");
            return;
        }
        ArrayList<Object> arr = new ArrayList<>();
        arr.addAll(Arrays.asList(nom, pass));

        HashMap<String, Object> result = sql.SELECT("SELECT `idusuario`, `Nombre`, `Password` FROM `Mario_Login` WHERE `Nombre`=? AND `Password`=?", arr);

        /*
        Iterator it = result.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry e = (Map.Entry) it.next();
       }
         */
        if (!sql.Exists(result)) {
            lbl_error_msg.setText("Invalid User or Password");
        } else {
            lbl_error_msg.setForeground(Color.green);
            lbl_error_msg.setText("login correct.");
            Dashboard dsh = new Dashboard();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()
                -> {
            MySQL4Java mySQL4Java = new MySQL4Java();
            mySQL4Java.setVisible(true);
        });

    }

}
