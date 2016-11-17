package ababilo.pwd.server.api.model;

/**
 * Created by ababilo on 08.11.16.
 */
public class Confirmation<T> {

    private T content;

    public Confirmation(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
