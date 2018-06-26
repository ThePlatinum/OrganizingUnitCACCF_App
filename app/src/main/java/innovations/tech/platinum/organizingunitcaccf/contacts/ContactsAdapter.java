package innovations.tech.platinum.organizingunitcaccf.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import innovations.tech.platinum.organizingunitcaccf.R;

/**
 * Created by PLATINUM
 * Date 6/8/2018
 * Time 9:58 AM
 * Package innovations.tech.platinum.organizingunitcaccf
 * Project OrganizingUnitCACCF
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView contactName , contactNumber, contactEmail ;
        private ImageView contactImage;

        public ViewHolder(View itemView) {
            super(itemView);
           contactName = (TextView)itemView.findViewById(R.id.contact_name);
            contactNumber = (TextView)itemView.findViewById(R.id.contact_number);
            contactImage = (ImageView)itemView.findViewById(R.id.contact_image);
            contactEmail = (TextView)itemView.findViewById(R.id.contact_email);
        }
    }
    private List<Contacts> ContactsListSet;

    public ContactsAdapter(List<Contacts> ContactsListSet) {
        this.ContactsListSet = ContactsListSet;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_card_design,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {
        Contacts contacts = ContactsListSet.get(position);
        String C_name = contacts.getContact_Name().trim();
        String C_Number = contacts.getContact_Phone().trim();
        String C_Email = contacts.getContact_Email().trim();

        holder.contactName.setText(C_name);
        holder.contactNumber.setText(C_Number);
        holder.contactEmail.setText(C_Email);
    }
    @Override
    public int getItemCount() {
        return ContactsListSet.size();
    }

}