package uo.ri.ui.manager.contracttype.action;

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
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.exception.BusinessException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FindContractTypeByName extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lbName;
    private JTextField txName;
    private JButton btFindContractType;
    private JPanel panel;
    private JLabel lbContractTypeName;
    private JLabel lblCompensationDays;
    private JTextField txCompensationDays;
    private JTextField txContractTypeName;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FindContractTypeByName frame = new FindContractTypeByName();
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
    public FindContractTypeByName() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 558, 330);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getLbName());
        contentPane.add(getTxName());
        contentPane.add(getBtFindContractType());
        contentPane.add(getPanel());

    }

    private JLabel getLbName() {
        if (lbName == null) {
            lbName = new JLabel("Contract type name:");
            lbName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lbName.setBounds(22, 37, 130, 17);
        }
        return lbName;
    }

    private JTextField getTxName() {
        if (txName == null) {
            txName = new JTextField();
            txName.setBounds(162, 38, 187, 18);
            txName.setColumns(10);
        }
        return txName;
    }

    private JButton getBtFindContractType() {
        if (btFindContractType == null) {
            btFindContractType = new JButton("Find contract type");
            btFindContractType.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        findContractType();
                    } catch (BusinessException exception) {
                        JOptionPane.showMessageDialog(null,
                                exception.getMessage(),
                                "Error finding a contract type",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btFindContractType.setBounds(359, 29, 153, 28);
        }
        return btFindContractType;
    }

    protected void findContractType() throws BusinessException {
        ContractTypeCrudService service =
                                        Factories.service.forContractTypeCrudService();
        ContractTypeDto dto = service.findByName(getTxName().getText())
                                     .orElseThrow(() -> new BusinessException(
                                             "Contract type not found"));
        getTxContractTypeName().setText(dto.name);
        getTxCompensationDays().setText(String.valueOf(dto.compensationDays));
    }

    private JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel();
            panel.setBorder(new TitledBorder(null, "Contract type details:",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(22, 85, 490, 148);
            panel.setLayout(null);
            panel.add(getLbContractTypeName());
            panel.add(getLblCompensationDays());
            panel.add(getTxCompensationDays());
            panel.add(getTxContractTypeName());
        }
        return panel;
    }

    private JLabel getLbContractTypeName() {
        if (lbContractTypeName == null) {
            lbContractTypeName = new JLabel("Name:");
            lbContractTypeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lbContractTypeName.setBounds(45, 45, 44, 12);
        }
        return lbContractTypeName;
    }

    private JLabel getLblCompensationDays() {
        if (lblCompensationDays == null) {
            lblCompensationDays = new JLabel("Compensation days:");
            lblCompensationDays.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblCompensationDays.setBounds(45, 93, 136, 12);
        }
        return lblCompensationDays;
    }

    private JTextField getTxCompensationDays() {
        if (txCompensationDays == null) {
            txCompensationDays = new JTextField();
            txCompensationDays.setBounds(191, 92, 244, 18);
            txCompensationDays.setColumns(10);
        }
        return txCompensationDays;
    }

    private JTextField getTxContractTypeName() {
        if (txContractTypeName == null) {
            txContractTypeName = new JTextField();
            txContractTypeName.setBounds(191, 44, 244, 18);
            txContractTypeName.setColumns(10);
        }
        return txContractTypeName;
    }
}
