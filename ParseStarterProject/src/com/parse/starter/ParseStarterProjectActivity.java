package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class ParseStarterProjectActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        String[] lastnames = {
                "Washington",
                "Adams",
                "Jefferson"
        };

        final String[] objectIds = new String[3];

        for (int i = 0; i < lastnames.length; ++i) {
            final int j = i;
            final ParseObject parseObject = new ParseObject("PresidentObject");
            parseObject.put("myid", i);
            parseObject.put("lastname", lastnames[i]);
            /*Task<Void> task =*/ parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("myTag", "got objectId " + parseObject.getObjectId());
                        objectIds[j] = parseObject.getObjectId();
                    } else {
                        Log.d("myTag", e.toString());
                        Toast toast = Toast.makeText(ParseStarterProjectActivity.this,
                                e.toString(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });
            //Log.d("myTag", "objectId = " + parseObject.getObjectId());
            //objectIds[i] = parseObject.getObjectId();
        }

        final TextView textView = (TextView)findViewById(R.id.textView);
        textView.append("\n\n");

        for (int i = 0; i < lastnames.length; ++i) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("PresidentObject");
            query.getInBackground(objectIds[i], new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {

                    if (e == null) {
                        Toast toast = Toast.makeText(ParseStarterProjectActivity.this,
                                "something went wrong", Toast.LENGTH_LONG);
                        toast.show();

                    } else {
                        /*Toast toast = Toast.makeText(ParseStarterProjectActivity.this,
                                object.getString("lastname"), Toast.LENGTH_LONG);
                        toast.show();*/
                        textView.append(object.getString("lastname") + "\n");

                    }
                }
            });
        }
    }
}
