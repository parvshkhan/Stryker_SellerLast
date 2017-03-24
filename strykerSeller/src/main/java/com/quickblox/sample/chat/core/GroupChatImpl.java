package com.quickblox.sample.chat.core;

import android.util.Log;
import android.widget.Toast;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBGroupChat;
import com.quickblox.chat.QBGroupChatManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBMessageListenerImpl;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.sample.chat.ui.activities.ChatActivity;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.DiscussionHistory;

import java.util.Arrays;
import java.util.List;

public class GroupChatImpl extends QBMessageListenerImpl<QBGroupChat> implements Chat {
    private static final String TAG = GroupChatImpl.class.getSimpleName();

    private ChatActivity chatActivity;

    private QBGroupChatManager groupChatManager;
    private QBGroupChat groupChat;

    public GroupChatImpl(ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
    }

    public void joinGroupChat(QBDialog dialog, QBEntityCallback callback){
        initManagerIfNeed();

        if(groupChat == null) {
            groupChat = groupChatManager.createGroupChat(dialog.getRoomJid());
        }
        join(groupChat, callback);
    }

    private void initManagerIfNeed(){
        if(groupChatManager == null){
            groupChatManager = QBChatService.getInstance().getGroupChatManager();
        }
    }

    private void join(final QBGroupChat groupChat, final QBEntityCallback callback) {
        DiscussionHistory history = new DiscussionHistory();
        history.setMaxStanzas(0);

        Toast.makeText(chatActivity, "Joining room...", Toast.LENGTH_LONG).show();

        groupChat.join(history, new QBEntityCallbackImpl() {
            
            public void onSuccess() {

                groupChat.addMessageListener(GroupChatImpl.this);

                chatActivity.runOnUiThread(new Runnable() {
                    
                    public void run() {
                        callback.onSuccess();

                        Toast.makeText(chatActivity, "Join successful", Toast.LENGTH_LONG).show();
                    }
                });
                Log.w("Chat", "Join successful");
            }

            
            public void onError(final List list) {
                chatActivity.runOnUiThread(new Runnable() {
                    
                    public void run() {
                        callback.onError(list);
                    }
                });


                Log.w("Could not join chat, errors:", Arrays.toString(list.toArray()));
            }
        });
    }

    public void leave(){
        try {
            groupChat.leave();
        } catch (SmackException.NotConnectedException nce) {
            nce.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    
    public void release() throws XMPPException {
        if (groupChat != null) {
            leave();

            groupChat.removeMessageListener(this);
        }
    }

    
    public void sendMessage(QBChatMessage message) throws XMPPException, SmackException.NotConnectedException {
        if (groupChat != null) {
            try {
                groupChat.sendMessage(message);
            } catch (SmackException.NotConnectedException nce){
                nce.printStackTrace();
                Toast.makeText(chatActivity, "Can't send a message, You are not connected to chat", Toast.LENGTH_SHORT).show();
            } catch (IllegalStateException e){
                e.printStackTrace();
                Toast.makeText(chatActivity, "You are still joining a group chat, please wait a bit", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(chatActivity, "Join unsuccessful", Toast.LENGTH_LONG).show();
        }
    }

    
    public void processMessage(QBGroupChat groupChat, QBChatMessage chatMessage) {
        // Show message
        Log.w(TAG, "new incoming message: " + chatMessage);
        chatActivity.showMessage(chatMessage);
    }

    
    public void processError(QBGroupChat groupChat, QBChatException error, QBChatMessage originMessage){

    }
}
