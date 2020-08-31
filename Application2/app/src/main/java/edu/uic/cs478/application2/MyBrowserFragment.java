package edu.uic.cs478.application2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Arrays;
import java.util.List;

public class MyBrowserFragment extends Fragment {
    private List<String> myWebsiteList;
    private int myPos = -1;
    private WebView myWebView;

    private View v;


    int getShownIndex() { return myPos; }

    // Show the website at position using webview
    void showQuoteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= myWebsiteList.size())
            return;
        myPos = newIndex;

        // Get appropriate pos of website list
        myWebView.loadUrl(myWebsiteList.get(myPos));

        // Forces to open in webview instead of browser
        myWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get appropriate website list
        Bundle bundle = this.getArguments();
        if(bundle != null){
            String type = bundle.getString("Website");
            Log.i("WHATSTRING", type);

            if(type.equals("AttractionsWebsite")){
                this.myWebsiteList = Arrays.asList(getResources().getStringArray(R.array.attractionsWebsiteArray));
            }
            else if(type.equals("RestaurantsWebsite")){
                this.myWebsiteList = Arrays.asList(getResources().getStringArray(R.array.restaurantsWebsiteArray));
            }
        }


        // Retain this Fragment across Activity reconfigurations
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create a webview on browser fragment
         v = inflater.inflate(R.layout.my_web_view, container, false);
        myWebView = (WebView) v.findViewById(R.id.webViewID);

        // Enable javascript in webview
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        return v;
    }

}
