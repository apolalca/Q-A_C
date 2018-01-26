package es.iesnervion.qa.model;

/**
 * Created by adrianpolalcala on 5/25/17.
 */

public class GoogleUser {
    private String name;
    private String fecha;
    private int id;

    public GoogleUser() {

    }

    public GoogleUser(String nameAccound) {
        this.name = nameAccound;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFecha() {
        return fecha;
    }

    public int getId() {
        return id;
    }
}
