package uo.ri.ui.manager.payroll.action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.util.exception.BusinessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ListPayrollsOfProfGroup extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel pnPayrolls;
    private JScrollPane spPayrolls;
    private JTable tablePayrolls;
    private DefaultTableModel tablePayrollsModel = new DefaultTableModel(
            new String[] { "Payroll id", "Date", "Net salary" }, 0);
    private JLabel lbProfGroupName;
    private JTextField txProfGroupName;
    private JButton btListPayrolls;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ListPayrollsOfProfGroup frame =
                                                  new ListPayrollsOfProfGroup();
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
    public ListPayrollsOfProfGroup() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 690, 428);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        contentPane.add(getPnPayrolls());
        contentPane.add(getLbProfGroupName());
        contentPane.add(getTxProfGroupName());
        contentPane.add(getBtListPayrolls());

    }

    private JPanel getPnPayrolls() {
        if (pnPayrolls == null) {
            pnPayrolls = new JPanel();
            pnPayrolls.setBackground(new Color(255, 255, 255));
            pnPayrolls.setBounds(50, 76, 579, 267);
            pnPayrolls.setLayout(new BorderLayout(0, 0));
            pnPayrolls.add(getSpPayrolls(), BorderLayout.CENTER);
        }
        return pnPayrolls;
    }

    private JScrollPane getSpPayrolls() {
        if (spPayrolls == null) {
            spPayrolls = new JScrollPane();
            spPayrolls.setViewportView(getTablePayrolls());
            spPayrolls.getViewport()
                      .setBackground(new Color(255, 255, 255));
        }
        return spPayrolls;
    }

    private JTable getTablePayrolls() {
        if (tablePayrolls == null) {
            tablePayrolls = new JTable();
            tablePayrolls.setModel(tablePayrollsModel);
        }
        return tablePayrolls;
    }

    private JLabel getLbProfGroupName() {
        if (lbProfGroupName == null) {
            lbProfGroupName = new JLabel(
                    "Enter the name of the professional group:");
            lbProfGroupName.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lbProfGroupName.setBounds(50, 26, 228, 17);
        }
        return lbProfGroupName;
    }

    private JTextField getTxProfGroupName() {
        if (txProfGroupName == null) {
            txProfGroupName = new JTextField();
            txProfGroupName.setBounds(288, 26, 186, 18);
            txProfGroupName.setColumns(10);
        }
        return txProfGroupName;
    }

    private JButton getBtListPayrolls() {
        if (btListPayrolls == null) {
            btListPayrolls = new JButton("List Payrolls");
            btListPayrolls.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        loadPayrolls();
                    } catch (BusinessException exception) {
                        JOptionPane.showMessageDialog(null,
                                exception.getMessage(),
                                "Error listing payrolls of professional group",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btListPayrolls.setBounds(499, 25, 128, 21);
        }
        return btListPayrolls;
    }

    protected void loadPayrolls() throws BusinessException {
        PayrollService service = Factories.service.forPayrollService();
        List<PayrollSummaryDto> payrolls =
                                         service.findSummarizedByProfessionalGroupName(
                                                 getTxProfGroupName().getText());
        if (payrolls.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No payrolls found for this professional group",
                    "Payrolls generated", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (PayrollSummaryDto p : payrolls) {
            tablePayrollsModel.addRow(
                    new Object[] { p.id, p.date, p.netSalary, });
        }

    }
}
