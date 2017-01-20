package com.apexmediatechnologies.apexmedia;

/**
 * Created by Zeta Apponomics 3 on 24-11-2014.
 */
public class ChatMessage {
    public boolean left;
    public String message;
    public String id;
    public String fromUser;
    public String toUser;

    public ChatMessage(boolean left, String message,String id,String fromUser,String toUser)
    {
        super();
        this.left = left;
        this.message = message;
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
