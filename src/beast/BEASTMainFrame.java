/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beast;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.List;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import beast.framework.PluginListener;
import beast.framework.PluginManager;


/**
 *
 * @author daniel.jones
 */
public class BEASTMainFrame extends JFrame {

    private JMenu toolbar;
    private PluginDisplayPanel displayPluginPanel;
    private JDesktopPane jdesk;
    private OASessionHelper oaSessionHelper;
    private List registeredListeners = new ArrayList();

    private JFileChooser fileChooser;

    public PluginDisplayPanel getDisplayPluginPanel() {
        return displayPluginPanel;
    }

    public JDesktopPane getJdesk() {
        return jdesk;
    }

    public void setJdesk(JDesktopPane jdesk) {
        this.jdesk = jdesk;
    }

    public OASessionHelper getOaSessionHelper() {
        return oaSessionHelper;
    }

    public void setOaSessionHelper(OASessionHelper oaSessionHelper) {
        this.oaSessionHelper = oaSessionHelper;
    }

  

    public void PanelSwitcher(Component panelShow, Component panelHide) {
        panelHide.setVisible(false);
        panelShow.setVisible(true);
        BEASTMainFrame.this.remove(panelHide);
        BEASTMainFrame.this.add(panelShow, BorderLayout.CENTER);
        this.repaint();
    }
    

    public BEASTMainFrame() {

        super("BEAST");
        setLayout(new BorderLayout());

        toolbar = new JMenu();
        displayPluginPanel = new PluginDisplayPanel(BEASTMainFrame.this);
        jdesk = new JDesktopPane();
        fileChooser = new JFileChooser();

        setJMenuBar(createToolbar());
        displayPluginPanel.createInitialPluginButtons();
        
        add(displayPluginPanel, BorderLayout.CENTER);
        jdesk.addContainerListener(new ContainerListener() {

            @Override
            public void componentAdded(ContainerEvent ce) {
                PanelSwitcher(jdesk, displayPluginPanel);
                repaint();
            }

            @Override
            public void componentRemoved(ContainerEvent ce) {
                
            }
        });
    
        //add(workSpace, BorderLayout.CENTER);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public synchronized void addPluginListener( PluginListener l ) {
        registeredListeners.add( l );
        System.out.println("Adding Listener");    
    }
    
    public synchronized void removePluginListener( PluginListener l ) {
        registeredListeners.remove( l );
    }
    
    private JMenuBar createToolbar() {
        JMenuBar menuBar = new JMenuBar();
        //FILE MENU
        JMenu fileMenu = new JMenu("File");
        JMenuItem installPlugin = new JMenuItem("Install Plugin...");
        JMenuItem uninstallPlugin = new JMenuItem("Remove Plugin...");
        JMenuItem logonAPAS = new JMenuItem("Login to APAS...");
        JMenuItem exitBEAST = new JMenuItem("Exit");

        fileMenu.add(installPlugin);
        fileMenu.add(uninstallPlugin);
        fileMenu.addSeparator();
        fileMenu.add(logonAPAS);
        fileMenu.addSeparator();
        fileMenu.add(exitBEAST);

        //INSTALL PLUGIN MENU ITEM LISTENER LOGIC
        installPlugin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(BEASTMainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    PluginManager.InstallPlugin(fileChooser.getSelectedFile().getAbsolutePath());
                    displayPluginPanel.createInitialPluginButtons();
                    PanelSwitcher(displayPluginPanel, jdesk);
                };
            }
        });

        //UNINSTALL PLUGIN MENU ITEM LISTENER LOGIC
        uninstallPlugin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(BEASTMainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    PluginManager.RemovePlugin(fileChooser.getSelectedFile().getAbsolutePath());
                };
            }
        });
        //LOGON APAS MENU ITEM LISTENER LOGIC
        logonAPAS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // int action = JOptionPane.showConfirmDialog(BEASTMainFrame.this, "Do you reall want to exit", "Confirm Close", JOptionPane.OK_CANCEL_OPTION);
                if (logonAPAS.getText() == "Login to APAS...") {
                    LoginDialog loginDialog = new LoginDialog(BEASTMainFrame.this);

                    loginDialog.setVisible(true);
                    if (loginDialog.isSucceeded()) {
                        logonAPAS.setText("Log Off APAS...");
                    }

                }
            }
        });
        //EXIT MENU ITEM LISTENER LOGIC
        exitBEAST.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(BEASTMainFrame.this, "Do you really want to exit", "Confirm Close", JOptionPane.OK_CANCEL_OPTION);

                if (action == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        //EDIT MENU
        JMenu editMenu = new JMenu("Edit");

        //PLUGINS MENU
        JMenu pluginsMenu = new JMenu("Plugins");
        JCheckBoxMenuItem displayPluginFolder = new JCheckBoxMenuItem("Display Plugin List...");
        displayPluginFolder.setSelected(true);
        pluginsMenu.add(displayPluginFolder);

        //DISPLAY PLUGIN LIST MENU ITEM LISTENER LOGIC
        displayPluginFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
                displayPluginPanel.setVisible(menuItem.isSelected());
                if (!menuItem.isSelected()) {
                    PanelSwitcher(jdesk, displayPluginPanel);
                } else {
                    PanelSwitcher(displayPluginPanel, jdesk);
                }
            }
        });

        //OPTIONS MENU
        JMenu optionsMenu = new JMenu("Options");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(pluginsMenu);
        menuBar.add(optionsMenu);

        return menuBar;
    }

}
