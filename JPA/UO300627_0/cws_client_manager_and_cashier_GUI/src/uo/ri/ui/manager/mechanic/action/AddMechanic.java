package uo.ri.ui.manager.mechanic.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.exception.BusinessException;

import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddMechanic extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel cpnDatos = new JPanel();
    private JPanel pnDatos;
    private JLabel lbNif;
    private JLabel lbName;
    private JLabel lbSurname;
    private JTextField txNif;
    private JTextField txName;
    private JTextField txSurname;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            AddMechanic dialog = new AddMechanic();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AddMechanic() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBackground(new Color(255, 255, 255));
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        cpnDatos.setBackground(new Color(255, 255, 255));
        cpnDatos.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(cpnDatos, BorderLayout.CENTER);
        cpnDatos.setLayout(null);
        cpnDatos.add(getPnDatos());
        {
            JPanel bpnBotones = new JPanel();
            bpnBotones.setBackground(new Color(255, 255, 255));
            bpnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(bpnBotones, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            addMechanic();
                        } catch (BusinessException exception) {
                            JOptionPane.showMessageDialog(null,
                                    exception.getMessage(),
                                    "Error adding mechanic",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                okButton.setActionCommand("OK");
                bpnBotones.add(okButton);
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
                bpnBotones.add(cancelButton);
            }
        }
        setLocationRelativeTo(null);
    }

    protected void addMechanic() throws BusinessException {
        MechanicDto m = new MechanicDto();
        m.name = getTxName().getText();
        m.nif = getTxNif().getText();
        m.surname = getTxSurname().getText();
        MechanicCrudService as = Factories.service.forMechanicCrudService();
        m = as.create(m);

        JOptionPane.showMessageDialog(this, "New mechanic added: " + m.id,
                "Add mechanic", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private JPanel getPnDatos() {
        if (pnDatos == null) {
            pnDatos = new JPanel();
            pnDatos.setBackground(new Color(255, 255, 255));
            pnDatos.setBorder(new TitledBorder(
                    new EtchedBorder(EtchedBorder.LOWERED,
                            new Color(255, 255, 255), new Color(160, 160, 160)),
                    "Enter the new mechanic's details:", TitledBorder.LEADING,
                    TitledBorder.TOP, null, new Color(0, 0, 0)));
            pnDatos.setBounds(26, 28, 377, 175);
            pnDatos.setLayout(null);
            pnDatos.add(getLbNif());
            pnDatos.add(getLbName());
            pnDatos.add(getLbSurname());
            pnDatos.add(getTxNif());
            pnDatos.add(getTxName());
            pnDatos.add(getTxSurname());
        }
        return pnDatos;
    }

    private JLabel getLbNif() {
        if (lbNif == null) {
            lbNif = new JLabel("Nif:");
            lbNif.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lbNif.setBounds(44, 44, 44, 12);
        }
        return lbNif;
    }

    private JLabel getLbName() {
        if (lbName == null) {
            lbName = new JLabel("Name:");
            lbName.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lbName.setBounds(44, 82, 55, 12);
        }
        return lbName;
    }

    private JLabel getLbSurname() {
        if (lbSurname == null) {
            lbSurname = new JLabel("Surname:");
            lbSurname.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lbSurname.setBounds(44, 127, 55, 12);
        }
        return lbSurname;
    }

    private JTextField getTxNif() {
        if (txNif == null) {
            txNif = new JTextField();
            txNif.setBounds(131, 42, 190, 18);
            txNif.setColumns(10);
        }
        return txNif;
    }

    private JTextField getTxName() {
        if (txName == null) {
            txName = new JTextField();
            txName.setColumns(10);
            txName.setBounds(131, 80, 190, 18);
        }
        return txName;
    }

    private JTextField getTxSurname() {
        if (txSurname == null) {
            txSurname = new JTextField();
            txSurname.setColumns(10);
            txSurname.setBounds(131, 125, 190, 18);
        }
        return txSurname;
    }
}
