package uo.ri.ui.manager.payroll.action;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.util.exception.BusinessException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowPayroll extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lbPayrollId;
    private JTextField txId;
    private JButton btShowPayroll;
    private JPanel pnPayroll;
    private JLabel lbPayrollId2;
    private JLabel lbContractId;
    private JLabel lbDate;
    private JLabel lbBaseSalary;
    private JLabel lbExtraSalary;
    private JLabel lbProductivityEarning;
    private JLabel lbTrienniumEarning;
    private JLabel lbTaxDeduction;
    private JLabel lbNicDeduction;
    private JLabel lbGrossSalary;
    private JLabel lbTotalDeductions;
    private JLabel lbNetSalary;
    private JTextField txPayrollId;
    private JTextField txContractId;
    private JTextField txDate;
    private JTextField txBaseSalary;
    private JTextField txExtraSalary;
    private JTextField txProductivityEarning;
    private JTextField txTrienniumEarning;
    private JTextField txTaxDeductions;
    private JTextField txNicDeductions;
    private JTextField txGrossSalary;
    private JTextField txTotalDeductions;
    private JTextField txNetSalary;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ShowPayroll frame = new ShowPayroll();
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
    public ShowPayroll() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 690, 388);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getLbPayrollId());
        contentPane.add(getTxId());
        contentPane.add(getBtShowPayroll());
        contentPane.add(getPnPayroll());

    }

    private JLabel getLbPayrollId() {
        if (lbPayrollId == null) {
            lbPayrollId = new JLabel("Enter the payroll id:");
            lbPayrollId.setFont(new Font("Tahoma", Font.PLAIN, 15));
            lbPayrollId.setBounds(45, 28, 149, 17);
        }
        return lbPayrollId;
    }

    private JTextField getTxId() {
        if (txId == null) {
            txId = new JTextField();
            txId.setBounds(204, 29, 277, 19);
            txId.setColumns(10);
        }
        return txId;
    }

    private JButton getBtShowPayroll() {
        if (btShowPayroll == null) {
            btShowPayroll = new JButton("Show payroll");
            btShowPayroll.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        showPayroll();
                    } catch (BusinessException exception) {
                        JOptionPane.showMessageDialog(null,
                                exception.getMessage(), "Error showing payroll",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btShowPayroll.setBounds(507, 28, 126, 20);
        }
        return btShowPayroll;
    }

    protected void showPayroll() throws BusinessException {
        PayrollService service = Factories.service.forPayrollService();
        PayrollDto dto = service.findById(getTxId().getText())
                                .orElseThrow(() -> new BusinessException(
                                        "Payroll not found"));
        getTxPayrollId().setText(dto.id);
        getTxContractId().setText(dto.contractId);
        getTxDate().setText(String.valueOf(dto.date));
        getTxBaseSalary().setText(String.valueOf(dto.baseSalary));
        getTxExtraSalary().setText(String.valueOf(dto.extraSalary));
        getTxGrossSalary().setText(String.valueOf(dto.grossSalary));
        getTxNetSalary().setText(String.valueOf(dto.netSalary));
        getTxNicDeductions().setText(String.valueOf(dto.nicDeduction));
        getTxProductivityEarning().setText(
                String.valueOf(dto.productivityEarning));
        getTxTaxDeductions().setText(String.valueOf(dto.taxDeduction));
        getTxTotalDeductions().setText(String.valueOf(dto.totalDeductions));
        getTxTrienniumEarning().setText(String.valueOf(dto.trienniumEarning));
    }

    private JPanel getPnPayroll() {
        if (pnPayroll == null) {
            pnPayroll = new JPanel();
            pnPayroll.setBackground(new Color(255, 255, 255));
            pnPayroll.setBorder(new TitledBorder(null, "Payroll searched:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            pnPayroll.setBounds(45, 83, 588, 224);
            pnPayroll.setLayout(null);
            pnPayroll.add(getLbPayrollId2());
            pnPayroll.add(getLbContractId());
            pnPayroll.add(getLbDate());
            pnPayroll.add(getLbBaseSalary());
            pnPayroll.add(getLbExtraSalary());
            pnPayroll.add(getLbProductivityEarning());
            pnPayroll.add(getLbTrienniumEarning());
            pnPayroll.add(getLbTaxDeduction());
            pnPayroll.add(getLbNicDeduction());
            pnPayroll.add(getLbGrossSalary());
            pnPayroll.add(getLbTotalDeductions());
            pnPayroll.add(getLbNetSalary());
            pnPayroll.add(getTxPayrollId());
            pnPayroll.add(getTxContractId());
            pnPayroll.add(getTxDate());
            pnPayroll.add(getTxBaseSalary());
            pnPayroll.add(getTxExtraSalary());
            pnPayroll.add(getTxProductivityEarning());
            pnPayroll.add(getTxTrienniumEarning());
            pnPayroll.add(getTxTaxDeductions());
            pnPayroll.add(getTxNicDeductions());
            pnPayroll.add(getTxGrossSalary());
            pnPayroll.add(getTxTotalDeductions());
            pnPayroll.add(getTxNetSalary());
        }
        return pnPayroll;
    }

    private JLabel getLbPayrollId2() {
        if (lbPayrollId2 == null) {
            lbPayrollId2 = new JLabel("Payroll id:");
            lbPayrollId2.setBounds(25, 38, 123, 15);
        }
        return lbPayrollId2;
    }

    private JLabel getLbContractId() {
        if (lbContractId == null) {
            lbContractId = new JLabel("Contract id:");
            lbContractId.setBounds(25, 71, 123, 12);
        }
        return lbContractId;
    }

    private JLabel getLbDate() {
        if (lbDate == null) {
            lbDate = new JLabel("Date:");
            lbDate.setBounds(25, 100, 123, 12);
        }
        return lbDate;
    }

    private JLabel getLbBaseSalary() {
        if (lbBaseSalary == null) {
            lbBaseSalary = new JLabel("Base salary:");
            lbBaseSalary.setBounds(25, 130, 104, 16);
        }
        return lbBaseSalary;
    }

    private JLabel getLbExtraSalary() {
        if (lbExtraSalary == null) {
            lbExtraSalary = new JLabel("Extra salary:");
            lbExtraSalary.setBounds(25, 156, 104, 21);
        }
        return lbExtraSalary;
    }

    private JLabel getLbProductivityEarning() {
        if (lbProductivityEarning == null) {
            lbProductivityEarning = new JLabel("Producticity earning:");
            lbProductivityEarning.setBounds(25, 187, 123, 15);
        }
        return lbProductivityEarning;
    }

    private JLabel getLbTrienniumEarning() {
        if (lbTrienniumEarning == null) {
            lbTrienniumEarning = new JLabel("Triennium earning:");
            lbTrienniumEarning.setBounds(318, 35, 119, 18);
        }
        return lbTrienniumEarning;
    }

    private JLabel getLbTaxDeduction() {
        if (lbTaxDeduction == null) {
            lbTaxDeduction = new JLabel("Tax deduction:");
            lbTaxDeduction.setBounds(318, 71, 109, 12);
        }
        return lbTaxDeduction;
    }

    private JLabel getLbNicDeduction() {
        if (lbNicDeduction == null) {
            lbNicDeduction = new JLabel("Nic deduction:");
            lbNicDeduction.setBounds(318, 100, 109, 12);
        }
        return lbNicDeduction;
    }

    private JLabel getLbGrossSalary() {
        if (lbGrossSalary == null) {
            lbGrossSalary = new JLabel("Gross salary");
            lbGrossSalary.setBounds(318, 130, 109, 16);
        }
        return lbGrossSalary;
    }

    private JLabel getLbTotalDeductions() {
        if (lbTotalDeductions == null) {
            lbTotalDeductions = new JLabel("Total deductions:");
            lbTotalDeductions.setBounds(318, 156, 109, 12);
        }
        return lbTotalDeductions;
    }

    private JLabel getLbNetSalary() {
        if (lbNetSalary == null) {
            lbNetSalary = new JLabel("Net salary:");
            lbNetSalary.setBounds(318, 187, 109, 15);
        }
        return lbNetSalary;
    }

    private JTextField getTxPayrollId() {
        if (txPayrollId == null) {
            txPayrollId = new JTextField();
            txPayrollId.setBounds(158, 35, 129, 18);
            txPayrollId.setColumns(10);
        }
        return txPayrollId;
    }

    private JTextField getTxContractId() {
        if (txContractId == null) {
            txContractId = new JTextField();
            txContractId.setColumns(10);
            txContractId.setBounds(158, 68, 129, 18);
        }
        return txContractId;
    }

    private JTextField getTxDate() {
        if (txDate == null) {
            txDate = new JTextField();
            txDate.setColumns(10);
            txDate.setBounds(158, 97, 129, 18);
        }
        return txDate;
    }

    private JTextField getTxBaseSalary() {
        if (txBaseSalary == null) {
            txBaseSalary = new JTextField();
            txBaseSalary.setColumns(10);
            txBaseSalary.setBounds(158, 127, 129, 18);
        }
        return txBaseSalary;
    }

    private JTextField getTxExtraSalary() {
        if (txExtraSalary == null) {
            txExtraSalary = new JTextField();
            txExtraSalary.setColumns(10);
            txExtraSalary.setBounds(158, 153, 129, 18);
        }
        return txExtraSalary;
    }

    private JTextField getTxProductivityEarning() {
        if (txProductivityEarning == null) {
            txProductivityEarning = new JTextField();
            txProductivityEarning.setColumns(10);
            txProductivityEarning.setBounds(158, 184, 129, 18);
        }
        return txProductivityEarning;
    }

    private JTextField getTxTrienniumEarning() {
        if (txTrienniumEarning == null) {
            txTrienniumEarning = new JTextField();
            txTrienniumEarning.setColumns(10);
            txTrienniumEarning.setBounds(437, 35, 129, 18);
        }
        return txTrienniumEarning;
    }

    private JTextField getTxTaxDeductions() {
        if (txTaxDeductions == null) {
            txTaxDeductions = new JTextField();
            txTaxDeductions.setColumns(10);
            txTaxDeductions.setBounds(437, 68, 129, 18);
        }
        return txTaxDeductions;
    }

    private JTextField getTxNicDeductions() {
        if (txNicDeductions == null) {
            txNicDeductions = new JTextField();
            txNicDeductions.setColumns(10);
            txNicDeductions.setBounds(437, 97, 129, 18);
        }
        return txNicDeductions;
    }

    private JTextField getTxGrossSalary() {
        if (txGrossSalary == null) {
            txGrossSalary = new JTextField();
            txGrossSalary.setColumns(10);
            txGrossSalary.setBounds(437, 127, 129, 18);
        }
        return txGrossSalary;
    }

    private JTextField getTxTotalDeductions() {
        if (txTotalDeductions == null) {
            txTotalDeductions = new JTextField();
            txTotalDeductions.setColumns(10);
            txTotalDeductions.setBounds(437, 153, 129, 18);
        }
        return txTotalDeductions;
    }

    private JTextField getTxNetSalary() {
        if (txNetSalary == null) {
            txNetSalary = new JTextField();
            txNetSalary.setColumns(10);
            txNetSalary.setBounds(437, 184, 129, 18);
        }
        return txNetSalary;
    }
}
