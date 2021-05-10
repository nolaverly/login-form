package com.task.loginform.model;

import javax.persistence.Transient;

public class Password {

    private String oldPassword;

    private String newPassword;

    @Transient
    private String confirmNewPassword;

    public String getnewPassword() {
        return newPassword;
    }

    public void setnewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getoldPassword() {
        return oldPassword;
    }

    public void setoldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getconfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setconfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
