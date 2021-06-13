package com.itia.cs.server;

import com.itia.cs.server.handlers.SQLHandler;

public class DBAuthService implements AuthService{
    @Override
    public String getDesktopByLoginAndPassword(String login, String password) {
        return SQLHandler.getUserDesktop(login, password);
    }

    @Override
    public boolean registration(String login, String password) {
        return false;
    }
}
