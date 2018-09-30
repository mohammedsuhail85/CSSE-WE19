/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

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
    
    
}
