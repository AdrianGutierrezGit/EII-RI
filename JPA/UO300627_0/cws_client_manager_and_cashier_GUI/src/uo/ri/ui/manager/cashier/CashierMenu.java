package uo.ri.ui.manager.cashier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.ri.ui.manager.cashier.action.FindNotInvoicedWorkOrders;
import uo.ri.ui.manager.cashier.action.InvoiceWorkorder;

import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CashierMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JPanel pnManagements;
    private JButton btInvoiceWorkOrder;
    private JButton btnFindNotInvoiceWorkOrder;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CashierMenu frame = new CashierMenu();
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
    public CashierMenu() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 645, 382);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(getLblNewLabel(), BorderLayout.NORTH);
        contentPane.add(getPnManagements(), BorderLayout.CENTER);

    }

    private JLabel getLblNewLabel() {
        if (lblNewLabel == null) {
            lblNewLabel = new JLabel("Cashier managements");
            lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        }
        return lblNewLabel;
    }

    private JPanel getPnManagements() {
        if (pnManagements == null) {
            pnManagements = new JPanel();
            pnManagements.setBackground(new Color(255, 255, 255));
            pnManagements.setLayout(null);
            pnManagements.add(getBtInvoiceWorkOrder());
            pnManagements.add(getBtnFindNotInvoiceWorkOrder());
        }
        return pnManagements;
    }

    private JButton getBtInvoiceWorkOrder() {
        if (btInvoiceWorkOrder == null) {
            btInvoiceWorkOrder = new JButton("Invoice work order");
            btInvoiceWorkOrder.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    InvoiceWorkorder iw = new InvoiceWorkorder();
                    iw.setVisible(true);
                }
            });
            btInvoiceWorkOrder.setFont(new Font("Tahoma", Font.PLAIN, 16));
            btInvoiceWorkOrder.setBounds(69, 93, 220, 100);
        }
        return btInvoiceWorkOrder;
    }

    private JButton getBtnFindNotInvoiceWorkOrder() {
        if (btnFindNotInvoiceWorkOrder == null) {
            btnFindNotInvoiceWorkOrder = new JButton(
                    "Find not invoice work order");
            btnFindNotInvoiceWorkOrder.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FindNotInvoicedWorkOrders fw =
                                                 new FindNotInvoicedWorkOrders();
                    fw.setVisible(true);
                }
            });
            btnFindNotInvoiceWorkOrder.setFont(
                    new Font("Tahoma", Font.PLAIN, 15));
            btnFindNotInvoiceWorkOrder.setBounds(336, 93, 220, 100);
        }
        return btnFindNotInvoiceWorkOrder;
    }
}
