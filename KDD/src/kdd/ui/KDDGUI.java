package kdd.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import kdd.data.DiscernableData;
import kdd.data.OriginalData;
import kdd.data.ReductData;

/**
 * The GUI class of the application.
 */
public class KDDGUI extends JPanel {

    private static final long serialVersionUID = 5483099078233967340L;

    /**
     * The JTable instance displays all data.
     */
    private static JTable table;

    /**
     * The table model instance for JTable.
     */
    private static DataTableModel tableModel;

    /**
     * The desired value in JTextField.
     */
    private static JTextField desiredValue = new JTextField("");

    /**
     * The progress bar.
     */
    private static JProgressBar bar;

    /**
     * Constructor. Initialize the GUI.
     */
    public KDDGUI() {
        super(new BorderLayout());

        // Display The desired value.
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.add(new JLabel("Desired Value:"));
        panel.add(desiredValue);

        // Add RUN button.
        JButton runButton = new JButton("RUN");
        panel.add(runButton);

        // Set action listener for the RUN button.
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Calendar startTime = Calendar.getInstance();

                        OriginalData data = OriginalData.getInstance();
                        if (data != null) {
                            if ("".equals(desiredValue.getText())) {
                                JOptionPane.showMessageDialog(null, "Set desired value!", "Alert",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                try {
                                    data.setDesiredValue(desiredValue.getText());
                                    DiscernableData discernableData = data.caLculateDiscernableData();
                                    ReductData reductData = discernableData.calculateReductData();
                                    reductData.createRecommendation();
                                } catch (java.lang.OutOfMemoryError e) {
                                    JOptionPane.showMessageDialog(null, "OutOfMemoryError!", "Alert",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Open data file first!", "Alert",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        if (data != null && !"".equals(data.getDesiredValue().trim())) {
                            Calendar endTime = Calendar.getInstance();
                            System.out.println("Time-consuming (seconds): "
                                    + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Time-consuming (seconds): "
                                            + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0,
                                    "Finished", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }).start();
            }
        });

        add(panel, BorderLayout.NORTH);

        // The table
        KDDGUI.tableModel = new DataTableModel(7, 7);
        KDDGUI.table = new JTable(KDDGUI.tableModel);
        KDDGUI.table.setPreferredScrollableViewportSize(new Dimension(600, 500));
        KDDGUI.table.setFillsViewportHeight(true);
        KDDGUI.table.getTableHeader().setReorderingAllowed(false);
        KDDGUI.table.setSelectionForeground(null);
        KDDGUI.table.setSelectionBackground(null);
        KDDGUI.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPaneTable = new JScrollPane(KDDGUI.table);

        // Add the scroll pane to this panel.
        add(scrollPaneTable, BorderLayout.CENTER);

        KDDGUI.bar = new JProgressBar();
        KDDGUI.bar.setMaximum(100);
        KDDGUI.bar.setStringPainted(true);
        add(KDDGUI.bar, BorderLayout.SOUTH);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame(" Action Reducts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The menu bar
        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu1 = new JMenu("File");

        // The menu for open file.
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

                        DataTableModel tableModel = new DataTableModel(originalData.getTableColumnNames(), originalData
                                .getTableData());
                        table.setModel(tableModel);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.toString(), "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // The menu for close application
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        fileMenu1.add(openItem);
        fileMenu1.add(new JSeparator());
        fileMenu1.add(exitItem);
        jMenuBar.add(fileMenu1);

        // The menu for display of Original Data.
        JMenu fileMenu2 = new JMenu("View");
        JMenuItem originalItem = new JMenuItem("Original Data");
        originalItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                OriginalData data = OriginalData.getInstance();
                if (data != null) {
                    DataTableModel tableModel = new DataTableModel(data.getTableColumnNames(), data.getTableData());
                    table.setModel(tableModel);
                    table.repaint();
                }
            }
        });

        // The menu for display of Discernable Data.
        JMenuItem discernableItem = new JMenuItem("Discernable Data");
        discernableItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                DiscernableData data = DiscernableData.getInstance();
                if (data != null) {
                    DataTableModel tableModel = new DataTableModel(data.getTableColumnNames(), data.getTableData());
                    table.setModel(tableModel);
                    table.repaint();
                }
            }
        });

        // The menu for display of Reduct Data.
        JMenuItem reductItem = new JMenuItem("Reduct Data");
        reductItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ReductData data = ReductData.getInstance();
                if (data != null) {
                    DataTableModel tableModel = new DataTableModel(data.getTableColumnNames(), data.getTableData());
                    table.setModel(tableModel);
                    table.repaint();
                }
            }
        });

        // The menu for display of Recommendations.
        JMenuItem recommendationItem = new JMenuItem("Recommendations");
        recommendationItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ReductData reductData = ReductData.getInstance();
                if (reductData != null) {
                    DataTableModel tableModel = new DataTableModel(reductData.getRecommendationColumn(), reductData
                            .createRecommendation());
                    table.setModel(tableModel);
                    table.repaint();
                    setColumnWidth(600);
                }
            }
        });

        fileMenu2.add(originalItem);
        fileMenu2.add(discernableItem);
        fileMenu2.add(reductItem);
        fileMenu2.add(recommendationItem);
        jMenuBar.add(fileMenu2);

        frame.setJMenuBar(jMenuBar);

        // Create and set up the content pane.
        KDDGUI newContentPane = new KDDGUI();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Set column width in the JTable.
     * 
     * @param width
     *            The width value.
     */
    private static void setColumnWidth(int width) {
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(width);
        }
    }

    /**
     * Update progress bar
     * 
     * @param num
     *            The new number of progress bar.
     */
    public static void showProgress(int num) {
        if (KDDGUI.bar != null) {
            KDDGUI.bar.setValue(num);
        }
    }

    /**
     * The main function.
     * 
     * @param args
     */
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
