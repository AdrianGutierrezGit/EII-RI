package uo.ri.ui.manager.cashier.action;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.exception.BusinessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class InvoiceWorkorder extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private JLabel lbWorkOrderId;
    private JTextField txWorkOrderId;
    private JButton btAddWorkOrder;
    private List<String> workOrdersIds = new ArrayList<String>();
    private JButton btGenerateInvoice;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InvoiceWorkorder frame = new InvoiceWorkorder();
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
    public InvoiceWorkorder() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 477, 265);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getPanel());
        contentPane.add(getBtAddWorkOrder());
        contentPane.add(getBtGenerateInvoice());

    }

    private JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel();
            panel.setBorder(new TitledBorder(
                    new EtchedBorder(EtchedBorder.LOWERED,
                            new Color(255, 255, 255), new Color(160, 160, 160)),
                    "Enter the ID of the work order you want to add to the invoice:",
                    TitledBorder.LEADING, TitledBorder.TOP, null,
                    new Color(0, 0, 0)));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBounds(63, 33, 333, 125);
            panel.setLayout(null);
            panel.add(getLbWorkOrderId());
            panel.add(getTxWorkOrderId());
        }
        return panel;
    }

    private JLabel getLbWorkOrderId() {
        if (lbWorkOrderId == null) {
            lbWorkOrderId = new JLabel("Work order id:");
            lbWorkOrderId.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lbWorkOrderId.setBounds(27, 48, 100, 19);
        }
        return lbWorkOrderId;
    }

    private JTextField getTxWorkOrderId() {
        if (txWorkOrderId == null) {
            txWorkOrderId = new JTextField();
            txWorkOrderId.setBounds(127, 49, 177, 18);
            txWorkOrderId.setColumns(10);
        }
        return txWorkOrderId;
    }

    private JButton getBtAddWorkOrder() {
        if (btAddWorkOrder == null) {
            btAddWorkOrder = new JButton("Add work order");
            btAddWorkOrder.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addWorkOrder();
                }
            });
            btAddWorkOrder.setBounds(89, 187, 123, 20);
        }
        return btAddWorkOrder;
    }

    protected void addWorkOrder() {
        this.workOrdersIds.add(getTxWorkOrderId().getText());
        getTxWorkOrderId().setText("");
        JOptionPane.showMessageDialog(this, "Work order added",
                "Add work order", JOptionPane.INFORMATION_MESSAGE);
    }

    private Object getMessageInvoice(InvoiceDto invoice) {
        double importeConIVa = invoice.amount;
        double iva = invoice.vat;
        double importeSinIva = importeConIVa / ( 1 + iva / 100 );
        String plantilla = "Invoice nº: %d\n" + "\tDate: %1$td/%1$tm/%1$tY\n"
                + "\tTotal: %.2f €\n" + "\tIva: %.1f %% \n"
                + "\tTotal with IVA: %.2f €\n" + "\tState: %s\n";
        return String.format(plantilla, invoice.number, invoice.date,
                importeSinIva, invoice.vat, invoice.amount, invoice.state);
    }

    private JButton getBtGenerateInvoice() {
        if (btGenerateInvoice == null) {
            btGenerateInvoice = new JButton("Generate invoice");
            btGenerateInvoice.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        generateInvoice();
                    } catch (BusinessException exception) {
                        JOptionPane.showMessageDialog(null,
                                exception.getMessage(),
                                "Error creating invoice",
                                JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            });
            btGenerateInvoice.setBounds(239, 187, 137, 20);
        }
        return btGenerateInvoice;
    }

    protected void generateInvoice() throws BusinessException {
        InvoicingService cs = Factories.service.forCreateInvoiceService();
        InvoiceDto invoice = cs.create(workOrdersIds);
        JOptionPane.showInternalMessageDialog(this, getMessageInvoice(invoice),
                "Generate invoice", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
