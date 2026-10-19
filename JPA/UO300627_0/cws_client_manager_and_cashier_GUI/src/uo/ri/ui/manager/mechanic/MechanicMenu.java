package uo.ri.ui.manager.mechanic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.ri.ui.manager.mechanic.action.AddMechanic;
import uo.ri.ui.manager.mechanic.action.DeleteMechanic;
import uo.ri.ui.manager.mechanic.action.ListCurrentMechanics;
import uo.ri.ui.manager.mechanic.action.ListMechanics;
import uo.ri.ui.manager.mechanic.action.UpdateMechanic;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MechanicMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lbMechanicsManagement;
    private JPanel pnGestionMecanicos;
    private JButton btAddMechanic;
    private JButton btDeleteMechanic;
    private JButton btUpdateMechanic;
    private JButton btListMechanics;
    private JButton btListCurrentMechanics;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MechanicMenu frame = new MechanicMenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MechanicMenu() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 642, 427);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(getLbMechanicsManagement(), BorderLayout.NORTH);
        contentPane.add(getPnGestionMecanicos(), BorderLayout.CENTER);

    }

    private JLabel getLbMechanicsManagement() {
        if (lbMechanicsManagement == null) {
            lbMechanicsManagement = new JLabel("Mechanics management");
            lbMechanicsManagement.setBackground(new Color(255, 255, 255));
            lbMechanicsManagement.setFont(new Font("Tahoma", Font.BOLD, 24));
        }
        return lbMechanicsManagement;
    }

    private JPanel getPnGestionMecanicos() {
        if (pnGestionMecanicos == null) {
            pnGestionMecanicos = new JPanel();
            pnGestionMecanicos.setBackground(new Color(255, 255, 255));
            pnGestionMecanicos.setLayout(null);
            pnGestionMecanicos.add(getBtAddMechanic());
            pnGestionMecanicos.add(getBtDeleteMechanic());
            pnGestionMecanicos.add(getBtUpdateMechanic());
            pnGestionMecanicos.add(getBtListMechanics());
            pnGestionMecanicos.add(getBtListCurrentMechanics());
        }
        return pnGestionMecanicos;
    }

    private JButton getBtAddMechanic() {
        if (btAddMechanic == null) {
            btAddMechanic = new JButton("Add mechanic");
            btAddMechanic.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    AddMechanic am = new AddMechanic();
                    am.setVisible(true);
                }
            });
            btAddMechanic.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btAddMechanic.setBounds(58, 71, 139, 73);
        }
        return btAddMechanic;
    }

    private JButton getBtDeleteMechanic() {
        if (btDeleteMechanic == null) {
            btDeleteMechanic = new JButton("Delete mechanic");
            btDeleteMechanic.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DeleteMechanic dm = new DeleteMechanic();
                    dm.setVisible(true);
                }
            });
            btDeleteMechanic.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btDeleteMechanic.setBounds(242, 71, 139, 73);
        }
        return btDeleteMechanic;
    }

    private JButton getBtUpdateMechanic() {
        if (btUpdateMechanic == null) {
            btUpdateMechanic = new JButton("Update mechanic");
            btUpdateMechanic.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    UpdateMechanic up = new UpdateMechanic();
                    up.setVisible(true);
                }
            });
            btUpdateMechanic.setFont(new Font("Tahoma", Font.PLAIN, 13));
            btUpdateMechanic.setBounds(424, 71, 141, 73);
        }
        return btUpdateMechanic;
    }

    private JButton getBtListMechanics() {
        if (btListMechanics == null) {
            btListMechanics = new JButton("List Mechanics");
            btListMechanics.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ListMechanics lm = new ListMechanics();
                    lm.setVisible(true);
                }
            });
            btListMechanics.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btListMechanics.setBounds(111, 192, 139, 73);
        }
        return btListMechanics;
    }

    private JButton getBtListCurrentMechanics() {
        if (btListCurrentMechanics == null) {
            btListCurrentMechanics = new JButton("List current mechanics");
            btListCurrentMechanics.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ListCurrentMechanics lcm = new ListCurrentMechanics();
                    lcm.setVisible(true);
                }
            });
            btListCurrentMechanics.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btListCurrentMechanics.setBounds(293, 192, 183, 73);
        }
        return btListCurrentMechanics;
    }
}
