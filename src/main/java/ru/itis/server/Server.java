package ru.itis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements ConnectionListener {
    private static final int PORT = 4321;
    private static int count = 0;
    private static final ArrayList<Connection> connections = new ArrayList<>();
    Connection conPlayerOne;
    Connection conPlayerTwo;

    boolean connTwo = false;


    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try {
                    new Connection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("Connection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onConnectionReady(Connection connection) {
        connections.add(connection);
        System.out.println("Client connected " + (count + 1) + " : " + connection);
        count++;
        if (count == 1) {
            conPlayerOne = connection;
        } else if (count == 2) {
            conPlayerTwo = connection;
            connTwo = true;
        }
    }

    @Override
    public void onReceiveString(Connection connection, String value) {
        sentToAllConnectionsButNotMe(value, connection);
        if (connTwo) {
            if (conPlayerTwo != null) {
                conPlayerTwo.sendString("Client 2");
                connTwo = false;
            }
        }
    }

    @Override
    public void onDisconnect(Connection connection) {
        connections.remove(connection);
        sendToAllConnections("Client disconnected: " + connection);
        count--;
    }

    @Override
    public void onException(Connection connection, Exception e) {
        System.out.println("Connection exception: " + e);
    }


    private void sendToAllConnections(String value) {
        for (Connection connect : connections) {
            connect.sendString(value);
        }

    }

    private void sentToAllConnectionsButNotMe(String value, Connection connectionId) {
        for (Connection con : connections) {
            if (con != connectionId) {
                con.sendString(value);
            }
        }
    }

}
