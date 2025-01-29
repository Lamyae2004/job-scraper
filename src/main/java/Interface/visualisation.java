package Interface;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.sql.*;
import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.Rotation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class visualisation extends JFrame {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/data";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";
    static String name;
    Based based = new Based();
    Connection objetConnection=based.connect();

    public static void main(String[] args,String username) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Instanciez et affichez votre interface ici
                name=username;
                visualisation frame = new visualisation("Visualisation des données");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public visualisation(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Apply a modern theme
        applyModernTheme();

        // Tabs for charts
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Post Count by Region", createPostCountByRegionChart());
        tabbedPane.addTab("Contract Type Distribution", createContractTypeDistributionChart());
        tabbedPane.addTab("Telework Distribution", createTeleworkDistributionChart());
        tabbedPane.addTab("Post Count by Date", createPostCountByDateChart());
        tabbedPane.addTab("Experience Level Distribution", createExperienceLevelDistributionChart());
        tabbedPane.addTab("Post Count by City", createCityPostCountChart());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Instanciez et affichez votre interface ici
                visualisation frame = new visualisation("Visualisation des données");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void applyModernTheme() {
        StandardChartTheme theme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();
        theme.setTitlePaint(Color.decode("#34495E")); // Darker title color
        theme.setSubtitlePaint(Color.decode("#7F8C8D")); // Subtitle color
        theme.setChartBackgroundPaint(Color.decode("#ECF0F1")); // Light background
        theme.setPlotBackgroundPaint(Color.WHITE);
        theme.setGridBandPaint(Color.decode("#BDC3C7")); // Light grid band
        theme.setAxisLabelPaint(Color.decode("#2C3E50")); // Axis label color
        theme.setTickLabelPaint(Color.decode("#34495E")); // Tick label color
        theme.setShadowVisible(false);
        theme.setBarPainter(new StandardBarPainter());
        theme.setExtraLargeFont(new Font("Arial", Font.BOLD, 20));
        theme.setLargeFont(new Font("Arial", Font.PLAIN, 18));
        theme.setRegularFont(new Font("Arial", Font.PLAIN, 16));
        ChartFactory.setChartTheme(theme);

    }

    private void customizeChart(JFreeChart chart, String colorHex) {

        TextTitle title = chart.getTitle();
        title.setFont(new Font("Roboto", Font.BOLD, 22));
        title.setPaint(Color.decode(colorHex));

        chart.setBackgroundPaint(new GradientPaint(
                0, 0, Color.decode("#F5F7FA"),
                0, 1000, Color.decode("#ECF0F1")
        ));

        // Add padding around the chart
        chart.setPadding(new RectangleInsets(10, 10, 10, 10));

        // Customize subtitle appearance (if any)
        for (Object subtitle : chart.getSubtitles()) {
            if (subtitle instanceof TextTitle) {
                TextTitle textSubtitle = (TextTitle) subtitle;
                textSubtitle.setFont(new Font("Roboto", Font.PLAIN, 16)); // Smaller font for subtitles
                textSubtitle.setPaint(Color.decode("#7F8C8D")); // Softer color for subtitles
            }
        }
    }


    private void stylePiePlot(PiePlot3D plot, String colorHex) {
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.8f);
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 16));
        plot.setBackgroundPaint(Color.decode("#ECF0F1")); // Set pie plot background
        plot.setOutlineVisible(false);
        plot.setLabelOutlinePaint(Color.decode("#BDC3C7")); // Label outline color
        plot.setLabelBackgroundPaint(Color.WHITE); // Label background
        plot.setDepthFactor(0.2); // Add 3D effect
    }

    private void customizeBarChartRenderer(CategoryPlot plot) {
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Define gradient colors for the 3D effect
        GradientPaint frontGradient1 = new GradientPaint(0.0f, 0.0f, Color.decode("#75ade6"), 0.0f, 1.0f, Color.decode("#2980B9"));
        GradientPaint frontGradient2 = new GradientPaint(0.0f, 0.0f, Color.decode("#061873"), 0.0f, 1.0f, Color.decode("#C0392B"));

        Color sideColor1 = Color.decode("#75ade6"); // Darker shade for the side face
        Color sideColor2 = Color.decode("#0c045c");

        // Apply gradients to the front faces of the bars
        renderer.setSeriesPaint(0, frontGradient1);
        renderer.setSeriesPaint(1, frontGradient2);

        // Enable shadows for added depth
        renderer.setShadowVisible(true);
        renderer.setShadowPaint(Color.decode("#7F8C8D")); // Shadow color
        renderer.setShadowXOffset(6.0); // Horizontal shadow offset
        renderer.setShadowYOffset(6.0); // Vertical shadow offset

        // Customize item labels
        renderer.setDefaultItemLabelPaint(Color.WHITE); // Set item label color to white
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER
        ));

        // Customize bar outlines
        renderer.setDefaultOutlinePaint(Color.WHITE); // Outline color
        renderer.setDefaultOutlineStroke(new BasicStroke(1.5f)); // Outline thickness

        plot.setBackgroundPaint(Color.decode("#ECF0F1"));
        plot.setDomainGridlinePaint(Color.decode("#75ade6"));
        plot.setRangeGridlinePaint(Color.decode("#75ade6"));

        // Add custom drawing logic (if needed)
        renderer.setBarPainter(new BarPainter() {
            @Override
            public void paintBar(Graphics2D g2, BarRenderer renderer, int row, int column,
                                 RectangularShape bar, RectangleEdge edge) {
                // Draw the front face with gradient
                GradientPaint gradient = (GradientPaint) renderer.getSeriesPaint(row);
                g2.setPaint(gradient);
                g2.fill(bar);

                // Draw the side face (polygon)
                Polygon sideFace = new Polygon();
                sideFace.addPoint((int) bar.getMaxX(), (int) bar.getMinY());
                sideFace.addPoint((int) (bar.getMaxX() + 10), (int) (bar.getMinY() - 10));
                sideFace.addPoint((int) (bar.getMaxX() + 10), (int) (bar.getMaxY() - 10));
                sideFace.addPoint((int) bar.getMaxX(), (int) bar.getMaxY());
                g2.setPaint(sideColor1);
                g2.fill(sideFace);

                // Draw the top face (polygon)
                Polygon topFace = new Polygon();
                topFace.addPoint((int) bar.getMinX(), (int) bar.getMinY());
                topFace.addPoint((int) bar.getMaxX(), (int) bar.getMinY());
                topFace.addPoint((int) (bar.getMaxX() + 10), (int) (bar.getMinY() - 10));
                topFace.addPoint((int) (bar.getMinX() + 10), (int) (bar.getMinY() - 10));
                g2.setPaint(sideColor2);
                g2.fill(topFace);

                // Draw the outline
                g2.setPaint(renderer.getDefaultOutlinePaint());
                g2.draw(bar);
            }

            @Override
            public void paintBarShadow(Graphics2D g2, BarRenderer renderer, int row, int column,
                                       RectangularShape bar, RectangleEdge edge, boolean pegShadow) {
                // Use default shadow drawing
            }
        });
    }



    private JPanel createPostCountByRegionChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        retrieveDataAndPopulateDataset("SELECT region, COUNT(*) AS Count FROM dataset GROUP BY region", dataset);

        JFreeChart chart = ChartFactory.createBarChart(
                "Post Count by Region",
                "Region",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                false , true, false
        );

        customizeChart(chart, "#2980B9");
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        customizeBarChartRenderer(plot);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        // Customize bar renderer
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10); // Taille 10 pour rendre plus petit
        plot.getDomainAxis().setTickLabelFont(labelFont);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);


        return createChartPanel(chart);
    }

    private JPanel createContractTypeDistributionChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        retrieveDataAndPopulateDataset("SELECT TypeContrat, COUNT(*) AS Count FROM dataset GROUP BY TypeContrat", dataset);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Contract Type Distribution",
                dataset,
                true, true, false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        stylePiePlot(plot, "#E74C3C");
        return createChartPanel(chart);
    }

    private JPanel createTeleworkDistributionChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        retrieveDataAndPopulateDataset("SELECT Teletravail, COUNT(*) AS Count FROM dataset GROUP BY Teletravail", dataset);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Telework Distribution",
                dataset,
                true, true, false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        stylePiePlot(plot, "#AF7AC5");
        return createChartPanel(chart);
    }

    private JPanel createPostCountByDateChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        retrieveDataAndPopulateDataset("SELECT DatePublication, COUNT(*) AS Count FROM dataset GROUP BY DatePublication", dataset);

        JFreeChart chart = ChartFactory.createLineChart(
                "Post Count by Date",  // Chart title
                "Date",                 // X-axis label
                "Count",                // Y-axis label
                dataset,                // Dataset
                PlotOrientation.VERTICAL,
                false,                  // Include legend
                true,                   // Tooltips
                false                   // URLs
        );

        // Customize the chart
        customizeChart(chart, "#2980B9");

        // Get the CategoryPlot
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        // Update the plot background with a gradient blue background
        GradientPaint gradientBackground = new GradientPaint(0, 0, Color.decode("#D6EAF8"), 0, 1000, Color.decode("#EAF2F8"));
        plot.setBackgroundPaint(gradientBackground);

        // Set gridline colors to a light blue shade
        plot.setDomainGridlinePaint(Color.decode("#AED6F1"));
        plot.setRangeGridlinePaint(Color.decode("#AED6F1"));

        // Customize the line and add shapes
        if (plot.getRenderer() instanceof LineAndShapeRenderer) {
            LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

            // Set the stroke for the line
            renderer.setSeriesStroke(0, new BasicStroke(3.0f)); // Set thick stroke for the line
            renderer.setSeriesPaint(0, Color.decode("#1F618D")); // Set deep blue color for the series line

            // Enable shapes (markers) for data points
            renderer.setDefaultShapesVisible(true);
            renderer.setDefaultShapesFilled(true);

            // Optionally, set custom shapes (e.g., circle)
            renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6)); // Small circular markers
        }

        // Add slight transparency to the plot background
        plot.setBackgroundAlpha(0.95f);

        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        // Customize bar renderer
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10); // Taille 10 pour rendre plus petit
        plot.getDomainAxis().setTickLabelFont(labelFont);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);


        return createChartPanel(chart);

    }

    private JPanel createExperienceLevelDistributionChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        retrieveDataAndPopulateDataset("SELECT Experience, COUNT(*) AS Count FROM dataset GROUP BY Experience", dataset);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Experience Level Distribution",
                dataset,
                true, true, false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        stylePiePlot(plot, "#1ABC9C");
        return createChartPanel(chart);
    }

    private JPanel createCityPostCountChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        retrieveDataAndPopulateDataset("SELECT Ville, COUNT(*) AS Count FROM dataset GROUP BY Ville", dataset);

        JFreeChart chart = ChartFactory.createBarChart(
                "Post Count by City",
                "City",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        customizeChart(chart, "#2980B9");
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        customizeBarChartRenderer(plot);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        return createChartPanel(chart);
    }

    private JPanel createChartPanel(JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(850, 600));
        panel.setMouseWheelEnabled(true); // Zoom enabled
        return panel;
    }

    private void retrieveDataAndPopulateDataset(String query, DefaultCategoryDataset dataset) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String key = resultSet.getString(1);
                int value = resultSet.getInt(2);
                if (key != null && !key.trim().isEmpty()) {
                    dataset.addValue(value, "Offers", key);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
    }

    private void retrieveDataAndPopulateDataset(String query, DefaultPieDataset dataset) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String key = resultSet.getString(1);
                int value = resultSet.getInt(2);
                if (key != null && !key.trim().isEmpty()) {
                    dataset.setValue(key, value);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
    }
}