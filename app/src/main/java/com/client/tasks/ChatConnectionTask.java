package com.client.tasks;

import android.os.AsyncTask;

import org.jivesoftware.smack.AbstractXMPPConnection;

/**
 * Created by lee on 6/13/16.
 */
public class ChatConnectionTask extends AsyncTask<AbstractXMPPConnection, Void, Boolean> {
    private AbstractXMPPConnection connection;
    public ChatConnectionTask(AbstractXMPPConnection connection){
        this.connection = connection;
    }
    @Override
    protected Boolean doInBackground(AbstractXMPPConnection... params) {
        boolean flag = false;
        try {
            connection.connect();
            connection.login("user2", "123456");
            flag = true;
        }catch (Exception e){

        }
        return flag;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

    }
}
