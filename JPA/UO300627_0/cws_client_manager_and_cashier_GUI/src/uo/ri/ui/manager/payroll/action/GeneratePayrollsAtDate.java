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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.awt.event.ActionEvent;

public class GeneratePayrollsAtDate extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lbDate;
    private JTextField txDate;
    private JButton btGenerate;
    private JPanel pnPayrolls;
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
                    GeneratePayrollsAtDate frame = new GeneratePayrollsAtDate();
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
    public GeneratePayrollsAtDate() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 716, 439);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getLbDate());
        contentPane.add(getTxDate());
        contentPane.add(getBtGenerate());
        contentPane.add(getPnPayrolls());

    }

    private JLabel getLbDate() {
        if (lbDate == null) {
            lbDate = new JLabel("Enter the date to generate the payrolls:");
            lbDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lbDate.setBounds(39, 29, 253, 29);
        }
        return lbDate;
    }

    private JTextField getTxDate() {
        if (txDate == null) {
            txDate = new JTextField();
            txDate.setBounds(314, 33, 203, 25);
            txDate.setColumns(10);
        }
        return txDate;
    }

    private JButton getBtGenerate() {
        if (btGenerate == null) {
            btGenerate = new JButton("Generate");
            btGenerate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        generatePayrolls();
                    } catch (BusinessException exception) {
                        JOptionPane.showMessageDialog(null,
                                exception.getMessage(),
                                "Error generating payrolls",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btGenerate.setFont(new Font("Tahoma", Font.PLAIN, 12));
            btGenerate.setBounds(547, 31, 108, 26);
        }
        return btGenerate;
    }

    protected void generatePayrolls() throws BusinessException {
        PayrollService service = Factories.service.forPayrollService();
        LocalDate date = LocalDate.parse(getTxDate().getText());
        List<PayrollDto> payrolls = service.generateForPreviousMonthOf(date);
        for (PayrollDto p : payrolls) {
            tablePayrollsModel.addRow(new Object[] { p.contractId, p.date,
                    p.grossSalary, p.totalDeductions, p.netSalary });
        }
        JOptionPane.showMessageDialog(this,
                payrolls.size() + " generated for the specified date",
                "Payrolls generated", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel getPnPayrolls() {
        if (pnPayrolls == null) {
            pnPayrolls = new JPanel();
            pnPayrolls.setBounds(39, 68, 616, 305);
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
        }
        return tablePayrolls;
    }
}
