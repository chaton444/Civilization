package Mapeo;

import javax.swing.*;
import java.awt.*;

class HexagonButton extends JButton {

    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 36;//tamaño del hexagono
    public static final int LENGTH = 20;
    public static final int WIDTH = 10;
    private int row = 0;
    private int col = 0;
    private int tipoBoton;
    private String UrlImagen;

    String e1 = "/img/edificaciones/ciudadela.png";
    String e2 = "/img/edificaciones/granbiblioteca.png";
    String e3 = "/img/edificaciones/jardinesColgantes.png";
    String e4 = "/img/edificaciones/mercadoEspecias.png";
    String e5 = "/img/edificaciones/observatorio.png";

    String d1 = "La Ciudadela: Una imponente fortaleza defensiva que protege tus fronteras. \n"
            + "Proporciona bonificaciones defensivas a tus tropas y puede aumentar la moral de tus soldados cercanos en caso de ataque enemigo.";
    String d2 = "La Gran Biblioteca: Una majestuosa biblioteca que ofrece beneficios significativos en la investigación y el desarrollo de tecnologías.\n"
            + "Aumenta la velocidad de investigación y proporciona bonificaciones a la educación de tus ciudadanos.";
    String d3 = "Los Jardines Colgantes: Una obra maestra arquitectónica que embellece tus ciudades y mejora la calidad de vida de tus ciudadanos. \n"
            + "Proporciona bonificaciones a la felicidad y el crecimiento poblacional.";
    String d4 = "El Mercado de Especias: Un bullicioso centro de comercio que impulsa la economía de tu civilización. \n"
            + "Aumenta los ingresos generados por el comercio y brinda acceso a recursos exóticos de otras culturas.";
    String d5 = "El Observatorio Astronómico: Una estructura que impulsa la exploración y el avance científico de tu civilización. \n"
            + "Aumenta la visibilidad del mapa y desbloquea eventos y descubrimientos relacionados con la astronomía.";

    Edificaciones Ciudadela = new Edificaciones(e1, d1);
    Edificaciones GranBiblioteca = new Edificaciones(e2, d2);
    Edificaciones JardinesColgantes = new Edificaciones(e3, d3);
    Edificaciones MercadoEspecias = new Edificaciones(e4, d4);
    Edificaciones ObservatorioAstronomico = new Edificaciones(e5, d5);

    Edificaciones edificios[] = {Ciudadela, GranBiblioteca, JardinesColgantes, MercadoEspecias, ObservatorioAstronomico};

    String imagenes[] = {e1, e2, e3, e4, e5};

    public String getUrlImagen() {
        return UrlImagen;
    }

    public void setUrlImagen(String UrlImagen) {
        this.UrlImagen = UrlImagen;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public HexagonButton(int row, int col) {
        setContentAreaFilled(false);
        setFocusPainted(true);
        setBorderPainted(false);

        this.row = row;
        this.col = col;
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonActionPerformed(evt);
            }
        });
    }

    private void BotonActionPerformed(java.awt.event.ActionEvent evt) {
        maquinaDeEstados();

    }

    public void actualizarTamaño() {

        int nuevoAncho = (int) (65 * MapaVisual.escala);
        int nuevoAlto = (int) (71 * MapaVisual.escala);

        setSize(new Dimension(nuevoAncho, nuevoAlto));
//    setBounds((int) (getX() * MapaVisual.escala), (int) (getY() * MapaVisual.escala), 105, 95);

    }

    public void setColorHexagono(Color color) {
        setBackground(color);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon hex = new Polygon();
        for (int i = 0; i < SIDES; i++) {
            hex.addPoint((int) (50 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES) - 18),//vertical
                    (int) (50 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES) - 15));//horizontal
        }

        g.setColor(getBackground());

        g.drawPolygon(hex);
    }

    public void maquinaDeEstados() {
        switch (tipoBoton) {
            case 0:
                System.out.println("es boton QLERO");
                break;
            case 1:
                System.out.println("Es boton de construccion");
                OptionPaneTipoConstruccion();
                break;

        }

    }

    public void setTipoBoton(int tipoBoton) {
        this.tipoBoton = tipoBoton;

    }

    public int getTipoBoton() {
        return tipoBoton;
    }

    public void OptionPaneTipoConstruccion() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        for (int i = 0; i <= 4; i++) {
            ImageIcon icon = new ImageIcon(getClass().getResource(edificios[i].getUrlImagen()));
            JButton button = new JButton(icon);
            button.setActionCommand(String.valueOf(i));
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Seleccionaste la opción " + e.getActionCommand());
            });
            
            constraints.insets = new Insets(0, 0, 0, 20); // Margen superior, izquierdo, inferior y derecho
            JTextArea textArea = new JTextArea(2, 40);
            textArea.setText(edificios[i].getDescripcionEdificacion());
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(false);
            textArea.setEditable(false);
            textArea.setFocusable(false);
            textArea.setBackground(UIManager.getColor("Label.background"));
            textArea.setFont(UIManager.getFont("Label.font"));
            textArea.setBorder(UIManager.getBorder("Label.border"));
            button.setPreferredSize(new Dimension(100, 100));

            constraints.gridx = 0; // Primera columna
            constraints.gridy = i; // Fila i
            panel.add(button, constraints);

            constraints.gridx = 1; // Segunda columna
            constraints.gridy = i; // Fila i
            panel.add(textArea, constraints);
        }

        JOptionPane.showOptionDialog(null, panel, "Selector de Edificaciones.",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new Object[]{}, null);
    }

}
