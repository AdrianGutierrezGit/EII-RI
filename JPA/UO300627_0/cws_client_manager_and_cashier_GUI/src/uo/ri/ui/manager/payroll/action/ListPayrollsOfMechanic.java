package uo.ri.ui.manager.payroll.action;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.util.exception.BusinessException;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ListPayrollsOfMechanic extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lbMechanicId;
    private JTextField txMechanicId;
    private JButton btListPayrolls;
    private JPanel pnPayrolls;
    private JScrollPane spPayrolls;
    private JTable tablePayrolls;
    private DefaultTableModel tablePayrollsModel = new DefaultTableModel(
            new String[] { "Payroll id", "Date", "Net salary" }, 0);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ListPayrollsOfMechanic frame = new ListPayrollsOfMechanic();
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
    public ListPayrollsOfMechanic() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 617, 384);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getLbMechanicId());
        contentPane.add(getTxMechanicId());
        contentPane.add(getBtListPayrolls());
        contentPane.add(getPnPayrolls());

    }

    private JLabel getLbMechanicId() {
        if (lbMechanicId == null) {
            lbMechanicId = new JLabel("Enter the mechanic's id:");
            lbMechanicId.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lbMechanicId.setBounds(33, 31, 174, 23);
        }
        return lbMechanicId;
    }

    private JTextField getTxMechanicId() {
        if (txMechanicId == null) {
            txMechanicId = new JTextField();
            txMechanicId.setBounds(200, 34, 213, 22);
            txMechanicId.setColumns(10);
        }
        return txMechanicId;
    }

    private JButton getBtListPayrolls() {
        if (btListPayrolls == null) {
            btListPayrolls = new JButton("List payrolls");
            btListPayrolls.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        loadPayrolls();
                    } catch (BusinessException exception) {
                        JOptionPane.showMessageDialog(null,
                                exception.getMessage(),
                                "Error listing payrolls of mechanic",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btListPayrolls.setBounds(438, 31, 127, 23);
        }
        return btListPayrolls;
    }

    protected void loadPayrolls() throws BusinessException {
        PayrollService service = Factories.service.forPayrollService();
        List<PayrollSummaryDto> payrolls = service.findSummarizedByMechanicId(
                getTxMechanicId().getText());
        if (payrolls.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No payrolls found for this mechanic", "Payrolls generated",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (PayrollSummaryDto p : payrolls) {
            tablePayrollsModel.addRow(
                    new Object[] { p.id, p.date, p.netSalary, });
        }
    }

    private JPanel getPnPayrolls() {
        if (pnPayrolls == null) {
            pnPayrolls = new JPanel();
            pnPayrolls.setBackground(new Color(255, 255, 255));
            pnPayrolls.setBounds(33, 79, 532, 210);
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
}
