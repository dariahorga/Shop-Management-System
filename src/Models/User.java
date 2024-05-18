package Models;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected int userId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String address;
    protected long phoneNumber;
    private static int userIndex;

    public User(){

    }
    public User(String firstName, String lastName, String email,String address, long phoneNumber){
        userIndex=userIndex+1;
        this.userId=userIndex;
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address)
    {
        this.address=address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract void userActions();

}