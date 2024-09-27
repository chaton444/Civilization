/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapeo;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatXcodeDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.Container;
import static java.awt.SystemColor.window;

import java.awt.event.ActionEvent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicScrollBarUI;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class MapaVisual extends JFrame {

    public static double escala = 1.0;
    public static AffineTransform transformacion = new AffineTransform();

    private static final int numero_hexagonos = 50;//AUXILIAR PARA SABER CUANTO SERA NUESTRO FRAME
    private static final int tamaño_Frame = 20;//AUXILIAR PARA MULTIPLICAR POR LOS HEXAGONOS PARA QUE HAGA UNA PANTALLA APROX
//imagenes de limite

    String mar2 = "/img/mar/mar2.png";
    String h1 = "/img/mar/h1.png";
    String izqSup = "/img/mar/izquierdaSuperior.png";
    String bOrilla = "/img/mar/bOrilla.png";
    String izqInf = "/img/mar/lia.png";
    String derInf = "/img/mar/derechaInferior.png";
    String granBiblio = "/img/edificaciones/granbiblioteca.png";
    String lpari = "/img/mar/lpari.png";
    String latIzq = "/img/mar/lateralizquierdo.png";
    String lpd = "/img/mar/lpd.png";
    String techo = "/img/mar/ma1.png";
    String latDer = "/img/mar/lateralderecho.png";
    String ld = "/img/mar/ld.png";

    String b1 = "/img/bosque/b1.png";
    String b2 = "/img/bosque/b2.png";
    String b3 = "/img/bosque/b3.png";
    String b4 = "/img/bosque/b4.png";
    String b5 = "/img/bosque/b5.png";
    String b7 = "/img/bosque/b7.png";

    String e1 = "/img/edificaciones/ciudadela.png";
    String e2 = "/img/edificaciones/granbiblioteca.png";
    String e3 = "/img/edificaciones/jardinesColgantes.png";
    String e4 = "/img/edificaciones/mercadoEspecias.png";
    String e5 = "/img/edificaciones/observatorio.png";
    String e7 = "/img/edificaciones/b7.png";

    String InicioImg = "/img/lugardeConstruccion.png";

//setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/" + imagenes[random])));
//fin de imagenes limite
    private int IndiceActual = 0;
    private int PasoActual = 0;
    private Nodo[][] Nodos;
    private static final int ROWS = 40;
    private static final int COLUMNS = 40;

    private HexagonButton[][] Botones = new HexagonButton[ROWS][COLUMNS];
    private boolean Finalizo = false;
    private boolean botonDerechoPrecionado = false;
    JPanel panel = new JPanel();
    public static boolean contorno = false;
    JPanel panelExterno = new JPanel();
    Container container = getContentPane();
    int offsetX;
    int offsetY;

    int[][] x = new int[ROWS][COLUMNS];
    int[][] y = new int[ROWS][COLUMNS];
    int aux = 0;

    ImageIcon[][] imagenes = new ImageIcon[ROWS][COLUMNS];
    String canciones[] = new String[34];

    public MapaVisual() {

        Nodos = new Nodo[numero_hexagonos][numero_hexagonos];
        Botones = new HexagonButton[numero_hexagonos][numero_hexagonos];
        getContentPane().setBackground(Color.BLACK);
        for (int i = 1; i <= 33; i++) {
            canciones[1] = "/musica/music/C" + i + "";
        }
        setSize(numero_hexagonos * tamaño_Frame + 50, numero_hexagonos * 12); // Aumenta el tamaño para incluir los nuevos botones
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

        container.setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        panelExterno.setLayout(new BorderLayout());
        panelExterno.setPreferredSize(new Dimension(2530, 2130));

        container.add(panelExterno, BorderLayout.CENTER);

        panelExterno.add(panel);
        // Crear un nuevo panel y configurar FlowLayout
        JPanel PanelJugadores = new JPanel();
        PanelJugadores.setLayout(new FlowLayout(FlowLayout.CENTER));

// Crear y agregar botones al panel
        JButton btnCaminoCorto = new JButton("Calculador De turno");
        JButton btnPasarTurno = new JButton("Pasar Turno");
        JButton btnVerEstadisticas = new JButton("Estadisticas");
        PanelJugadores.add(btnCaminoCorto);
        PanelJugadores.add(btnPasarTurno);
        PanelJugadores.add(btnVerEstadisticas);
        PanelJugadores.setBackground(Color.BLACK);

// Agregar el panel de botones al contenedor principal en la posición sur (abajo)
        container.add(PanelJugadores, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(panelExterno);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20); // Aumenta la velocidad de scroll horizontal
        scrollPane.getVerticalScrollBar().setUnitIncrement(20); // Aumenta la velocidad de scroll vertical
        // Ocultar las flechas de la barra de desplazamiento vertical
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        // Ocultar las flechas de la barra de desplazamiento horizontal
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

// Agrega el scrollPane al contenedor en lugar del panelExterno original
        container.add(scrollPane, BorderLayout.CENTER);
        container.setBackground(Color.BLACK); // Establecer el color de fondo del contenedor principal en negro
        panelExterno.setBackground(Color.BLACK); // Establecer el color de fondo del panelExterno en negro
        panel.setBackground(Color.BLACK); // Establecer el color de fondo del panel en negro

        panel.setLayout(null); // Desactiva el diseño para poder posicionar los botones manualmente

//
//scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
//    @Override
//    protected void configureScrollBarColors() {
//        this.thumbColor = Color.BLACK;
//    }
//});
        for (int fila = 0; fila < ROWS; fila++) {
            for (int col = 0; col < COLUMNS; col++) {
                Nodos[fila][col] = new Nodo(fila, col);
                Botones[fila][col] = new HexagonButton(fila, col);
                //horilla superior del mapa inicio
                if (fila == 0 || fila == 1 || fila == 2 || fila == 3 || fila == 36 || fila == 37 || fila == 38 || fila == 39) {
                    switch (fila) {

                        case 0:
                            Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(techo)));
                            Botones[fila][col].setUrlImagen(techo);
                            Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                            break;
                        case 3:
                            if (col < 35) {
                                Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(h1)));
                                Botones[fila][col].setUrlImagen(h1);
                                Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                                if (col == 3) {
                                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(izqSup)));
                                    Botones[fila][col].setUrlImagen(izqSup);
                                    Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                                }
                            }
                            break;
                        case 36:
                            Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(bOrilla)));
                            Botones[fila][col].setUrlImagen(bOrilla);
                            Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                            if (col == 3) {
                                Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(izqInf)));
                                Botones[fila][col].setUrlImagen(izqInf);
                                Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                            } else if (col == 36) {
                                Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(derInf)));
                                Botones[fila][col].setUrlImagen(derInf);
                                Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                            }
                            break;
                        default:
                            Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(mar2)));
                            Botones[fila][col].setUrlImagen(mar2);
                            Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                            break;
                    }
                } else {

                    //aqui se ponen los inicios aleatoreos del mapa inicial
//                  
                    Botones[fila][col].setColorHexagono(Color.BLUE);
                    String[] imagenesRandom = {b2, b3, b2, b3, b3, b3, b2, b3, b3, b3, b2, b3, b2, b3, b2, b3, b3, b3, b2, b3, b3, b3, b2, b3, b3, b1, b1, b1, b1, b3, b3, b1, b1, b1, b1, b3, b3, b1, b1, b1, b1, b3, b3, b2, b3, b2, b3, b3, b3, b2, b3, b3, b3, b2, b3, InicioImg, b1, b2, b3, b2, b3, b3, b3, b2, b3, b3, b3, b2, b3};
                    Random rand = new Random();
                    int random = rand.nextInt(imagenesRandom.length);
                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(imagenesRandom[random])));
                    Botones[fila][col].setUrlImagen(imagenesRandom[random]);

                }

                if (col == 0 || col == 1 || col == 2 || col == 3 || col == 36 || col == 37 || col == 38 || col == 39) {
                    switch (col) {

                        case 0:
                        case 1:
                        case 2:
                        case 37:
                        case 38:
                        case 39:
                            Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(mar2)));
                            Botones[fila][col].setUrlImagen(mar2);
                            Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                            break;
                        case 3:
                            if (fila >= 4 && fila < 36) {
                                if (fila % 2 == 0) {
                                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(lpari)));
                                    Botones[fila][col].setUrlImagen(lpari);
                                    Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                                } else {
                                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(latIzq)));
                                    Botones[fila][col].setUrlImagen(latIzq);
                                    Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                                }
                            }
                            break;
                        case 36:
                            if (fila >= 4 && fila < 36) {
                                if (fila % 2 != 0) {
                                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(lpd)));
                                    Botones[fila][col].setUrlImagen(lpd);
                                    Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                                } else {
                                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(latDer)));
                                    Botones[fila][col].setUrlImagen(latDer);
                                    Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                                }
                            }
                            break;
                    }
                }

                if (col == 35 && fila == 3) {
                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(ld)));
                    Botones[fila][col].setUrlImagen(ld);
                    Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                }
                if (col == 36 && fila == 3) {
                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(lpd)));
                    Botones[fila][col].setUrlImagen(lpd);
                    Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                }
                if ((fila == 0 && col == 37) || (fila == 0 && col == 38) || (fila == 0 && col == 2) || (fila == 0 && col == 1) || (fila == 0 && col == 0)) {
                    Botones[fila][col].setIcon(new javax.swing.ImageIcon(getClass().getResource(techo)));
                    Botones[fila][col].setUrlImagen(techo);
                    Botones[fila][col].setColorHexagono(new java.awt.Color(47, 142, 156));
                }

                Botones[fila][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        HexagonButton clickedButton = (HexagonButton) e.getSource();
                        System.out.println("Button clicked: [" + clickedButton.getRow() + "][" + clickedButton.getCol() + "]");
                    }
                });

            

                final int FilaFinal = fila;
                final int ColumnaFinal = col;

                Botones[fila][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            botonDerechoPrecionado = true;

                            Botones[FilaFinal][ColumnaFinal].setIcon((new javax.swing.ImageIcon(getClass().getResource("observatorio.png"))));

                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            botonDerechoPrecionado = false;
                            contorno = false;
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (botonDerechoPrecionado) {
                            Botones[FilaFinal][ColumnaFinal].setIcon((new javax.swing.ImageIcon(getClass().getResource("bosque2.png"))));
//                            Botones[FilaFinal][ColumnaFinal].setColorHexagono(Color.RED);
                            contorno = true;

                        }
                    }
                });

                Botones[fila][col].setBounds(offsetX, offsetY, 105, 95);
                x[fila][col] = offsetX;
                y[fila][col] = offsetY;
                offsetX += 65;//altura de cada fila del poligono
                Botones[fila][col].setSize(new Dimension(65, 71));
                panel.add(Botones[fila][col]);

            }
            if (fila % 2 == 0) {
                offsetX = -37;//este si es impar baje para que haga el efecto del panal de una abeja
            } else {
                offsetX = -70;
            }
            offsetY += 53;
        }
        actualizarBotones(); // Llamar al método para repintar los botones
//        agregarEventosScroll();

//aqui estoy agregando mis 0 a mis botones de inicio a mi tablero
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (Botones[i][j].getUrlImagen() == InicioImg) {
                    Botones[i][j].setTipoBoton(1);
                }
            }
        }

    }

    private void actualizarTransformacion() {
        transformacion.setToIdentity();
        transformacion.scale(escala, escala);
        repaint();
        actualizarBotones();
    }

    private void agregarEventosScroll() {
        panelExterno.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotacion = e.getWheelRotation();
                System.out.println(escala);
                if (rotacion > 0) {
                    escala -= 0.1;
                    if (escala < 0) {
                        escala = 0.1;
                        repaint();
                        System.out.println(escala);
                    }
                } else {
                    escala += 0.1;
                    if (escala > 1.0) {
                        escala = 1.0;
                        repaint();
                        System.out.println(escala);
                    }
                }
                actualizarTransformacion();
            }
        });
    }

    private void actualizarBotones() {
        for (int fila = 0; fila < ROWS; fila++) {
            for (int col = 0; col < COLUMNS; col++) {
                Botones[fila][col].actualizarTamaño();
                Botones[fila][col].setBounds((int) (x[fila][col] * escala), (int) (y[fila][col] * escala), (int) (65 * escala), (int) (71 * escala));

            }
        }
    }


    public static void main(String[] args) {

        try {
            //ctrl + espacio para ver los diseños de ventana de la libreria de jTatto
//            UIManager.setLookAndFeel(new FlatDarculaLaf());
            FlatXcodeDarkIJTheme.setup();

        } catch (Exception ex) {
            Logger.getLogger(MapaVisual.class.getName()).log(Level.SEVERE, null, ex);
        }
        MapaVisual Mapavisual = new MapaVisual();
        Mapavisual.setVisible(true);
    }

}
