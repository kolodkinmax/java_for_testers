package ru.stqa.addressbook.model;

public record ContactData(
        String id, String firstName, String middleName, String lastName, String nickname, String photo, String title, String company,
        String address, String home, String mobile, String work, String fax, String email, String email2, String email3,
        String homepage, String bDay, String bMonth, String bYear) {

    public ContactData() {
        this("", "", "", "", "", "","", "", "", "",
                "", "", "", "", "", "", "", "-", "-","");
    }

    public ContactData(String firstName, String middleName, String lastName, String bDay, String bMonth, String bYear) {
        this("", firstName, middleName, lastName, "", "", "","", "", "",
                "", "", "", "", "", "", "", bDay, bMonth, bYear);
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstName, this.middleName, this.lastName, this.nickname, this.photo, this.title, this.company, this.address, this.home,
                this.mobile, this.work, this.fax, this.email, this.email2, this.email3, this.homepage, this.bDay, this.bMonth,this.bYear);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this.id, firstName, this.middleName, this.lastName, this.nickname, this.photo, this.title, this.company, this.address, this.home,
                this.mobile, this.work, this.fax, this.email, this.email2, this.email3, this.homepage, this.bDay, this.bMonth,this.bYear);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(this.id, this.firstName, this.middleName, lastName, this.nickname, this.photo, this.title, this.company, this.address, this.home,
                this.mobile, this.work, this.fax, this.email, this.email2, this.email3, this.homepage, this.bDay, this.bMonth,this.bYear);
    }

    public ContactData withDefaultValueExceptIdAndFirstNameAndLastName() {
        return new ContactData(this.id, this.firstName, "", this.lastName, "", "", "", "", "", "",
                "", "", "", "", "", "", "", "-", "-","");
    }

    public ContactData withPhoto(String photo) {
        return new ContactData(this.id, this.firstName, this.middleName, lastName, this.nickname, photo, this.title, this.company, this.address, this.home,
                this.mobile, this.work, this.fax, this.email, this.email2, this.email3, this.homepage, this.bDay, this.bMonth,this.bYear);
    }
}
