import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Octane {

    List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        new Octane().startUI();
    }

    public Octane() {

    }

    public void startUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame("Octane Updater");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 40));
        JButton cancelButton = new JButton("Cancel");

        JButton pickButton = new JButton("Select All Features");
        JButton unpickButton = new JButton("De-Select All Features");


        MyTableModel model = new MyTableModel();
        for (int i = 0; i < 150; i++) {
            model.addRow(new Object[]{i, i, false});
        }
        pickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 150; i++) {
                    model.setValueAt(true, i, 2);
                }
            }
        });
        unpickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 150; i++) {
                    model.setValueAt(false, i, 2);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(list);
                System.exit(0);

            }
        });

        JPanel mainPanel = new JPanel();
        JPanel topButtonPanel = new JPanel();
        JPanel bottomButtonPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        topButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        bottomButtonPanel.add(submitButton);
        bottomButtonPanel.add(cancelButton);

        topButtonPanel.add(pickButton);
        topButtonPanel.add(unpickButton);

        mainPanel.add(topButtonPanel, BorderLayout.NORTH);
        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public class MyTableModel extends DefaultTableModel {

        public MyTableModel() {
            super(new String[]{"ID", "Name", "Select for Octane"}, 0);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class clazz = String.class;
            switch (columnIndex) {
                case 0:
                    clazz = Integer.class;
                    break;
                case 2:
                    clazz = Boolean.class;
                    break;
            }
            return clazz;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 2;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (aValue instanceof Boolean && column == 2) {
                Vector rowData = (Vector) getDataVector().get(row);
                rowData.set(2, (boolean) aValue);
                fireTableCellUpdated(row, column);
                if ((Boolean) aValue) {
                    list.add(row);
                } else {
                    list.remove(Integer.valueOf(row));
                }
            }
        }

    }

}