package musica;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReproMusic {
    private Player player;
    public boolean pausa = false;

    public void reproducir(String rutaArchivo) throws IOException, JavaLayerException {
        URL url = getClass().getResource(rutaArchivo);
        InputStream inputStream = url.openStream();
        player = new Player(inputStream);
        new Thread() {
            public void run() {
                try {
                    while (true) {
                        if (pausa) {
                            if (!player.play(1)) {
                                break;
                            }
                        }
                    }
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void togglePausa() {
        pausa = !pausa;
    }
     public void Play() {
        pausa = true;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Reproductor");
        JPanel panel = new JPanel();
        JButton botonPausa = new JButton("Pausa");
        JButton botonPlay = new JButton("play");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        ReproMusic reproductor = new ReproMusic();
   

        botonPausa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reproductor.pausa=false;
            }
        });
           botonPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 reproductor.pausa=true;
                try {
                    reproductor.reproducir("music/R1.mp3");
                } catch (IOException ex) {
                    Logger.getLogger(ReproMusic.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JavaLayerException ex) {
                    Logger.getLogger(ReproMusic.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
        });

        panel.add(botonPausa);
         panel.add(botonPlay);
        frame.add(panel);
        frame.setVisible(true);
    }
}