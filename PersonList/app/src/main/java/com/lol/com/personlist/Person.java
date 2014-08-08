package com.lol.com.personlist;

/**
 * Created by Тарас on 07.08.2014.
 */
public class Person
{
    private int id;
    private String fname;
    private String lname;
    private String bday;
    public Person()
    {
        setId(0);
        setFname("unknown");
        setLname("unknown");
        setBday("unknown");
    }
    public Person(int id, String fname, String lname, String bday)
    {
        setId(id);
        setFname(fname);
        setLname(lname);
        setBday(bday);
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
