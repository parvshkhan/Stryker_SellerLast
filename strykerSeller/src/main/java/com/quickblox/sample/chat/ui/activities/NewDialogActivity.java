package com.quickblox.sample.chat.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.app.strykerseller.R;
import com.quickblox.sample.chat.core.ChatService;
import com.quickblox.sample.chat.ui.adapters.UsersAdapter;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class NewDialogActivity extends BaseActivity implements QBEntityCallback<ArrayList<QBUser>> {

    private static final int PAGE_SIZE = 10;

    private int listViewIndex;
    private int listViewTop;
    private int currentPage = 0;
    private List<QBUser> users = new ArrayList<QBUser>();

    private PullToRefreshListView usersList;
    private Button createChatButton;
    private ProgressBar progressBar;
    private UsersAdapter usersAdapter;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new);

        usersList = (PullToRefreshListView) findViewById(R.id.usersList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        createChatButton = (Button) findViewById(R.id.createChatButton);
        createChatButton.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {

                ChatService.getInstance().addDialogsUsers(usersAdapter.getSelected());

                // Create new group dialog
                //
                QBDialog dialogToCreate = new QBDialog();
                dialogToCreate.setName(usersListToChatName());
                if (usersAdapter.getSelected().size() == 1) {
                    dialogToCreate.setType(QBDialogType.PRIVATE);
                } else {
                    dialogToCreate.setType(QBDialogType.GROUP);
                }
                dialogToCreate.setOccupantsIds(getUserIds(usersAdapter.getSelected()));

                QBChatService.getInstance().getGroupChatManager().createDialog(dialogToCreate, new QBEntityCallbackImpl<QBDialog>() {
                    
                    public void onSuccess(QBDialog dialog, Bundle args) {
                        if (usersAdapter.getSelected().size() == 1) {
                            startSingleChat(dialog);
                        } else {
                            startGroupChat(dialog);
                        }
                    }

                    
                    public void onError(List<String> errors) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(NewDialogActivity.this);
                        dialog.setMessage("dialog creation errors: " + errors).create().show();
                    }
                });
            }
        });

        usersList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // Do work to refresh the list here.
                loadNextPage();
                listViewIndex = usersList.getRefreshableView().getFirstVisiblePosition();
                View v = usersList.getRefreshableView().getChildAt(0);
                listViewTop = (v == null) ? 0 : v.getTop();
            }
        });

        if(isSessionActive()){
            loadNextPage();
        }
    }

    
    public void onBackPressed() {
        Intent i = new Intent(NewDialogActivity.this, DialogsActivity.class);
        startActivity(i);
        finish();
    }


    public static QBPagedRequestBuilder getQBPagedRequestBuilder(int page) {
        QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
        pagedRequestBuilder.setPage(page);
        pagedRequestBuilder.setPerPage(PAGE_SIZE);

        return pagedRequestBuilder;
    }


    
    public void onSuccess(ArrayList<QBUser> newUsers, Bundle bundle){

        // save users
        //
        users.addAll(newUsers);

        // Prepare users list for simple adapter.
        //
        usersAdapter = new UsersAdapter(users, this);
        usersList.setAdapter(usersAdapter);
        usersList.onRefreshComplete();
        usersList.getRefreshableView().setSelectionFromTop(listViewIndex, listViewTop);

        progressBar.setVisibility(View.GONE);
    }

    
    public void onSuccess(){

    }

    
    public void onError(List<String> errors){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("get users errors: " + errors).create().show();
    }


    private String usersListToChatName(){
        String chatName = "";
        for(QBUser user : usersAdapter.getSelected()){
            String prefix = chatName.equals("") ? "" : ", ";
            chatName = chatName + prefix + user.getLogin();
        }
        return chatName;
    }

    public void startSingleChat(QBDialog dialog) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ChatActivity.EXTRA_DIALOG, dialog);

        ChatActivity.start(this, bundle);
    }

    private void startGroupChat(QBDialog dialog){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ChatActivity.EXTRA_DIALOG, dialog);

        ChatActivity.start(this, bundle);
    }

    private void loadNextPage() {
        ++currentPage;

        QBUsers.getUsers(getQBPagedRequestBuilder(currentPage), this);
    }

    public static ArrayList<Integer> getUserIds(List<QBUser> users){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for(QBUser user : users){
            ids.add(user.getId());
        }
        return ids;
    }

    //
    // ApplicationSessionStateCallback
    //

    
    public void onStartSessionRecreation() {

    }

    
    public void onFinishSessionRecreation(final boolean success) {
        runOnUiThread(new Runnable() {
            
            public void run() {
                if (success) {
                    loadNextPage();
                }
            }
        });
    }
}
