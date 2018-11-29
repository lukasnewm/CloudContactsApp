package com.example.lukas.inclass09;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactsAdaptor extends ArrayAdapter<Contact> {
    Context context;
    ViewHolder viewHolder = null;
    int resource = 0;

    public ContactsAdaptor(Context context, int resource, List<Contact> conList){
        super(context, resource, conList);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact con = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.textName = (TextView)convertView.findViewById(R.id.textViewContactName);
            viewHolder.textEmail = (TextView)convertView.findViewById(R.id.textViewContactEmail);
            viewHolder.textPhone = (TextView)convertView.findViewById(R.id.textViewContactPhone);
            viewHolder.textDept = (TextView)convertView.findViewById(R.id.textViewContactDept);
            viewHolder.imageAvatar = (ImageView)convertView.findViewById(R.id.imageViewContactAvatar);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textName.setText(con.name);
        viewHolder.textEmail.setText(con.email);
        viewHolder.textPhone.setText(con.phone);
        viewHolder.textDept.setText(con.dept);
        viewHolder.imageAvatar.setBackgroundResource(con.avatarID);


        return convertView;
    }

    private class ViewHolder {
        ImageView imageAvatar;
        TextView textName;
        TextView textEmail;
        TextView textPhone;
        TextView textDept;
    }
}
