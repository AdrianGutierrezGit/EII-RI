package uo.ri.ui.manager.mechanic.action;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.util.exception.BusinessException;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

public class ListCurrentMechanics extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel pnContracts;
    private JScrollPane spContracts;
    private JTable tableContracts;
    private DefaultTableModel tableContractsModel = new DefaultTableModel(
            new String[] { "Id", "Mechanic nif", "Mechanic name",
                    "Mechanic surname", "Contract type name", "Start date",
                    "End date", "State" },
            0);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ListCurrentMechanics frame = new ListCurrentMechanics();
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
    public ListCurrentMechanics() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 796, 494);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getPnContracts());
        loadContracts();

    }

    private void loadContracts() {
        try {
            listCurrentMechanics();
        } catch (BusinessException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(),
                    "Error showing contracts in force",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    protected void listCurrentMechanics() throws BusinessException {
        ContractCrudService service =
                                    Factories.service.forContractCrudService();
        for (ContractDto c : service.findInforceContracts()) {
            tableContractsModel.addRow(new Object[] { c.id, c.mechanic.nif,
                    c.mechanic.name, c.mechanic.surname, c.contractType.name,
                    c.startDate, c.endDate != null ? c.endDate : "---",
                    c.state });
        }
    }

    private JPanel getPnContracts() {
        if (pnContracts == null) {
            pnContracts = new JPanel();
            pnContracts.setBackground(new Color(255, 255, 255));
            pnContracts.setBounds(53, 34, 684, 375);
            pnContracts.setLayout(new BorderLayout(0, 0));
            pnContracts.add(getSpContracts(), BorderLayout.CENTER);
        }
        return pnContracts;
    }

    private JScrollPane getSpContracts() {
        if (spContracts == null) {
            spContracts = new JScrollPane();
            spContracts.setViewportView(getTableContracts());
            spContracts.getViewport()
                       .setBackground(new Color(255, 255, 255));

        }
        return spContracts;
    }

    private JTable getTableContracts() {
        if (tableContracts == null) {
            tableContracts = new JTable();
            tableContracts.setBackground(new Color(255, 255, 255));
            tableContracts.setModel(tableContractsModel);
        }
        return tableContracts;
    }
}
