package com.itia.cs.server;

public interface AuthService {
    String getDesktopByLoginAndPassword (String login, String password);
    boolean registration (String login, String password);
}
