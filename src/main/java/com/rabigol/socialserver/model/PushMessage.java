package com.rabigol.socialserver.model;

import java.util.HashMap;

/**
 * Created by Artur.Ziaev on 27.11.2016.
 */
public class PushMessage {
    public String to;
    public HashMap<String, String> data;

    public PushMessage(){

    }

    public PushMessage(String to, HashMap<String, String> data){
        this.to = to;
        this.data = data;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}
