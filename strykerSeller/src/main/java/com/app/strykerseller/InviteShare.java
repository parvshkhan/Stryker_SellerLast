package com.app.strykerseller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.adapter.MyArrayAdapter;
import com.app.listener.ICallBack;
import com.app.model.ContactModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InviteShare extends AppCompatActivity implements SearchView.OnQueryTextListener,ICallBack {

    List<ContactModel> contactModelsList= new ArrayList<>();

    TextView nocontacttv;
    ListView listView;
    LinearLayout buttonInvite;
    String storeName;
    String appUrl = "https://play.google.com/store/apps/details?id=com.app.stryker&referrer=";
    String storeId;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    ArrayList<ContactModel>tempArrayList;
    private Toolbar toolbar;
    public int count1=0;
    //private FriendListAdapter friendListAdapter;
    //private ArrayList<User> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_share);
      toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        if(getIntent().hasExtra("storename") && getIntent().hasExtra("id"))
        {
            storeName = getIntent().getStringExtra("storename");
            storeId= getIntent().getStringExtra("id");
        }
        listView = (ListView)findViewById(R.id.listcontact);
        nocontacttv = (TextView)findViewById(R.id.numbercontact) ;
        new ContactTask().execute();
        buttonInvite = (LinearLayout) findViewById(R.id.invite);



        buttonInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MyArrayAdapter.count>0)
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < contactModelsList.size(); i++) {
                        if (contactModelsList.get(i).isChecked) {
                            String num = contactModelsList.get(i).getMobileNumber().replace("+91","");
                            if(num.charAt(0)=='0')
                            {
                                num = num.substring(1,num.length());
                            }
                            stringBuilder.append(num  + ",");
                        }
                    }
                    sendInvitiation(stringBuilder);
                }
                else
                    Toast.makeText(InviteShare.this, "Must Select", Toast.LENGTH_SHORT).show();


            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(position>0 && position <= contactModelsList.size()) {
                    //handelListItemClick((User)MyArrayAdapter.get(position - 1));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      /*  MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);*/

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setOnQueryTextListener(this);

        return true;
    }


    private void sendInvitiation(final StringBuilder strings) {

        final ProgressDialog progressDialog = new ProgressDialog(InviteShare.this);
        progressDialog.setMessage("Sending Invitation");
        progressDialog.setCancelable(false);
        progressDialog.show();


        progressDialog.setMessage("Sending Invitation");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://marketapp.online/web/service/sellerInvite",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("responseInvite", response);
                        Toast.makeText(InviteShare.this, "success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InviteShare.this, SellerHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InviteShare.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String val = new String(strings);
                val = val.substring(0, val.length() - 1);
                params.put("store", storeName);
                params.put("message",getMessage());
                params.put("mobileno", val);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private String getMessage() {
        String appUrl = "https://play.google.com/store/apps/details?id=com.app.stryker&referrer="+storeId;
        return appUrl;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String s) {


        int textlength = s.length();

        tempArrayList = new ArrayList<>();
        for (ContactModel c : contactModelsList) {
            if (textlength <= c.getName().length()) {
                if (c.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                    tempArrayList.add(c);
                }
            }
        }
        Log.i("temp size", tempArrayList.size() + "");
        MyArrayAdapter myArrayAdapter = new MyArrayAdapter(getApplicationContext(),R.layout.row_conatct,tempArrayList);
        myArrayAdapter.iCallBack = new ICallBack() {

            @Override
            public void onCount(int count) {
                count1 = count;
            }
        };

        listView.setAdapter(myArrayAdapter);
       // MyArrayAdapter.getFilter().filter(s);


        return false;
    }

    @Override
    public void onCount(int count) {
        Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
    }


    private class ContactTask extends AsyncTask<Void, Void, Long> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(InviteShare.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Long doInBackground(Void... params) {
            contactModelsList = getContacts(InviteShare.this);
            long res = 9;
            return res;
        }

        @Override
        protected void onPostExecute(Long aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            nocontacttv.setText(contactModelsList.size() + "");


           MyArrayAdapter myArrayAdapter = new MyArrayAdapter(getApplicationContext(),R.layout.row_conatct,contactModelsList);
            listView.setAdapter(myArrayAdapter);

        }
    }


    public List<ContactModel> getContacts(Context ctx) {
        List<ContactModel> list = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(InviteShare.this.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {
                        ContactModel info = new ContactModel();
                        info.setId(id);
                        info.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                        info.setMobileNumber(cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        info.setPhoto(photo);
                        info.setPhotoURI(pURI);
                        list.add(info);
                    }

                    cursorInfo.close();
                }
            }
        }
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyArrayAdapter.count=0;
    }
}
