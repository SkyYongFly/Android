package com.example.daomain;

/**
 * Created by yzas on 2015/10/6.
 */
public class BlackNumInfo {
    private String blackNumber ;
    private String mode ;

    public String getBlackNumber() {
        return blackNumber;
    }

    public void setBlackNumber(String blackNumber) {
        this.blackNumber = blackNumber;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public BlackNumInfo(String blackNumber, String mode) {
        this.blackNumber = blackNumber;
        this.mode = mode;
    }

    public BlackNumInfo() {
    }

    @Override
    public String toString() {
        return "BlackNumInfo{" +
                "blackNumber='" + blackNumber + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
