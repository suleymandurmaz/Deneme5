package Utils;

public enum RegisterInputs {
    Password("password"),
    First_name("first Name"),
    Last_name("last Name"),
    Company("company"),
    Address1("address1"),
    Address2("address2"),
    Country("country"),
    State("state"),
    City("city"),
    Zipcode("zip Code"),
    Mobile_number("mobile_number");

    private String str;

    private RegisterInputs(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
