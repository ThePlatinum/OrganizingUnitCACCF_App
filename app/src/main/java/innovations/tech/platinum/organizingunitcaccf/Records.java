package innovations.tech.platinum.organizingunitcaccf;

/**
 * Created by PLATINUM
 * Date 4/20/2018
 * Time 10:19 PM
 * Package innovations.tech.platinum.organizingunitcaccf
 * Project OrganizingUnitCACCF
 */

public class Records {
    public String Rec_Title;
    public String Rec_Edited;
    public String Rec_Date;
    public String Rec_PcUsed;
    public String Rec_RecordedBy;

    public Records() {
    }

    public Records(String rec_Title, String rec_Edited, String rec_Date, String rec_PcUsed, String rec_RecordedBy) {
        Rec_Title = rec_Title;
        Rec_Edited = rec_Edited;
        Rec_Date = rec_Date;
        Rec_PcUsed = rec_PcUsed;
        Rec_RecordedBy = rec_RecordedBy;

    }

    public String getRec_PcUsed() {
        return Rec_PcUsed;
    }

    public void setRec_PcUsed(String rec_PcUsed) {
        Rec_PcUsed = rec_PcUsed;
    }

    public String getRec_RecordedBy() {
        return Rec_RecordedBy;
    }

    public void setRec_RecordedBy(String rec_RecordedBy) {
        Rec_RecordedBy = rec_RecordedBy;
    }

    public String getRec_Title() {
        return Rec_Title;
    }

    public void setRec_Title(String rec_Title) {
        Rec_Title = rec_Title;
    }

    public String getRec_Edited() {
        return Rec_Edited;
    }

    public void setRec_Edited(String rec_Edited) {
        Rec_Edited = rec_Edited;
    }

    public String getRec_Date() {
        return Rec_Date;
    }

    public void setRec_Date(String rec_Date) {
        Rec_Date = rec_Date;
    }
}
