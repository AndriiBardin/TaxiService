package taxi.model;

public class Driver {
    private Long id;
    private String name;
    private String licenceNumber;
    private String login;
    private String password;

    public Driver(String name, String licenceNumber, String login, String password) {
        this.name = name;
        this.licenceNumber = licenceNumber;
        this.login = login;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Driver{"
                + "id=" + id
                + ", name= " + name
                + ", licenceNumber=" + licenceNumber
                + ", login=" + login + '}';
    }
}
