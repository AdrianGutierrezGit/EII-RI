package uo.ri.ui.manager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.ri.ui.manager.cashier.CashierMenu;
import uo.ri.ui.manager.contracttype.ContractTypeMenu;
import uo.ri.ui.manager.mechanic.MechanicMenu;
import uo.ri.ui.manager.payroll.PayrollMenu;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lbSelectManagement;
    private JPanel pnGestiones;
    private JButton btMechanics;
    private JButton btContractTypes;
    private JButton btPayrolls;
    private JButton btCashier;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
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
    public MainWindow() {
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 753, 461);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(getLbSelectManagement(), BorderLayout.NORTH);
        contentPane.add(getPnGestiones(), BorderLayout.CENTER);

    }

    private JLabel getLbSelectManagement() {
        if (lbSelectManagement == null) {
            lbSelectManagement = new JLabel(
                    "Select the management you want to perform");
            lbSelectManagement.setBackground(new Color(255, 255, 255));
            lbSelectManagement.setFont(new Font("Tahoma", Font.BOLD, 24));
        }
        return lbSelectManagement;
    }

    private JPanel getPnGestiones() {
        if (pnGestiones == null) {
            pnGestiones = new JPanel();
            pnGestiones.setBackground(new Color(255, 255, 255));
            pnGestiones.setLayout(null);
            pnGestiones.add(getBtMechanics());
            pnGestiones.add(getBtContractTypes());
            pnGestiones.add(getBtPayrolls());
            pnGestiones.add(getBtCashier());
        }
        return pnGestiones;
    }

    private JButton getBtMechanics() {
        if (btMechanics == null) {
            btMechanics = new JButton("Mechanics");
            btMechanics.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MechanicMenu vm = new MechanicMenu();
                    vm.setVisible(true);
                }
            });
            btMechanics.setFont(new Font("Tahoma", Font.PLAIN, 18));
            btMechanics.setBounds(113, 64, 176, 92);
        }
        return btMechanics;
    }

    private JButton getBtContractTypes() {
        if (btContractTypes == null) {
            btContractTypes = new JButton("Contract types");
            btContractTypes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ContractTypeMenu ctm = new ContractTypeMenu();
                    ctm.setVisible(true);
                }
            });
            btContractTypes.setFont(new Font("Tahoma", Font.PLAIN, 18));
            btContractTypes.setBounds(422, 64, 176, 92);
        }
        return btContractTypes;
    }

    private JButton getBtPayrolls() {
        if (btPayrolls == null) {
            btPayrolls = new JButton("Payrolls");
            btPayrolls.setFont(new Font("Tahoma", Font.PLAIN, 18));
            btPayrolls.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PayrollMenu pm = new PayrollMenu();
                    pm.setVisible(true);
                }
            });
            btPayrolls.setBounds(113, 229, 176, 92);
        }
        return btPayrolls;
    }

    private JButton getBtCashier() {
        if (btCashier == null) {
            btCashier = new JButton("Cashier");
            btCashier.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CashierMenu cm = new CashierMenu();
                    cm.setVisible(true);
                }
            });
            btCashier.setFont(new Font("Tahoma", Font.PLAIN, 18));
            btCashier.setBounds(422, 229, 176, 92);
        }
        return btCashier;
    }
}
