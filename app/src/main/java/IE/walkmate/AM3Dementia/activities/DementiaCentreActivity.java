package IE.walkmate.AM3Dementia.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.webkit.WebSettings;
import android.webkit.WebView;
import IE.walkmate.AM3Dementia.R;

/**
 * * Author: Team B40
 *  * Version: 01
 *  This class is essentially responsible for showing dementia care centre, the vebview component is used here
 *  which helps in achieving web site pages within android studio.
 *  loadview is used here to display webview of the page.
 */
public class DementiaCentreActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private ActionBar actionBar;
    private WebView mWebView;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        mWebView = findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

        // REMOTE RESOURCE
        mWebView.loadUrl("https://www.google.com/maps/search/dementia+care+centres+victoria/@-37.7963533,145.0384249,11z/data=!3m1!4b1");

        // LOCAL RESOURCE
        // mWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
