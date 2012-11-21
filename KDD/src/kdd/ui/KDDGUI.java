package kdd.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import kdd.data.OriginalData;

public class KDDGUI extends JPanel {

    private static final long serialVersionUID = 5483099078233967340L;
    private JTable table;

    public KDDGUI() {
        super(new BorderLayout());

        // The menu bar
        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open File...");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                JFrame frame = new JFrame("Open File");

                fc.setDialogTitle("Open File");

                int state = fc.showOpenDialog(frame);

                if (state == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        OriginalData originalData = CSVFile.readCSV(file.getAbsolutePath());
                    } catch (Exception e) {
                        // TODO
                        e.printStackTrace();
                    }
                }
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        fileMenu.add(openItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitItem);
        jMenuBar.add(fileMenu);

        add(jMenuBar);

        // The table
        this.table = new JTable(new DataTableModel(4, 7));
        // this.table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        // this.table.setFillsViewportHeight(true);
        // this.table.setTableHeader(null);
        // this.table.setSelectionForeground(null);
        // this.table.setSelectionBackground(null);
        // this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPaneTable = new JScrollPane(this.table);

        // Add the scroll pane to this panel.
        add(scrollPaneTable, BorderLayout.SOUTH);
        // add(panel, BorderLayout.SOUTH);

    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame(" Action Reducts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        KDDGUI newContentPane = new KDDGUI();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
