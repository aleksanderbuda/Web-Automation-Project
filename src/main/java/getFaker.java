import com.github.javafaker.Faker;

public class getFaker {
    private static final Faker faker = new Faker();
    static String storeThePassword = "";
    static String storeTheUsername = "";

    public String generateFirstName() {
            return faker.name().firstName();
    }
    public String generateLastName() {
        return faker.name().lastName();
    }
    public String generateAddress() {
        return faker.address().streetAddress();
    }
    public String generateCityField() {
        return faker.address().city();
    }
    public String generateStateField() {
        return faker.address().state();
    }
    public String generateZipCodeField() {
        return faker.address().zipCode();
    }
    public String generatePhoneField() {
        return faker.phoneNumber().cellPhone();
    }
    public String generateSsnField() {
        return faker.idNumber().ssnValid();
    }
    public String generateUsernameField() {
        return this.storeTheUsername = faker.name().username() + " ";
    }
    public String generatePasswordField() {
        return this.storeThePassword = faker.internet().password();
    }

    static void getThePasswordAndUsername() {
        System.out.println(storeTheUsername);
        System.out.println(storeThePassword);
    }

}
