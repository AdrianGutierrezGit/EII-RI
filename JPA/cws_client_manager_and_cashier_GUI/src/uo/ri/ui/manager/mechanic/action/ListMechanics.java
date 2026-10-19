package uo.ri.ui.manager.mechanic.action;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.exception.BusinessException;

import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ListMechanics extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel pnTableMechanics;
    private JScrollPane spMechanics;
    private JTable tableMechanics;
    private DefaultTableModel tableMechanicsModel = new DefaultTableModel(
            new String[] { "Id", "Nif", "Name", "Surname" }, 0);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ListMechanics frame = new ListMechanics();
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
    public ListMechanics() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 649, 393);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getPnTableMechanics());
        loadMechanics();

    }

    private void loadMechanics() {
        try {
            listMechanics();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(),
                    "Error showing mechanic", JOptionPane.ERROR_MESSAGE);
        }

    }

    protected void listMechanics() throws BusinessException {
        MechanicCrudService service =
                                    Factories.service.forMechanicCrudService();
        for (MechanicDto m : service.findAll()) {
            tableMechanicsModel.addRow(
                    new Object[] { m.id, m.nif, m.name, m.surname });
        }
    }

    private JPanel getPnTableMechanics() {
        if (pnTableMechanics == null) {
            pnTableMechanics = new JPanel();
            pnTableMechanics.setBackground(new Color(255, 255, 255));
            pnTableMechanics.setBounds(35, 34, 560, 279);
            pnTableMechanics.setLayout(new BorderLayout(0, 0));
            pnTableMechanics.add(getSpMechanics());
        }
        return pnTableMechanics;
    }

    private JScrollPane getSpMechanics() {
        if (spMechanics == null) {
            spMechanics = new JScrollPane();
            spMechanics.setViewportView(getTableMechanics());
            spMechanics.getViewport()
                       .setBackground(new Color(255, 255, 255));
        }
        return spMechanics;
    }

    private JTable getTableMechanics() {
        if (tableMechanics == null) {
            tableMechanics = new JTable();
            tableMechanics.setBackground(new Color(255, 255, 255));
            tableMechanics.setModel(tableMechanicsModel);
        }
        return tableMechanics;
    }
}
