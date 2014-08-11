/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beast;

/**
 *
 * @author daniel.jones
 */
import com.fidessa.inf.oa.OAException;
import com.fidessa.inf.oa.OpenAccess;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.border.*;

public class LoginDialog extends JDialog {
   private JTextField tfUsername;
    private JTextField tfSysID;
    private JTextField tfGateway; 
    private JFormattedTextField tfLogonGatewayID;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbGateway;
    private JLabel lbSysID;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
 
    public LoginDialog(BEASTMainFrame parent) {
        super(parent, "APAS Login", true);
        //
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);
 
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbGateway = new JLabel("Gateway: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbGateway, cs);
 
        tfGateway = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tfGateway, cs);
        
        lbSysID = new JLabel("System ID: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbSysID, cs);
 
        tfSysID = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(tfSysID, cs);
        
        lbSysID = new JLabel("Logon Gateway ID: ");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbSysID, cs);
 
        tfLogonGatewayID = new JFormattedTextField(NumberFormat.getInstance());       
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 2;
        panel.add(tfLogonGatewayID, cs);
        
        
        tfSysID.setText("NEFO_DEV");
        tfLogonGatewayID.setText("1000");
        tfGateway.setText("ny1-dmem-nefo-10:17000");
        pfPassword.setText("System.User@NEFO.US");
        tfUsername.setText("System.User@NEFO.US");
        
        btnLogin = new JButton("Login");
 
        btnLogin.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                if (parent.getOaSessionHelper().login(getUsername(), getPassword(),getTfSysID(), getTfGateway(),getTfLogonGatewayID())) {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Hi " + getUsername() + "! You have successfully logged in.",
                            "Login",
                            JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                  //  parent.setLogonGatewayAddresses(getTfGateway());
                  //  parent.setLogonGatewayId(getTfLogonGatewayID());
                  //  parent.setUsername(getUsername());
                  //  parent.setPassword(getPassword());
                  //  parent.setLoggedInAPAS(succeeded);
                    dispose();
                } else {
                  //  parent.setLoggedInAPAS(false);
                }
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getTfSysID() {
        return tfSysID.getText().trim();
    }

    public String[] getTfGateway() {
        String[] GatewayAddress = { tfGateway.getText().trim() };
        return GatewayAddress;
    }
    
    public int getTfLogonGatewayID() {        
        return Integer.parseInt(tfLogonGatewayID.getText().replaceAll(",", ""));
    }
 
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
   
 
    public boolean isSucceeded() {
        return succeeded;
    }
}
