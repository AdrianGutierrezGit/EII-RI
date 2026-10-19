package uo.ri.ui.manager.contracttype.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.exception.BusinessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

public class UpdateContractType extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txName;
    private JTextField txCompensationDays;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UpdateContractType dialog = new UpdateContractType();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UpdateContractType() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null,
                    "Enter the name of the contract type:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(52, 37, 315, 61);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lnName = new JLabel("Name:");
                lnName.setFont(new Font("Tahoma", Font.PLAIN, 12));
                lnName.setBounds(30, 26, 44, 12);
                panel.add(lnName);
            }
            {
                txName = new JTextField();
                txName.setBounds(84, 24, 196, 18);
                panel.add(txName);
                txName.setColumns(10);
            }
        }
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null,
                    "Enter the new number of compensation days:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(52, 127, 315, 61);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lbCompensationDays = new JLabel("Compensation days:");
                lbCompensationDays.setFont(new Font("Tahoma", Font.PLAIN, 10));
                lbCompensationDays.setBounds(29, 25, 105, 12);
                panel.add(lbCompensationDays);
            }
            {
                txCompensationDays = new JTextField();
                txCompensationDays.setBounds(144, 22, 138, 18);
                panel.add(txCompensationDays);
                txCompensationDays.setColumns(10);
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
                            updateContractType();
                        } catch (BusinessException exception) {
                            JOptionPane.showMessageDialog(null,
                                    exception.getMessage(),
                                    "Error updating contract type",
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

    protected void updateContractType() throws BusinessException {
        ContractTypeCrudService service =
                                        Factories.service.forContractTypeCrudService();
        ContractTypeDto dto = service.findByName(txName.getText())
                                     .orElseThrow(() -> new BusinessException(
                                             "Contract type not found"));

        dto.compensationDays = Double.parseDouble(txCompensationDays.getText());

        service.update(dto);
        JOptionPane.showMessageDialog(this, "Contract type updated",
                "Update contract type", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

}
