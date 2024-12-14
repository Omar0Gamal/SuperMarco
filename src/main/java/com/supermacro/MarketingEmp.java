package com.supermacro;


public class MarketingEmp extends Employee{

    public MarketingEmp(){
        employeeType =empType.MARKETING_EMPLOYEE;
    }
    public MarketingEmp(String username, String password, int ID)
    {
        this.username=username;
        this.password=password;
        this.ID=ID;
        employeeType =empType.MARKETING_EMPLOYEE;
    }
    public void makeOffer(int productId, double discount) {
        for (Product p:InventoryEmp.inventory)
            if(p.getProductId()== productId)
            {
                p.setDiscount(discount);
            }
    }

    public Report makeReport(String Title, String Desc) {
        Report report = new Report(Title,Desc,this);
        return report;
    }


    @Override
    public void setUsername(String username) {
        this.username = "MAR_" + username;
    }
}
