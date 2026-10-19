package uo.ri.ui.manager.contracttype;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.ri.ui.manager.contracttype.action.AddContractType;
import uo.ri.ui.manager.contracttype.action.DeleteContractType;
import uo.ri.ui.manager.contracttype.action.FindContractTypeByName;
import uo.ri.ui.manager.contracttype.action.ListAllContractTypes;
import uo.ri.ui.manager.contracttype.action.UpdateContractType;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ContractTypeMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JPanel pnManagements;
    private JButton btAddContractType;
    private JButton btnDeleteContractType;
    private JButton btnFindContractType;
    private JButton btnListAllContrct;
    private JButton btnUpdateContractType;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ContractTypeMenu frame = new ContractTypeMenu();
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
    public ContractTypeMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 690, 431);
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
            lblNewLabel = new JLabel("Contract types management");
            lblNewLabel.setBackground(new Color(255, 255, 255));
            lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        }
        return lblNewLabel;
    }

    private JPanel getPnManagements() {
        if (pnManagements == null) {
            pnManagements = new JPanel();
            pnManagements.setBackground(new Color(255, 255, 255));
            pnManagements.setLayout(null);
            pnManagements.add(getBtAddContractType());
            pnManagements.add(getBtnDeleteContractType());
            pnManagements.add(getBtnFindContractType());
            pnManagements.add(getBtnListAllContrct());
            pnManagements.add(getBtnUpdateContractType());
        }
        return pnManagements;
    }

    private JButton getBtAddContractType() {
        if (btAddContractType == null) {
            btAddContractType = new JButton("Add contract type");
            btAddContractType.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    AddContractType act = new AddContractType();
                    act.setVisible(true);
                }
            });
            btAddContractType.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btAddContractType.setBounds(51, 75, 152, 78);
        }
        return btAddContractType;
    }

    private JButton getBtnDeleteContractType() {
        if (btnDeleteContractType == null) {
            btnDeleteContractType = new JButton("Delete contract type");
            btnDeleteContractType.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DeleteContractType dct = new DeleteContractType();
                    dct.setVisible(true);
                }
            });
            btnDeleteContractType.setFont(new Font("Tahoma", Font.PLAIN, 13));
            btnDeleteContractType.setBounds(255, 75, 152, 78);
        }
        return btnDeleteContractType;
    }

    private JButton getBtnFindContractType() {
        if (btnFindContractType == null) {
            btnFindContractType = new JButton("Find contract type by name");
            btnFindContractType.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FindContractTypeByName fct = new FindContractTypeByName();
                    fct.setVisible(true);
                }
            });
            btnFindContractType.setFont(new Font("Tahoma", Font.PLAIN, 10));
            btnFindContractType.setBounds(367, 199, 165, 78);
        }
        return btnFindContractType;
    }

    private JButton getBtnListAllContrct() {
        if (btnListAllContrct == null) {
            btnListAllContrct = new JButton("List all contrct types");
            btnListAllContrct.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ListAllContractTypes lact = new ListAllContractTypes();
                    lact.setVisible(true);
                }
            });
            btnListAllContrct.setFont(new Font("Tahoma", Font.PLAIN, 13));
            btnListAllContrct.setBounds(155, 198, 152, 78);
        }
        return btnListAllContrct;
    }

    private JButton getBtnUpdateContractType() {
        if (btnUpdateContractType == null) {
            btnUpdateContractType = new JButton("Update contract type");
            btnUpdateContractType.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    UpdateContractType uct = new UpdateContractType();
                    uct.setVisible(true);
                }
            });
            btnUpdateContractType.setFont(new Font("Tahoma", Font.PLAIN, 13));
            btnUpdateContractType.setBounds(459, 77, 152, 78);
        }
        return btnUpdateContractType;
    }
}
