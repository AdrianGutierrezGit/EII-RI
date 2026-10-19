package uo.ri.ui.manager.cashier.action;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
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

public class FindNotInvoicedWorkOrders extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lbClientNif;
    private JTextField txClientNif;
    private JButton btFindWorkOrders;
    private JPanel pnWorkOrders;
    private JScrollPane spWorkOrders;
    private JTable tableWorkOrders;
    private DefaultTableModel tableWorkOrdersModel = new DefaultTableModel(
            new String[] { "Work order id", "Description", "Date", "State",
                    "Amount" },
            0);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FindNotInvoicedWorkOrders frame =
                                                    new FindNotInvoicedWorkOrders();
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
    public FindNotInvoicedWorkOrders() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 682, 440);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getLbClientNif());
        contentPane.add(getTxClientNif());
        contentPane.add(getBtFindWorkOrders());
        contentPane.add(getPnWorkOrders());

    }

    private JLabel getLbClientNif() {
        if (lbClientNif == null) {
            lbClientNif = new JLabel("Enter the client's nif:");
            lbClientNif.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lbClientNif.setBounds(47, 34, 145, 28);
        }
        return lbClientNif;
    }

    private JTextField getTxClientNif() {
        if (txClientNif == null) {
            txClientNif = new JTextField();
            txClientNif.setBounds(202, 34, 225, 24);
            txClientNif.setColumns(10);
        }
        return txClientNif;
    }

    private JButton getBtFindWorkOrders() {
        if (btFindWorkOrders == null) {
            btFindWorkOrders = new JButton("Find");
            btFindWorkOrders.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        showWorkOrders();
                    } catch (BusinessException exception) {
                        JOptionPane.showMessageDialog(null,
                                exception.getMessage(),
                                "Error finding work orders",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btFindWorkOrders.setBounds(485, 34, 115, 25);
        }
        return btFindWorkOrders;
    }

    protected void showWorkOrders() throws BusinessException {
        InvoicingService cs = Factories.service.forCreateInvoiceService();

        List<InvoicingWorkOrderDto> reps =
                                         cs.findNotInvoicedWorkOrdersByClientNif(
                                                 getTxClientNif().getText());

        if (reps.size() == 0) {
            JOptionPane.showInternalMessageDialog(null,
                    "There is no pending work orders",
                    "Find not invoiced work orders",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (InvoicingWorkOrderDto w : reps) {
            tableWorkOrdersModel.addRow(new Object[] { w.id, w.description,
                    w.date, w.state, w.amount });
        }

    }

    private JPanel getPnWorkOrders() {
        if (pnWorkOrders == null) {
            pnWorkOrders = new JPanel();
            pnWorkOrders.setBackground(new Color(255, 255, 255));
            pnWorkOrders.setBounds(47, 93, 553, 242);
            pnWorkOrders.setLayout(new BorderLayout(0, 0));
            pnWorkOrders.add(getSpWorkOrders(), BorderLayout.CENTER);
        }
        return pnWorkOrders;
    }

    private JScrollPane getSpWorkOrders() {
        if (spWorkOrders == null) {
            spWorkOrders = new JScrollPane();
            spWorkOrders.setViewportView(getTableWorkOrders());
            spWorkOrders.getViewport()
                        .setBackground(new Color(255, 255, 255));
        }
        return spWorkOrders;
    }

    private JTable getTableWorkOrders() {
        if (tableWorkOrders == null) {
            tableWorkOrders = new JTable();
            tableWorkOrders.setModel(tableWorkOrdersModel);
        }
        return tableWorkOrders;
    }
}
