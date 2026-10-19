package uo.ri.ui.manager.contracttype.action;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.exception.BusinessException;

import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ListAllContractTypes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel pnContractTypes;
    private JScrollPane spContractTypes;
    private JTable tableContractTypes;
    private DefaultTableModel tableContractTypesModel = new DefaultTableModel(
            new String[] { "Contract type name", "Compensation" }, 0);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ListAllContractTypes frame = new ListAllContractTypes();
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
    public ListAllContractTypes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 570, 354);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getPnContractTypes());
        try {
            loadContractTypes();
        } catch (BusinessException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(),
                    "Error listing all contract types",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void loadContractTypes() throws BusinessException {
        ContractTypeCrudService service =
                                        Factories.service.forContractTypeCrudService();
        List<ContractTypeDto> types = service.findAll();
        if (types.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No contract types found",
                    "Listing contract types", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (ContractTypeDto c : types) {
            tableContractTypesModel.addRow(
                    new Object[] { c.name, c.compensationDays });
        }

    }

    private JPanel getPnContractTypes() {
        if (pnContractTypes == null) {
            pnContractTypes = new JPanel();
            pnContractTypes.setBackground(new Color(255, 255, 255));
            pnContractTypes.setBounds(33, 40, 486, 231);
            pnContractTypes.setLayout(new BorderLayout(0, 0));
            pnContractTypes.add(getSpContractTypes(), BorderLayout.CENTER);
        }
        return pnContractTypes;
    }

    private JScrollPane getSpContractTypes() {
        if (spContractTypes == null) {
            spContractTypes = new JScrollPane();
            spContractTypes.setViewportView(getTableContractTypes());
            spContractTypes.getViewport()
                           .setBackground(new Color(255, 255, 255));
        }
        return spContractTypes;
    }

    private JTable getTableContractTypes() {
        if (tableContractTypes == null) {
            tableContractTypes = new JTable();
            tableContractTypes.setModel(tableContractTypesModel);
        }
        return tableContractTypes;
    }
}
