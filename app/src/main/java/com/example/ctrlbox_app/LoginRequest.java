package com.example.ctrlbox_app;

public class LoginRequest {
    private String Username;
    private String WorkstationLogin;

    public LoginRequest(String Username, String WorkstationLogin){
        this.Username = Username;
        this.WorkstationLogin = WorkstationLogin;

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getWorkstationLogin() {
        return WorkstationLogin;
    }

    public void setWorkstationLogin(String workstationLogin) {
        WorkstationLogin = workstationLogin;
    }
}

