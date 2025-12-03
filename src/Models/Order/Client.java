package Models.Order;

import java.util.Objects;

/**
 * Represents a client (customer) of the online shop.
 * Contains personal information and a unique identifier.
 */
public class Client {
    private String name;
    private String surname;
    private Long id;

    /**
     * Constructs a new Client.
     *
     * @param name    The first name of the client.
     * @param surname The last name of the client.
     * @param id      The unique identifier for the client.
     */
    public Client(String name, String surname, Long id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(name, client.name) && Objects.equals(surname, client.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, id);
    }

    @Override
    public String toString() {
        return id + " " + name + " " + surname;
    }
}