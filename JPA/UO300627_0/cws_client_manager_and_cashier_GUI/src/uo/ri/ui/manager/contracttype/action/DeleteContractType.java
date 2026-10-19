package uo.ri.ui.manager.contracttype.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.util.exception.BusinessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteContractType extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txName;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DeleteContractType dialog = new DeleteContractType();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DeleteContractType() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 412, 251);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null,
                    "Enter the name of the contract type to delete:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(44, 20, 312, 127);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lbName = new JLabel("Name:");
                lbName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lbName.setBounds(24, 57, 59, 17);
                panel.add(lbName);
            }
            {
                txName = new JTextField();
                txName.setBounds(93, 58, 190, 18);
                panel.add(txName);
                txName.setColumns(10);
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBackground(new Color(255, 255, 255));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            deleteContractType();
                        } catch (BusinessException exception) {
                            JOptionPane.showMessageDialog(null,
                                    exception.getMessage(),
                                    "Error deleting contract type",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

    protected void deleteContractType() throws BusinessException {
        ContractTypeCrudService service =
                                        Factories.service.forContractTypeCrudService();
        service.delete(txName.getText());
        JOptionPane.showMessageDialog(this,
                "The contract type has been deleted", "Delete contract type",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

}
