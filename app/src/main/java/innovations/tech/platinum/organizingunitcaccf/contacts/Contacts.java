package innovations.tech.platinum.organizingunitcaccf.contacts;

/**
 * Created by PLATINUM
 * Date 6/8/2018
 * Time 9:53 AM
 * Package innovations.tech.platinum.organizingunitcaccf
 * Project OrganizingUnitCACCF
 */

public class Contacts {
    public String getContact_Name() {
        return Contact_Name;
    }

    public Contacts(String contact_ImageUri, String contact_Name, String contact_Phone, String contact_Email) {
        Contact_ImageUri = contact_ImageUri;
        Contact_Name = contact_Name;
        Contact_Phone = contact_Phone;
        Contact_Email = contact_Email;
    }

    public Contacts() {
    }

    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    public String getContact_Phone() {
        return Contact_Phone;
    }

    public void setContact_Phone(String contact_Phone) {
        Contact_Phone = contact_Phone;
    }

    public String getContact_Email() {
        return Contact_Email;
    }

    public void setContact_Email(String contact_Email) {
        Contact_Email = contact_Email;
    }

    public String getContcat_ImageUri() {
        return Contact_ImageUri;
    }

    public void setContcat_ImageUri(String contcat_ImageUri) {
        Contact_ImageUri = contcat_ImageUri;
    }

    public String Contact_ImageUri , Contact_Name , Contact_Phone , Contact_Email;
}
