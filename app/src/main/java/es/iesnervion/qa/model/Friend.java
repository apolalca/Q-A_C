package es.iesnervion.qa.model;

/**
 * Created by adrianpolalcala on 5/27/17.
 */

public class Friend {
    private long id;
    private long amigo;
    private long usuario;

    public Friend(long amigo, long usuario) {
        this.amigo = amigo;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmigo() {
        return amigo;
    }

    public void setAmigo(long amigo) {
        this.amigo = amigo;
    }

    public long getUsuario() {
        return usuario;
    }

    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }
}
