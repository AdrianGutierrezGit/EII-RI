package uo.ri.ui.manager.mechanic.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.exception.BusinessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

public class DeleteMechanic extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txId;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DeleteMechanic dialog = new DeleteMechanic();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DeleteMechanic() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 360, 224);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(
                    new EtchedBorder(EtchedBorder.LOWERED,
                            new Color(255, 255, 255), new Color(160, 160, 160)),
                    "Enter the id of the mechanic you want to remove:",
                    TitledBorder.LEADING, TitledBorder.TOP, null,
                    new Color(0, 0, 0)));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(40, 25, 264, 105);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lbId = new JLabel("Mechanic id:");
                lbId.setFont(new Font("Tahoma", Font.PLAIN, 10));
                lbId.setBounds(21, 47, 76, 12);
                panel.add(lbId);
            }
            {
                txId = new JTextField();
                txId.setBounds(88, 44, 155, 18);
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
                            deleteMechanic();
                        } catch (BusinessException exception) {
                            JOptionPane.showMessageDialog(null,
                                    exception.getMessage(),
                                    "Error deleting mechanic",
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
        setLocationRelativeTo(null);
    }

    protected void deleteMechanic() throws BusinessException {
        MechanicCrudService as = Factories.service.forMechanicCrudService();
        as.delete(txId.getText());

        JOptionPane.showMessageDialog(this,
                "The mechanic with id: " + txId.getText() + " has been deleted",
                "Delete mechanic", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

}
