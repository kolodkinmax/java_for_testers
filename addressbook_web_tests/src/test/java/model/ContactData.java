package model;

public record ContactData(
        String firstName, String middleName, String lastName, String nickname, String title, String company,
        String address, String home, String mobile, String work, String fax, String email, String email2, String email3,
        String homepage, String bDay, String bMonth, String bYear) {

    public ContactData() {
        this("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "-", "-","");
    }

    public ContactData(String firstName, String middleName, String lastName, String bDay, String bMonth, String bYear) {
        this(firstName, middleName, lastName, "", "", "", "", "",
                "", "", "", "", "", "", "", bDay, bMonth, bYear);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(firstName, "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "-", "-","");
    }
}
