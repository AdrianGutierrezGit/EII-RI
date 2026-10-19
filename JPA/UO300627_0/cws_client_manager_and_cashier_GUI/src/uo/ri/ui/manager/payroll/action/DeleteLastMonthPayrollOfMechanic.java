package uo.ri.ui.manager.payroll.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.util.exception.BusinessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteLastMonthPayrollOfMechanic extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txId;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DeleteLastMonthPayrollOfMechanic dialog =
                                                    new DeleteLastMonthPayrollOfMechanic();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DeleteLastMonthPayrollOfMechanic() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JPanel panel = new JPanel();
            panel.setBackground(new Color(255, 255, 255));
            panel.setBorder(new TitledBorder(null,
                    "Enter the id of the mechanic whose payroll you want to delete.:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBounds(34, 40, 368, 132);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lbId = new JLabel("Mechanic id:");
                lbId.setFont(new Font("Tahoma", Font.PLAIN, 12));
                lbId.setBounds(29, 54, 73, 12);
                panel.add(lbId);
            }
            {
                txId = new JTextField();
                txId.setBounds(112, 52, 222, 18);
                panel.add(txId);
                txId.setColumns(10);
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
                            deletePayrollsOfMechanic();
                        } catch (BusinessException exception) {
                            JOptionPane.showMessageDialog(null,
                                    exception.getMessage(),
                                    "Error deleting payrolls",
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

    protected void deletePayrollsOfMechanic() throws BusinessException {
        PayrollService service = Factories.service.forPayrollService();
        service.deleteLastGeneratedOfMechanicId(txId.getText());
        JOptionPane.showMessageDialog(this,
                "Last month's payroll for the mechanic deleted",
                "Payrolls deleted", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

}
