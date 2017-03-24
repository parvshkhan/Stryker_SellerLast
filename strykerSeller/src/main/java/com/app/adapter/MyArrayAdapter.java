package com.app.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.app.listener.ICallBack;
import com.app.model.ContactModel;
import com.app.strykerseller.R;

import java.util.ArrayList;
import java.util.List;


public class MyArrayAdapter extends ArrayAdapter {
    Context context;
    int res;
    List<ContactModel> contactModelsList;
    LayoutInflater layoutInflater;
    public static int count = 0;
    private TextView textViewCount;
    public ICallBack iCallBack;



    public MyArrayAdapter(Context context, int res, List<ContactModel> contactModelsList) {
        super(context, res, contactModelsList);

        this.context = context;
        this.res = res;
        this.contactModelsList = contactModelsList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        convertView = layoutInflater.inflate(res, null);


        textViewCount = (TextView) parent.findViewById(R.id.count);


        TextView textView = (TextView) convertView.findViewById(R.id.tvname);
        TextView txtInitial = (TextView) convertView.findViewById(R.id.txt_initial);

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chkboccontact);

        textView.setText(contactModelsList.get(position).getName());

        String value = null;

        try {
            value = getInitial(contactModelsList.get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtInitial.setText(value);
/*

        if(contactModelsList.get(position).getPhonNum().length()>=10)
        {
            checkBox.setVisibility(View.INVISIBLE);
        }
*/

/*

        if (contactModelsList.get(position).isExist == 0) {
            checkBox.setVisibility(View.VISIBLE);

            ((TextView) convertView.findViewById(R.id.mrktapp)).setVisibility(View.INVISIBLE);
        } else if (contactModelsList.get(position).isExist == 2) {
            checkBox.setVisibility(View.INVISIBLE);
            ((TextView) convertView.findViewById(R.id.mrktapp)).setVisibility(View.INVISIBLE);
        } else {
            checkBox.setVisibility(View.INVISIBLE);

            ((TextView) convertView.findViewById(R.id.mrktapp)).setVisibility(View.VISIBLE);

        }
*/


        checkBox.setTag(position);
        textView.setTag(position);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = Integer.parseInt(buttonView.getTag().toString());
                if (isChecked) {
                    count++;
                    contactModelsList.get(id).isChecked = true;
    //                iCallBack.onCount(count);
                } else {
                    count--;
                    contactModelsList.get(id).isChecked = false;
      //              iCallBack.onCount(count);
                }

            }
        });

        return convertView;
    }




    private String getInitial(String contactName) throws Exception {
        String value = "";
        String c[] = contactName.split(" ");
        if (c.length > 0)
            for (int i = 0; i < c.length; i++) {
                value = value + c[i].charAt(0) + "";
            }

        return value;
    }


}

