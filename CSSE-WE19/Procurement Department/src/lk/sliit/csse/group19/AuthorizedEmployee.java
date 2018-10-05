/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Muhammed Suhail
 */
public class AuthorizedEmployee {

    private String employeeID;
    private String empType;
    private String empName;
    private String empPassword;
    private int empPhone;

    public AuthorizedEmployee(String employeeID, String empType, String empName, String empPassword, int empPhone) {
        this.employeeID = employeeID;
        this.empType = empType;
        this.empName = empName;
        this.empPassword = empPassword;
        this.empPhone = empPhone;
    }

    public AuthorizedEmployee(String employeeID, String empType, String empName, int empPhone) {
        this.employeeID = employeeID;
        this.empType = empType;
        this.empName = empName;
        this.empPhone = empPhone;
    }
    
    

    public AuthorizedEmployee() {
    }

    public AuthorizedEmployee(String employeeID, String empPassword) {
        this.employeeID = employeeID;
        this.empPassword = empPassword;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public int getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(int empPhone) {
        this.empPhone = empPhone;
    }

    public AuthorizedEmployee getUser() {
        JSONObject data;
        try {
            boolean varified = false;
//            data = new API("http://localhost:9000/authorizedEmployee/" + this.employeeID).getSingleValue();
//            String name = data.getString("id");
//            String password = data.getString("password");

//            System.out.println(this.employeeID + "  " + this.empPassword + "  " + name + "  " + password);
            AuthorizedEmployee obj;
//            if(this.empName.contentEquals(name) && this.empPassword.contentEquals(password)){
            if (this.employeeID == "mg" && this.empPassword == "123") {
//                obj = new AuthorizedEmployee(data.getString("id"), data.getString("type"), data.getString("name"), 
//                        Integer.parseInt(data.getString("mobileNumber")));
                obj = new AuthorizedEmployee();
                System.out.println(obj.getEmpName());
                return obj;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
