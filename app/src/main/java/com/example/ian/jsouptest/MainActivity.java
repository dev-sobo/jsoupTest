package com.example.ian.jsouptest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.htmlButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncHTTP().execute();
            }
        });
    }
    /*public void executeAsync() {
        new AsyncHTTP().execute();
    }*/
         class AsyncHTTP extends AsyncTask<Void, Void, Void> {
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0";
        String rosterAppsLogin = "https://jetblue.rosterapps.com/Login.aspx?ReturnUrl=/";
        String rosterAppsCalendar = "https://jetblue.rosterapps.com/calendar.month.aspx";
        String eventTarget = "__EVENTTARGET";
        String eventArgument = "__EVENTARGUMENT";
        String lastFocus = "__LASTFOCUS";
        String viewState = "__VIEWSTATE";
        String viewStateGenerator = "__VIEWSTATEGENERATOR";
        String txtUsername = "txtUsername";
        String txtPassword = "txtPassword";
        String btnLogin = "btnLogin";
             @Override
             protected Void doInBackground(Void... objects) {
                 try {
                    Connection.Response loginResponse = Jsoup.connect(rosterAppsLogin)
                            .userAgent(userAgent)
                            .data(eventTarget,"")
                            .data(eventArgument, "")
                            .data(lastFocus, "")
                            .data(viewState,"/wEPDwULLTE0NTU3NDM0MzlkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBRBjaGtQZXJzaXN0Q29va2llI+gcwdzIyCAjhj2bPon1rUHC+z8=")
                            .data(viewStateGenerator, "C2EE9ABB")
                            .data(txtUsername, "ian.sobocinski@jetblue.com")
                            .data(txtPassword, "Thisisforthezoos.")
                            .data(btnLogin, "Login")
                            .method(Connection.Method.POST)
                            .execute();
                     Map<String, String> loginCookie = loginResponse.cookies();
                   //  Log.i(LOG_TAG," LOGIN_RESPONSE: " + loginResponse.parse().html());
                     Document calendarDocument = Jsoup.connect(rosterAppsCalendar)
                             .userAgent(userAgent)
                             .cookies(loginCookie)
                             .execute().parse();
                     System.out.print(calendarDocument.html());

                 } catch (IOException e) {
                     Log.e(LOG_TAG,e.toString());
                 }
                 return null;
             }

         }

    }

