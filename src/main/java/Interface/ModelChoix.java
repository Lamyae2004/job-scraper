package Interface;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public class ModelChoix {

    private final Image imageBack = new ImageIcon(Login.class.getResource("/images/logo1.png"))
            .getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
    Based based = new Based();
    Connection objetConnection = based.connect();
    static String name;

    public static void main(String[] args, String username) {
        EventQueue.invokeLater(() -> {
            try {
                name = username;
                ModelChoix frame = new ModelChoix();
                frame.createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Modélisation Machine Learning");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(939, 577);
        frame.getContentPane().setLayout(null);

        // Gradient Background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = Color.decode("#ABDFE1");
                Color color2 = Color.decode("#ABDFE1");
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        gradientPanel.setLayout(null);
        frame.getContentPane().add(gradientPanel);

        JLabel placeBack = new JLabel("");
        placeBack.setHorizontalAlignment(SwingConstants.CENTER);
        placeBack.setBounds(5, 13, 80, 80);
        gradientPanel.add(placeBack);
        placeBack.setIcon(new ImageIcon(imageBack));

        JLabel title = new JLabel("Modélisation Machine Learning", JLabel.CENTER);
        title.setFont(new Font("Roboto", Font.BOLD, 24));
        title.setForeground(Color.decode("#1466b8"));
        title.setBounds(150, 40, 640, 40);
        gradientPanel.add(title);

        JLabel typeLabel = new JLabel("Type d'algorithme :");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typeLabel.setForeground(Color.decode("#FFA500"));
        typeLabel.setBounds(250, 140, 200, 30);
        gradientPanel.add(typeLabel);

        JRadioButton classificationButton = new JRadioButton("Classification");
        classificationButton.setForeground(Color.WHITE);
        classificationButton.setFont(new Font("Arial", Font.BOLD, 14));
        classificationButton.setBackground(Color.decode("#ABDFE1"));
        classificationButton.setBounds(430, 140, 150, 30);
        gradientPanel.add(classificationButton);

        JRadioButton clusteringButton = new JRadioButton("Clustering");
        clusteringButton.setForeground(Color.WHITE);
        clusteringButton.setFont(new Font("Arial", Font.BOLD, 14));
        clusteringButton.setBackground(Color.decode("#ABDFE1"));
        clusteringButton.setBounds(590, 140, 150, 30);
        gradientPanel.add(clusteringButton);

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(classificationButton);
        typeGroup.add(clusteringButton);

        JLabel algoLabel = new JLabel("Algorithme :");
        algoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        algoLabel.setForeground(Color.decode("#FFA500"));
        algoLabel.setBounds(250, 180, 200, 30);
        gradientPanel.add(algoLabel);

        JComboBox<String> algoComboBox = new JComboBox<>();
        algoComboBox.setEnabled(false);
        algoComboBox.setFont(new Font("Roboto", Font.PLAIN, 14));
        algoComboBox.setBounds(430, 180, 310, 30);
        gradientPanel.add(algoComboBox);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setBounds(250, 220, 490, 120);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Color.decode("#ABDFE1"));
        gradientPanel.add(optionsPanel);

        JButton executeButton = new JButton("Lancer");
        executeButton.setFont(new Font("Arial", Font.BOLD, 16));
        executeButton.setForeground(Color.WHITE);
        executeButton.setBackground(Color.decode("#FFA500"));
        executeButton.setBounds(380, 350, 180, 40);
        executeButton.setEnabled(false);
        gradientPanel.add(executeButton);


        classificationButton.addActionListener(e -> {
            algoComboBox.setEnabled(true);
            algoComboBox.removeAllItems();
            algoComboBox.addItem("Decision Tree");
            algoComboBox.addItem("KNN");
            algoComboBox.addItem("SVM");
            executeButton.setEnabled(false);
        });

        clusteringButton.addActionListener(e -> {
            algoComboBox.setEnabled(true);
            algoComboBox.removeAllItems();
            algoComboBox.addItem("K-Means");
            executeButton.setEnabled(false);
        });
        algoComboBox.addActionListener(e -> {
            if (algoComboBox.getSelectedItem() != null) {
                executeButton.setEnabled(true);
            }
        });


        algoComboBox.addActionListener(e -> {
            String selectedAlgo = (String) algoComboBox.getSelectedItem();
            optionsPanel.removeAll();

            if ("Decision Tree".equals(selectedAlgo)) {
                JLabel optionsLabel = new JLabel("Options pour Decision Tree :");
                optionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
                optionsLabel.setForeground(Color.decode("#FFA500"));
                optionsPanel.add(optionsLabel);

                optionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                JRadioButton bySector = new JRadioButton("Par offre");
                bySector.setForeground(Color.WHITE);
                bySector.setFont(new Font("Arial", Font.BOLD, 14));
                bySector.setBackground(Color.decode("#ABDFE1"));

                JRadioButton bySkills = new JRadioButton("Par compétence");
                bySkills.setForeground(Color.WHITE);
                bySkills.setFont(new Font("Arial", Font.BOLD, 14));
                bySkills.setBackground(Color.decode("#ABDFE1"));

                JRadioButton predict = new JRadioButton("Prédire");
                predict.setForeground(Color.WHITE);
                predict.setFont(new Font("Arial", Font.BOLD, 14));
                predict.setBackground(Color.decode("#ABDFE1"));

                ButtonGroup group = new ButtonGroup();
                group.add(bySector);
                group.add(bySkills);
                group.add(predict);

                optionsPanel.add(bySector);
                optionsPanel.add(bySkills);
                optionsPanel.add(predict);

                ActionListener enableLaunchButton = e1 -> executeButton.setEnabled(true);
                bySector.addActionListener(enableLaunchButton);
                bySkills.addActionListener(enableLaunchButton);
                predict.addActionListener(enableLaunchButton);
            }

            optionsPanel.revalidate();
            optionsPanel.repaint();
        });

        executeButton.addActionListener(e -> {
            String selectedAlgo = (String) algoComboBox.getSelectedItem();
            String selectedType = classificationButton.isSelected() ? "Classification" : "Clustering";
            if("Classification".equals(selectedType)){
                if ("Decision Tree".equals(selectedAlgo)) {
                    for (Component comp : optionsPanel.getComponents()) {
                        if (comp instanceof JRadioButton && ((JRadioButton) comp).isSelected()) {
                            String selectedOption = ((JRadioButton) comp).getText();
                            System.out.println("Option sélectionnée : " + selectedOption);

                            if ("Par offre".equals(selectedOption)) {
                                openDecisionTreePage();
                            } else if ("Par compétence".equals(selectedOption)) {
                                openClassificationTask();
                            } else if ("Prédire".equals(selectedOption)) {
                                openPredictionTsk();
                            }
                        }
                    }
                } else if ("KNN".equals(selectedAlgo)) {

                    openKNNPage();
                } else if ("SVM".equals(selectedAlgo)) {

                    openSVMPage();
                }
            } else if ("Clustering".equals(selectedType)) {
                if ("K-Means".equals(selectedAlgo)) {
                    // Ouvrir une nouvelle fenêtre pour K-Means
                    openKMeansPage();
                }
            }
        });

        placeBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AccueilChoix.main(null, name);
                frame.dispose();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openDecisionTreePage() {
        SwingUtilities.invokeLater(() -> {
            try {
                ClassificationTsk.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void openKMeansPage() {
        SwingUtilities.invokeLater(() -> {
            try {
                KMeansWithGraph.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void openPredictionTsk() {
        SwingUtilities.invokeLater(() -> {
            try {
                PredictionTsk.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void openClassificationTask() {
        SwingUtilities.invokeLater(() -> {
            try {
                ClassificationTask.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void openKNNPage() {
        SwingUtilities.invokeLater(() -> {
            try {
                EvaluationGraph.main(new String[]{});
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  // Appeler la méthode main de KMeansWithGraph
        });
    }
    private void openSVMPage() {
        SwingUtilities.invokeLater(() -> {
            try {
                ClassificationSVM.main(new String[]{});
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  // Appeler la méthode main de KMeansWithGraph
        });
    }

}