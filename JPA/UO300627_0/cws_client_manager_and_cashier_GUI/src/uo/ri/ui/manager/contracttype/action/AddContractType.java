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

public class AddContractType extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txCompensationDays;
    private JTextField txName;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            AddContractType dialog = new AddContractType();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AddContractType() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 510, 331);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null,
                    "Enter the details of the type of contract to add:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(62, 44, 371, 183);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lbName = new JLabel("Name:");
                lbName.setFont(new Font("Tahoma", Font.PLAIN, 12));
                lbName.setBounds(28, 53, 44, 12);
                panel.add(lbName);
            }
            {
                JLabel lbCompensationDays = new JLabel("Compensation days:");
                lbCompensationDays.setFont(new Font("Tahoma", Font.PLAIN, 12));
                lbCompensationDays.setBounds(28, 112, 118, 12);
                panel.add(lbCompensationDays);
            }
            {
                txCompensationDays = new JTextField();
                txCompensationDays.setBounds(144, 110, 193, 18);
                panel.add(txCompensationDays);
                txCompensationDays.setColumns(10);
            }
            {
                txName = new JTextField();
                txName.setColumns(10);
                txName.setBounds(144, 51, 193, 18);
                panel.add(txName);
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
                            addContractType();
                        } catch (BusinessException exception) {
                            JOptionPane.showMessageDialog(null,
                                    exception.getMessage(),
                                    "Error adding contract type",
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

    protected void addContractType() throws BusinessException {
        ContractTypeCrudService service =
                                        Factories.service.forContractTypeCrudService();
        ContractTypeDto dto = new ContractTypeDto();
        dto.name = txName.getText();
        dto.compensationDays = Double.parseDouble(txCompensationDays.getText());

        service.create(dto);
        JOptionPane.showMessageDialog(this, "Contract type registered",
                "Add contract type", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

}
