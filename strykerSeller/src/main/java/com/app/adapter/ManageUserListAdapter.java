package com.app.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.jsoncall.JsonCall;
import com.app.listener.UserCountListener;
import com.app.model.ChatUserModel;
import com.app.model.CircleTransform;
import com.app.model.OrderHistoryModel;
import com.app.fragment.ManageUsersFragment;
//import com.app.fragment.ManageUsersFragment.ManageChatUserTask;
import com.app.strykerseller.R;
import com.app.strykerseller.SellerChatActivity;
import com.app.strykerseller.SellerOrderHistoryActivity;
import com.app.utills.AppUtil;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.sample.chat.core.ChatService;
import com.quickblox.sample.chat.ui.activities.ChatActivity;
import com.quickblox.sample.chat.ui.activities.DialogsActivity;
import com.quickblox.sample.chat.ui.adapters.DialogsAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class ManageUserListAdapter extends ArrayAdapter<ChatUserModel> {

	int layoutId;
	Context con;
	ProgressDialog dialog;
	List<ChatUserModel> listUsers;
	//ManageUsersFragment frag = new ManageUsersFragment();
	String storeId = "";
	String buyerId = "";
	String userName = "";
	String buyerid="";
	//List<OrderHistoryModel> listOrderHistory;
	//UserCountListener listener;
	ArrayAdapter<String> dataAdapter;
	com.app.fragment.ManageUsersFragment frag;
    private ProgressBar progressBar;

	public ManageUserListAdapter(Context context, int resource, List<ChatUserModel> objects,
								 String storeID,com.app.fragment.ManageUsersFragment frag) {
		super(context, resource, objects);

		con = context;
		layoutId = resource;
		listUsers = objects;
		this.storeId = storeID;
		this.buyerId = buyerId;
		this.frag = frag;
		
		final List<String> list = new ArrayList<String>();
		list.add("");
		list.add("Start Chat");
		list.add("Order History");
		list.add("Remove User");
		list.add("Block User");
		dataAdapter = new ArrayAdapter<String>(con,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        progressBar = (ProgressBar) frag.getActivity().findViewById(R.id.progressBar);

	}

	
	public int getCount() {
		return super.getCount();
	}

	class ViewHolder {
		TextView txtUserName, txtInit;
		ImageView imgMenu;
		//ImageView imgUser;
		Spinner spinnerMenu;
		int posi;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View row = convertView;
		final ChatUserModel hgetter = getItem(position);
		userName = hgetter.getUserName();



		if (row == null) {

			row = inflater.inflate(layoutId, null);
			ViewHolder holder = new ViewHolder();

			holder.txtUserName = (TextView) row.findViewById(R.id.txt_user_name_m);
			//holder.imgUser = (ImageView) row.findViewById(R.id.img_profile);
			holder.imgMenu = (ImageView) row.findViewById(R.id.img_option);
			holder.txtInit = (TextView) row.findViewById(R.id.txt_init);
			
			holder.spinnerMenu = (Spinner)row.findViewById(R.id.spinner);

			row.setTag(holder);
		}

		final ViewHolder holder = (ViewHolder) row.getTag();
		holder.posi = position;

		holder.txtUserName.setText(hgetter.getUserName());
		
		/*if(!(hgetter.getUserImage().equalsIgnoreCase("") || 
				hgetter.getUserImage().equals(null))){
			try {				
				holder.txtInit.setVisibility(View.GONE);
				holder.imgUser.setVisibility(View.VISIBLE);
				
				Picasso.with(con).load(con.getString(R.string.image_base_url) + hgetter.getUserName())
				.placeholder(R.drawable.circle1)				
				.transform(new CircleTransform())
				.into(holder.imgUser);				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{*/
		//holder.imgUser.setVisibility(View.GONE);
        holder.txtInit.setText("" + (hgetter.getUserName()).toUpperCase().charAt(0)+"");
        holder.txtInit.setVisibility(View.VISIBLE);
			
		//}

		holder.spinnerMenu.setAdapter(dataAdapter);
		holder.spinnerMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 1) {				
				
					//QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
					//requestBuilder.eq("userid", "xxxxxxx");
					//requestBuilder.eq("recieverID", "aaaaaaa");
					
					buyerid = listUsers.get(holder.posi).getBuyerChatId();
					getdialogid(listUsers.get(holder.posi).getBuyerChatId());			       
					
					//Toast.makeText(con, "Start Chat", Toast.LENGTH_SHORT).show();
				} else if (position == 2) {

					Intent in = new Intent(con, SellerOrderHistoryActivity.class);
					in.putExtra("user_name",hgetter.getUserName());
					Log.e("BuyerId in Adapt", hgetter.getUserId());

					in.putExtra("buyer_id",hgetter.getUserId());
					in.putExtra("store_id",storeId);
					con.startActivity(in);

				} else if (position == 3) {

					if (AppUtil.isNetworkAvailable(con)) {
						dialog = ProgressDialog.show(con, "", "Please wait...");
						RemoveUserTask task = new RemoveUserTask(holder.posi);
						task.execute(new String[] {hgetter.getUserId(),storeId  });

					} else {
						AppUtil.showCustomToast(con,
								"please check your internet connection");
					}


				} else if (position == 4) {

					if (AppUtil.isNetworkAvailable(con)) {
						String seller_Id = AppUtil.getUserId(con);
						dialog = ProgressDialog.show(con, "", "Please wait...");
						BlockUserTask task = new BlockUserTask(holder.posi);
						task.execute(new String[] {seller_Id, hgetter.getUserId() });

					} else {
						AppUtil.showCustomToast(con,
								"please check your internet connection");
					}
				}
			}

			
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		
		holder.txtInit.setText(hgetter.getUserName().charAt(0)+"");
		holder.txtInit.setVisibility(View.VISIBLE);
/*
		if(!hgetter.getUserImage().equalsIgnoreCase("")){
			
			//holder.txtInit.setVisibility(View.GONE);
			//holder.imgUser.setVisibility(View.VISIBLE);
			
			Picasso.with(con).load(hgetter.getUserImage())
			.placeholder(R.drawable.placeholder)
			   .transform(new CircleTransform())
					.into(holder.imgUser);
		}else{
			
			holder.txtInit.setText(hgetter.getUserName().charAt(0));
			holder.txtInit.setVisibility(View.VISIBLE);
			//holder.imgUser.setVisibility(View.GONE);
		}*/

		holder.imgMenu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {				
				holder.spinnerMenu.performClick();
			}
		});
		return row;
	}

	private class RemoveUserTask extends AsyncTask<String, Void, String> {
		int position ;
		public RemoveUserTask(int position){

			this.position = position;
		}
		protected void onPreExecute() {
			super.onPreExecute();
		}
		protected String doInBackground(String... urls) {
			String response = "";
			try {
				JsonCall obj = new JsonCall();
				response = obj.RemoveChatUser(urls[0], urls[1],
						con.getResources().getString(R.string.remove_user_url));
				Log.e("Remove User RRESPONSE", "" + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(String result) {
			JSONObject jObject;
			try {
				if (result != null) {
					jObject = new JSONObject(result);
					JSONObject job = jObject.getJSONObject("commandResult");
					int success = job.getInt("success");
					String message = job.getString("message");

					if (success == 1) {
						AppUtil.showCustomToast(con, message);
						//JSONObject job1 = job.getJSONObject("data");
						listUsers.remove(position);
						notifyDataSetChanged();
						SellerChatActivity.userCount = SellerChatActivity.userCount-1;
					} else {
						AppUtil.showCustomToast(con, message);
					}

				} else {
					AppUtil.showCustomToast(con,
							"please check your internet connection");
				}
				if (dialog != null)
					dialog.cancel();
			} catch (Exception e) {
				e.printStackTrace();
				if (dialog != null)
					dialog.cancel();

			}
		}
	}

	private class BlockUserTask extends AsyncTask<String, Void, String> {
		int position ;
		public BlockUserTask(int position){

			this.position = position;
		}
		protected void onPreExecute() {
			super.onPreExecute();
		}
		protected String doInBackground(String... urls) {
			String response = "";
			try {
				JsonCall obj = new JsonCall();
				response = obj.BlockChatUser(urls[0], urls[1],
						con.getResources().getString(R.string.block_user_url));
				Log.e("Remove User RRESPONSE", "" + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(String result) {
			JSONObject jObject;
			try {
				if (result != null) {
					jObject = new JSONObject(result);
					JSONObject job = jObject.getJSONObject("commandResult");
					int success = job.getInt("success");
					String message = job.getString("message");

					if (success == 1) {
						AppUtil.showCustomToast(con, message);
						//JSONObject job1 = job.getJSONObject("data");
						listUsers.remove(position);
						notifyDataSetChanged();
						SellerChatActivity.userCount = SellerChatActivity.userCount-1;

					} else {
						AppUtil.showCustomToast(con, message);
					}

				} else {
					AppUtil.showCustomToast(con,
							"please check your internet connection");
				}
				if (dialog != null)
					dialog.cancel();
			} catch (Exception e) {
				e.printStackTrace();
				if (dialog != null)
					dialog.cancel();

			}
		}
	}

	 private void getDialogs(final String lchatDialogID,final String Buyerid){
	      
		  progressBar.setVisibility(View.VISIBLE);
	        // Get dialogs
	        //
	        ChatService.getInstance().getDialogs(new QBEntityCallbackImpl() {
	            
	            public void onSuccess(Object object, Bundle bundle) {
	                progressBar.setVisibility(View.GONE);


	                final ArrayList<QBDialog> dialogs = (ArrayList<QBDialog>)object;

	                
	                
	                
	                buildListView(dialogs,lchatDialogID,Buyerid);
	            }

	            
	            public void onError(List errors) {

	             //   AlertDialog.Builder dialog = new AlertDialog.Builder(DialogsActivity.this);
	             //   dialog.setMessage("get dialogs errors: " + errors).create().show();
	            }
	        });
	    }
	 void buildListView(List<QBDialog> dialogs,String lchatDialogID, String Buyerid){
		 
		 
		
		Log.e("dialogid-->",lchatDialogID);
		 
	        final DialogsAdapter adapter = new DialogsAdapter(dialogs,frag.getActivity());
	       
	        int i=0;
	        QBDialog selectedDialog = null;
	        String tempdialogid="5627adf0a28f9a466100168d";
	      while(adapter.getCount()>0)
	       {
	    	   
	    	   adapter.getItem(i);
	    	    selectedDialog = (QBDialog) adapter.getItem(i);
	    	    Log.e("sss--------->",selectedDialog.getDialogId());
	    	    if(selectedDialog.getDialogId().equalsIgnoreCase(lchatDialogID))
	    	    {
	    	    	
	    	    	
	    	    	break;
	    	    }
	    	    else
	    	    {

	    	    }
	    	   
	    	   i++;
	    	   
	       }
	        
	    //    QBDialog selectedDialog = (QBDialog) adapter.getItem(0);

	        Bundle bundle = new Bundle();
	        bundle.putSerializable(ChatActivity.EXTRA_DIALOG, selectedDialog);

	        // Open chat activity
	        //
	        ChatActivity.start(frag.getActivity(), bundle);

	        //finish();
	     
	   
	    }
	 
	 
	 private class GetDialogTask extends AsyncTask<String, Void, String> {

		 
		 private ManageUserListAdapter manageUserListAdapter;

		 public GetDialogTask( ManageUserListAdapter activity ) {
			 manageUserListAdapter = activity;
			}
		 
		 
	        protected void onPreExecute() {
	            super.onPreExecute();
	        }
	        protected String doInBackground(String... urls) {
	            String response = "";
	            try {
	                JsonCall obj = new JsonCall();
	                response = obj.getDialog(urls[0], urls[1],con.getResources().getString(R.string.get_dialog_url));
	                Log.e("GetDialog", "" + response);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return response;
	        }

	        protected void onPostExecute(String result) {
	            JSONObject jObject;
	            try {
	                if (result != null) {

	            
	                    jObject = new JSONObject(result);
	                    JSONObject job = jObject.getJSONObject("commandResult");
	                    int success = job.getInt("success");
	                    String message = job.getString("message");
	                    if (success == 1) {
	                    	 AppUtil.showCustomToast(con,
	 	                            "Please check your internet connection");
	                    	 
	                    	JSONObject joData = job.getJSONObject("data");
	                    	String chatDialogID = joData.getString("ChatDialogID");
	                    	
	                    	Log.e("sdfdsfdsf",chatDialogID)	;
	                    	Log.e("eeeeeeeeee--->",manageUserListAdapter.buyerid); 

	                    	  getDialogs(chatDialogID,manageUserListAdapter.buyerid);
	                    	
	                    }
	                    else
	                    {
        	if (!manageUserListAdapter.buyerid.equalsIgnoreCase("")) {
				/*  No dialog found hence do the following
				                	    
				                		1  Create new dialog
				                		2  save dialog id to database
				                		3  call chat activity
				                		
				 */
				QBPrivateChatManager privateChatManager = QBChatService
						.getInstance().getPrivateChatManager();
				privateChatManager.createDialog(
						Integer.parseInt(manageUserListAdapter.buyerid),
						new QBEntityCallbackImpl<QBDialog>() {
							
							public void onSuccess(QBDialog dialog, Bundle args) {
								Bundle bundle = new Bundle();
								bundle.putSerializable(
										ChatActivity.EXTRA_DIALOG, dialog);

								AddDialogTask task = new AddDialogTask();
								task.execute(new String[] {
										AppUtil.getUserId(con),
										manageUserListAdapter.buyerid,
										dialog.getDialogId() });
								ChatActivity.start(frag.getActivity(), bundle);
							}

							
							public void onError(List<String> errors) {

							}
						});
			}else{
				AppUtil.showCustomToast(con, "Oops,please try after sometime.");
			}
	                    	
	                    	
	                    	
	                    }

	                }
	                else 
	                {
	        
	                	
	                	
		    	    	
		    	    /*	if (AppUtil.isNetworkAvailable(con)) {
		    	            dialog = ProgressDialog.show(con, "", "Please wait...");
		    	            
		    	            AddDialogTask task = new AddDialogTask();

		    	        
		    	            
		    	            
		    	        } else {
		    	            AppUtil.showCustomToast(con,
		    	                    "please check your internet connection");
		    	        }*/
	                   
	                }
	                if (dialog != null)
	                    dialog.cancel();
	            } catch (Exception e) {
	                e.printStackTrace();
	                if (dialog != null)
	                    dialog.cancel();
	            }
	        }
	    }
 
	 
	 private class AddDialogTask extends AsyncTask<String, Void, String> {

		
		  
		 
		 protected void onPreExecute() {
	            super.onPreExecute();
	        }
	        protected String doInBackground(String... urls) {
	            String response = "";
	            try {
	                JsonCall obj = new JsonCall();
	                response = obj.addDialog(urls[0], urls[1], urls[2], con.getResources().getString(R.string.add_dialog_url));
	                Log.e("GetDialog", "" + response);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return response;
	        }

	        protected void onPostExecute(String result) {
	            JSONObject jObject;
	            try {
	                if (result != null) {

	            
	                    jObject = new JSONObject(result);
	                    JSONObject job = jObject.getJSONObject("commandResult");
	                    int success = job.getInt("success");
	                    String message = job.getString("message");
	                    if (success == 1) {
	                    	// AppUtil.showCustomToast(con,
	 	                     //       "Please check your internet connection");
	                    	 
	                   
	                    	 
	                    	 
	                    }

	                } else {
	                    AppUtil.showCustomToast(con,
	                            "Please check your internet connection");
	                }
	                if (dialog != null)
	                    dialog.cancel();
	            } catch (Exception e) {
	                e.printStackTrace();
	                if (dialog != null)
	                    dialog.cancel();
	            }
	        }
	    }

	 
	 
public void  getdialogid(String buyer)
{
	
	 String BuyerId = buyer;
	 
	 if (AppUtil.isNetworkAvailable(con)) {
            dialog = ProgressDialog.show(con, "", "Please wait...");
            
            GetDialogTask task = new GetDialogTask(this);

         //   task.execute(new String[] {AppUtil.getUserId(con), BuyerId });
            task.execute(new String[] {AppUtil.getUserId(con), BuyerId });
            
        } else {
            AppUtil.showCustomToast(con,
                    "please check your internet connection");
        }
	 
}
}


