public class Passenger {
    private final String id= "P" + System.currentTimeMillis() + "-" + (int)(Math.random()*1000);
    private String firstName;
    private String lastName;
    private long birthDate;
    private String passportNumber;
    private String nationality;
    private String contactEmail;
    private String contactPhone;

    public Passenger(String firstName, String lastName, long birthDate, String passportNumber, String nationality, String contactEmail, String contactPhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public boolean validateDetails(){
        if (firstName == null || firstName.isEmpty()){
            return false;
        }
        if (lastName == null || lastName.isEmpty()){
            return false;
        }
        if (passportNumber.length() < 5){
            return false;
        }
        if (!contactEmail.contains("@")){
            return false;
        }
        if (contactPhone == null || contactPhone.isEmpty()){
            return false;
        }

        return true;

    }

    public int getAge(){
        long ageMilis = System.currentTimeMillis()-birthDate;
        return (int)ageMilis/1000/60/60/24/365;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }
}
