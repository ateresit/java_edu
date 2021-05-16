package com.itia.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

/**
 * Swing client - File Storage
 * Client command: upload filename | download filename
 */

public class Client extends JFrame {
    private static final int SERVER_PORT = 9977;
    private static final String SERVER_IP = "localhost";
    private static final String CLIENT_FOLDER = "test_folder/client/";
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public Client() throws IOException {
        // init connection
        socket = new Socket(SERVER_IP, SERVER_PORT);
        System.out.println("Connect to server");

        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        // create client form
        setSize(300,300);
        JPanel panel = new JPanel(new GridLayout(2,1));

        JButton btnSend = new JButton("send");
        JTextField textField = new JTextField();

        btnSend.addActionListener(a -> {
            String[] cmd = textField.getText().split(" ");
            if ("upload".equals(cmd[0])){
                sendFile(cmd[1]);
            } else if ("download".equals(cmd[0])) {
                getFile(cmd[1]);
            }
        });

        // отключаемся от сервера, если закрыли окно
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                sendMessage("exit");
            }
        });

        panel.add(textField);
        panel.add(btnSend);

        add(panel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void getFile(String fileName) {
        // TODO: 13.05.2021  downloading
    }

    private void sendFile(String fileName) {
        try {
            File file = new File(CLIENT_FOLDER + fileName);
            if (!file.exists()){
                throw new FileNotFoundException();
            }
            long fileLenght = file.length();
            FileInputStream fis = new FileInputStream(file);

            out.writeUTF("upload");
            out.writeUTF(fileName);
            out.writeLong(fileLenght);

            // делаем буфер
            int read = 0;
            byte[] buffer = new byte[8 * 1024];

            while ((read = fis.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush(); // очистка

            String status = in.readUTF();
            System.out.println("Sending status: " + status);
        } catch (FileNotFoundException e) {
            System.err.println("File not found - /client/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param message String
     */

    private void sendMessage(String message) {
        try {
            out.writeUTF(message);

            // если закрыли соединение
            String command = in.readUTF();
            if ("exit_done".equals(command)){
                System.out.println("Disconnected from server");
            }

        } catch (EOFException eofException) {
            System.err.println("Reading command error from " + socket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
