package com.zrdb.app.event;

public class MsgEvent {
    public String msg;
    public int code;

    public MsgEvent(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MsgEvent(int code) {
        this.code = code;
    }
}
