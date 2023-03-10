package com.example.angel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentNotiAdapter extends BaseAdapter {

    Context context;
    ArrayList<StudentNotification> arrayList;

    public StudentNotiAdapter(Context context, ArrayList<StudentNotification> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.show_info_layout,null);
        TextView tvid=convertView.findViewById(R.id.tvid1);
        TextView tvname=convertView.findViewById(R.id.namev);
       // TextView tvage=convertView.findViewById(R.id.agev);
        TextView tvschool=convertView.findViewById(R.id.school);
        //TextView tvaress=convertView.findViewById(R.id.addressv);
        TextView email=convertView.findViewById(R.id.email);
        TextView phone=convertView.findViewById(R.id.phone);

        StudentNotification studentNotification=arrayList.get(position);
        int id=studentNotification.getId();
        String name=studentNotification.getName();
        //String age=studentNotification.getAge();
        String school=studentNotification.getSchool();
        //String arress=studentNotification.getAddress();
        String emailv=studentNotification.getEmail();
        String phonev=studentNotification.getPhone();

        tvid.setText(String.valueOf(id));
        tvname.setText(name);
        //tvage.setText(age);
        tvschool.setText(school);
        //tvaress.setText(arress);
        email.setText(emailv);
        phone.setText(phonev);




        return convertView;
    }
}
