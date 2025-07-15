package com.libreria;

import com.libreria.vista.*;
import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("Sistema Librería – Home");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Productos",  new ProductoPanel());
        tabs.addTab("Clientes",   new ClientePanel());
        tabs.addTab("Facturación",new FacturaPanel());
        tabs.addTab("Caja",       new CajaPanel());

        setContentPane(tabs);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
