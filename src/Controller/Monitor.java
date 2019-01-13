/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Rexnet
 */
import com.jfoenix.controls.JFXTextArea;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Monitor  implements Runnable{

    Thread th = new Thread();

    /**
     * @param args
     */
    Runnable ping(final String ip, final JFXTextArea area) {
        return () -> {
            String resposta = "";
            String comando = "ping -c 8 " + ip ;

            try {
                Scanner S = new Scanner(Runtime.getRuntime().exec(comando)
                        .getInputStream());
                while (S.hasNextLine()) {
                    final String newText = S.nextLine();
                    EventQueue.invokeLater(() -> {
                        area.setText(area.getText()
                                + System.getProperty("line.separator")
                                + newText);
                    });

                }
            } catch (IOException ex) {
            }
        };

    }
    ExecutorService executor = Executors.newFixedThreadPool(10);

    Runnable tracert(final String ip, final JFXTextArea area) {
        return () -> {
            String resposta = "";
            String comando = "traceroute " + ip;

            try {
                Scanner S = new Scanner(Runtime.getRuntime().exec(comando)
                        .getInputStream());
                while (S.hasNextLine()) {
                    final String newText = S.nextLine();
                    area.setText(area.getText()
                            + System.getProperty("line.separator")
                            + newText);

                }
            } catch (IOException ex) {
            }
        };
    }

    Executor exe = Executors.newFixedThreadPool(1);

    boolean stop(boolean b) {
        Thread.currentThread().interrupt();
        return false;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
