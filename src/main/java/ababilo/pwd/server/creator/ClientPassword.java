package ababilo.pwd.server.creator;

/**
 * Created by ababilo on 18.11.16.
 */
public class ClientPassword {

    private short id;
    private String title;
    private byte[] part;

    public ClientPassword(short id, String title, byte[] part) {
        this.id = id;
        this.title = title;
        this.part = part;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getPart() {
        return part;
    }

    public void setPart(byte[] part) {
        this.part = part;
    }
}
