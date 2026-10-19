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
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.exception.BusinessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class UpdateMechanic extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txId;
    private JTextField txName;
    private JTextField txSurname;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UpdateMechanic dialog = new UpdateMechanic();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UpdateMechanic() {
        setBounds(100, 100, 369, 249);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null,
                    "Enter the id of the mechanic you want to update:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(48, 10, 261, 62);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lbId = new JLabel("Mechanic id:");
                lbId.setBounds(22, 31, 64, 12);
                panel.add(lbId);
            }
            {
                txId = new JTextField();
                txId.setBounds(96, 28, 139, 18);
                panel.add(txId);
                txId.setColumns(10);
            }
        }
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null,
                    "Enter the new mechanic's information:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(48, 82, 261, 77);
            contentPanel.add(panel);
            panel.setLayout(null);
            {
                JLabel lbName = new JLabel("Name:");
                lbName.setBounds(25, 25, 44, 12);
                panel.add(lbName);
            }
            {
                JLabel lbSurname = new JLabel("Surname:");
                lbSurname.setBounds(25, 47, 59, 12);
                panel.add(lbSurname);
            }
            {
                txName = new JTextField();
                txName.setBounds(89, 22, 139, 18);
                panel.add(txName);
                txName.setColumns(10);
            }
            {
                txSurname = new JTextField();
                txSurname.setBounds(89, 44, 139, 18);
                panel.add(txSurname);
                txSurname.setColumns(10);
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
                            updateMechanic();
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null,
                                    exception.getMessage(),
                                    "Error updating mechanic",
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

    protected void updateMechanic() throws BusinessException {
        MechanicCrudService as = Factories.service.forMechanicCrudService();

        Optional<MechanicDto> res = as.findById(txId.getText());
        if (!res.isPresent()) {
            throw new BusinessException("There is no mechanic with that id");
        }
        MechanicDto m = res.get();
        m.name = txName.getText();
        m.surname = txSurname.getText();

        as.update(m);
        JOptionPane.showMessageDialog(this, "The mechanic has been updated",
                "Update mechanic", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

}
