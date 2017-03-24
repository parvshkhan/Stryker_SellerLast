package com.app.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import com.app.model.StoreHomeListModel;
import com.app.strykerseller.R;
import com.app.utills.AppUtil;
import com.app.utills.GPSTracker;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.sample.chat.core.ChatService;
import com.quickblox.sample.chat.pushnotifications.Consts;
import com.quickblox.sample.chat.pushnotifications.PlayServicesHelper;
import com.quickblox.sample.chat.ui.activities.BaseActivity;
import com.quickblox.sample.chat.ui.activities.ChatActivity;
import com.quickblox.sample.chat.ui.activities.DialogsActivity;
import com.quickblox.sample.chat.ui.adapters.DialogsAdapter;
import com.quickblox.users.model.QBUser;



public class ChatFragment extends android.support.v4.app.Fragment {

    Context context;
    private static final String TAG = DialogsActivity.class.getSimpleName();

    ListView listUsers;
    ArrayList<StoreHomeListModel> arlistStoreHome = new ArrayList<StoreHomeListModel>();
    private Double mLat = 0.0, mLong = 0.0;
    ProgressDialog dialog;

    private PlayServicesHelper playServicesHelper;
    private ListView dialogsListView;
    private ProgressBar progressBar;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootview = inflater.inflate(R.layout.fragment_chat_store,
                container, false);

        return rootview;
    }

    
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        GPSTracker mGPS = new GPSTracker(context);
        if (mGPS.canGetLocation) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();
        }
        init();

    }
    public void init(){
		playServicesHelper = new PlayServicesHelper(getActivity());

        dialogsListView = (ListView) getView().findViewById(R.id.roomsList);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);


        // Register to receive push notifications events
        //
        
         /**init chat login*/
        initChat();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mPushReceiver,
                new IntentFilter(Consts.NEW_PUSH_EVENT));

        // Get dialogs if the session is active
        //
        if(((BaseActivity) getActivity()).isSessionActive()){
        	if(getActivity() != null)
            getDialogs();
      	 }
	}
	
	 private void getDialogs(){
	        progressBar.setVisibility(View.VISIBLE);

	        // Get dialogs
	        //
	        ChatService.getInstance().getDialogs(new QBEntityCallbackImpl() {
	            
	            public void onSuccess(Object object, Bundle bundle) {
	                progressBar.setVisibility(View.GONE);

	                final ArrayList<QBDialog> dialogs = (ArrayList<QBDialog>)object;

	                // build list view
	                //
	                if(dialogs != null)
	                buildListView(dialogs);
	            }

	            
	            public void onError(List errors) {
	                progressBar.setVisibility(View.GONE);

	              //  AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
	             //   dialog.setMessage("get dialogs errors1: " + errors).create().show();
	            }
	        });
	    }


	    void buildListView(List<QBDialog> dialogs){
	        final DialogsAdapter adapter = new DialogsAdapter(dialogs, getActivity());
	        dialogsListView.setAdapter(adapter);

	        // choose dialog
	        //
	        dialogsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                QBDialog selectedDialog = (QBDialog) adapter.getItem(position);

	                Bundle bundle = new Bundle();
	                bundle.putSerializable(ChatActivity.EXTRA_DIALOG, selectedDialog);

	                // Open chat activity
	                //
	                ChatActivity.start(getActivity(), bundle);

	                //finish();
	            }
	        });
	    }

	/*    
	    protected void onResume() {
	        super.onResume();
	        playServicesHelper.checkPlayServices();
	    }

	    
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.rooms, menu);
	        return true;
	    }

	    
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int id = item.getItemId();
	        if (id == R.id.action_add) {

	            // go to New Dialog activity
	            //
	            Intent intent = new Intent(getActivity(), NewDialogActivity.class);
	            startActivity(intent);
	            finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
*/
	    // Our handler for received Intents.
	    //
	    private BroadcastReceiver mPushReceiver = new BroadcastReceiver() {
	        
	        public void onReceive(Context context, Intent intent) {

	            // Get extra data included in the Intent
	            String message = intent.getStringExtra(Consts.EXTRA_MESSAGE);

	            Log.i(TAG, "Receiving event " + Consts.NEW_PUSH_EVENT + " with data: " + message);
	        }
	    };


	    //
	    // ApplicationSessionStateCallback
	    //

/*	    
	    public void onStartSessionRecreation() {

	    }

	    
	    public void onFinishSessionRecreation(final boolean success) {
	        runOnUiThread(new Runnable() {
	            
	            public void run() {
	                if (success) {
	                    getDialogs();
	                }
	            }
	        });
	    }*/
	
	public void initChat(){
		try {
			ChatService.initIfNeed(getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		final QBUser user = new QBUser();
	        user.setLogin(AppUtil.getUserId(getActivity()));
	        user.setPassword("12345678");

	       
	        
	        ChatService.getInstance().login(user, new QBEntityCallbackImpl() {

	            
	            public void onSuccess() {
	                // Go to Dialogs screen
	                //
	            	Log.e("response", " in side success--->");
	            	  
	              	
	      			

	                // Amrit done
	                
	            }

	            
	            public void onError(List errors) {
	            	Log.e("response", " in side failure--->"+errors.get(0).toString());
	            	
	            	if(errors.get(0).toString()=="You have already logged in chat")
	            	{
	            		Log.e("sdfdfgdfgdf","dfgdfgdfgdfgdgf");
	            		getDialogs();
	            		
	            	}
	            	
	            	
	            //    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
	             //   dialog.setMessage("chat login errors: " + errors).create().show();
	            }
	        });

	}

}


