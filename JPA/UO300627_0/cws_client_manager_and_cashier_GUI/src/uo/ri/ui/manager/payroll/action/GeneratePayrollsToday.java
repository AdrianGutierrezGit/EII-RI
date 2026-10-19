package uo.ri.ui.manager.payroll.action;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.util.exception.BusinessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.time.LocalDate;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

public class GeneratePayrollsToday extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel pnPayrolls;
    private JLabel lbPayrollsGenerated;
    private JScrollPane spPayrolls;
    private JTable tablePayrolls;
    private DefaultTableModel tablePayrollsModel = new DefaultTableModel(
            new String[] { "Contract id", "Date", "Gross salary",
                    "Total deductions", "Net Salary" },
            0);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GeneratePayrollsToday frame = new GeneratePayrollsToday();
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
    public GeneratePayrollsToday() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 717, 423);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getPnPayrolls());
        contentPane.add(getLbPayrollsGenerated());
        try {
            loadPayrolls();
        } catch (BusinessException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(),
                    "Error generating payrolls", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void loadPayrolls() throws BusinessException {
        PayrollService service = Factories.service.forPayrollService();
        List<PayrollDto> payrolls = service.generateForPreviousMonthOf(
                LocalDate.now());
        for (PayrollDto p : payrolls) {
            tablePayrollsModel.addRow(new Object[] { p.contractId, p.date,
                    p.grossSalary, p.totalDeductions, p.netSalary });
        }
        JOptionPane.showMessageDialog(this,
                payrolls.size() + " generated for today", "Payrolls generated",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel getPnPayrolls() {
        if (pnPayrolls == null) {
            pnPayrolls = new JPanel();
            pnPayrolls.setBounds(29, 42, 652, 297);
            pnPayrolls.setLayout(new BorderLayout(0, 0));
            pnPayrolls.add(getSpPayrolls(), BorderLayout.CENTER);
        }
        return pnPayrolls;
    }

    private JLabel getLbPayrollsGenerated() {
        if (lbPayrollsGenerated == null) {
            lbPayrollsGenerated = new JLabel(
                    "The following payrolls have been generated:");
            lbPayrollsGenerated.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lbPayrollsGenerated.setBounds(29, 10, 340, 35);
        }
        return lbPayrollsGenerated;
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
        }
        return tablePayrolls;
    }
}
