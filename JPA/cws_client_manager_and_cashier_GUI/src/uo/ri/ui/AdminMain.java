package uo.ri.ui;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import uo.ri.ui.manager.MainWindow;

public class AdminMain {
    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        manageException(e);
                    }
                });
        new AdminMain().configure()
                       .run();
    }

    private AdminMain configure() {
        return this;
    }

    private AdminMain run() {
        MainWindow.main(null);
        return this;
    }

    private static void manageException(Throwable e) {
        SwingUtilities.invokeLater(() -> {
            showError(e);
        });
    }

    private static void showError(Throwable e) {
        JOptionPane.showMessageDialog(null,
                "An unrecoverable internal error has occurred, "
                        + "the program must terminate.\n" + "Problem: "
                        + e.getMessage(),
                "Tecnical problem detected", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
