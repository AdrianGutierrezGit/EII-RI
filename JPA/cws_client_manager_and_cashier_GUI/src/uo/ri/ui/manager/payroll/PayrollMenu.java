package uo.ri.ui.manager.payroll;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.ui.manager.payroll.action.DeleteLastMonthPayrollOfMechanic;
import uo.ri.ui.manager.payroll.action.GeneratePayrollsAtDate;
import uo.ri.ui.manager.payroll.action.GeneratePayrollsToday;
import uo.ri.ui.manager.payroll.action.ListPayrollsOfMechanic;
import uo.ri.ui.manager.payroll.action.ListPayrollsOfProfGroup;
import uo.ri.ui.manager.payroll.action.ShowPayroll;
import uo.ri.util.exception.BusinessException;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PayrollMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lbPayrollsManagement;
    private JPanel pnManagements;
    private JButton btGeneratePayrollsAtDate;
    private JButton btnGeneratePayrollsToday;
    private JButton btnDeleteLastMonthPayrollOfMechanic;
    private JButton btnDeleteLastMonth;
    private JButton btnListPayrollsOf;
    private JButton btnListPayrollsOf_1;
    private JButton btnShowPayroll;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PayrollMenu frame = new PayrollMenu();
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
    public PayrollMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 679, 411);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(getLbPayrollsManagement(), BorderLayout.NORTH);
        contentPane.add(getPnManagements(), BorderLayout.CENTER);

    }

    private JLabel getLbPayrollsManagement() {
        if (lbPayrollsManagement == null) {
            lbPayrollsManagement = new JLabel("Payrolls management");
            lbPayrollsManagement.setFont(new Font("Tahoma", Font.BOLD, 24));
        }
        return lbPayrollsManagement;
    }

    private JPanel getPnManagements() {
        if (pnManagements == null) {
            pnManagements = new JPanel();
            pnManagements.setLayout(null);
            pnManagements.add(getBtGeneratePayrollsAtDate());
            pnManagements.add(getBtnGeneratePayrollsToday());
            pnManagements.add(getBtnDeleteLastMonthPayrollOfMechanic());
            pnManagements.add(getBtnDeleteLastMonth());
            pnManagements.add(getBtnListPayrollsOf());
            pnManagements.add(getBtnListPayrollsOf_1());
            pnManagements.add(getBtnShowPayroll());
        }
        return pnManagements;
    }

    private JButton getBtGeneratePayrollsAtDate() {
        if (btGeneratePayrollsAtDate == null) {
            btGeneratePayrollsAtDate = new JButton("Generate payrolls at date");
            btGeneratePayrollsAtDate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    GeneratePayrollsAtDate gpad = new GeneratePayrollsAtDate();
                    gpad.setVisible(true);
                }
            });
            btGeneratePayrollsAtDate.setFont(
                    new Font("Tahoma", Font.PLAIN, 12));
            btGeneratePayrollsAtDate.setBounds(27, 44, 190, 71);
        }
        return btGeneratePayrollsAtDate;
    }

    private JButton getBtnGeneratePayrollsToday() {
        if (btnGeneratePayrollsToday == null) {
            btnGeneratePayrollsToday = new JButton("Generate payrolls today");
            btnGeneratePayrollsToday.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    GeneratePayrollsToday gpt = new GeneratePayrollsToday();
                    gpt.setVisible(true);
                }
            });
            btnGeneratePayrollsToday.setFont(
                    new Font("Tahoma", Font.PLAIN, 12));
            btnGeneratePayrollsToday.setBounds(240, 44, 180, 71);
        }
        return btnGeneratePayrollsToday;
    }

    private JButton getBtnDeleteLastMonthPayrollOfMechanic() {
        if (btnDeleteLastMonthPayrollOfMechanic == null) {
            btnDeleteLastMonthPayrollOfMechanic = new JButton(
                    "Delete last month payroll of mechanic");
            btnDeleteLastMonthPayrollOfMechanic.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            DeleteLastMonthPayrollOfMechanic d =
                                                               new DeleteLastMonthPayrollOfMechanic();
                            d.setVisible(true);
                        }
                    });
            btnDeleteLastMonthPayrollOfMechanic.setFont(
                    new Font("Tahoma", Font.PLAIN, 9));
            btnDeleteLastMonthPayrollOfMechanic.setBounds(440, 44, 190, 71);
        }
        return btnDeleteLastMonthPayrollOfMechanic;
    }

    private JButton getBtnDeleteLastMonth() {
        if (btnDeleteLastMonth == null) {
            btnDeleteLastMonth = new JButton("Delete last month payroll");
            btnDeleteLastMonth.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        deleteLastMonthPayroll();
                    } catch (BusinessException exception) {
                        JOptionPane.showMessageDialog(null,
                                exception.getMessage(),
                                "Error deleting payrolls",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btnDeleteLastMonth.setFont(new Font("Tahoma", Font.PLAIN, 12));
            btnDeleteLastMonth.setBounds(27, 137, 190, 71);
        }
        return btnDeleteLastMonth;
    }

    protected void deleteLastMonthPayroll() throws BusinessException {
        PayrollService service = Factories.service.forPayrollService();
        service.deleteLastGenerated();
        JOptionPane.showMessageDialog(this, "Last month's payrolls deleted",
                "Payrolls deleted", JOptionPane.INFORMATION_MESSAGE);

    }

    private JButton getBtnListPayrollsOf() {
        if (btnListPayrollsOf == null) {
            btnListPayrollsOf = new JButton("List payrolls of mechanic");
            btnListPayrollsOf.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ListPayrollsOfMechanic lm = new ListPayrollsOfMechanic();
                    lm.setVisible(true);
                }
            });
            btnListPayrollsOf.setFont(new Font("Tahoma", Font.PLAIN, 12));
            btnListPayrollsOf.setBounds(240, 137, 180, 71);
        }
        return btnListPayrollsOf;
    }

    private JButton getBtnListPayrollsOf_1() {
        if (btnListPayrollsOf_1 == null) {
            btnListPayrollsOf_1 = new JButton(
                    "List payrolls of professional group");
            btnListPayrollsOf_1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ListPayrollsOfProfGroup lpg = new ListPayrollsOfProfGroup();
                    lpg.setVisible(true);
                }
            });
            btnListPayrollsOf_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
            btnListPayrollsOf_1.setBounds(440, 137, 190, 71);
        }
        return btnListPayrollsOf_1;
    }

    private JButton getBtnShowPayroll() {
        if (btnShowPayroll == null) {
            btnShowPayroll = new JButton("Show payroll");
            btnShowPayroll.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ShowPayroll sp = new ShowPayroll();
                    sp.setVisible(true);
                }
            });
            btnShowPayroll.setFont(new Font("Tahoma", Font.PLAIN, 12));
            btnShowPayroll.setBounds(240, 232, 180, 71);
        }
        return btnShowPayroll;
    }
}
