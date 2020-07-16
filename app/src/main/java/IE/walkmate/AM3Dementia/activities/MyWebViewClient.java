package IE.walkmate.AM3Dementia.activities;

import android.webkit.WebViewClient;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class MyWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String hostname;

        // YOUR HOSTNAME
        hostname = "https://www.google.com/maps/search/dementia+care+centres+victoria/@-37.7963533,145.0384249,11z/data=!3m1!4b1";

        Uri uri = Uri.parse(url);
        if (url.startsWith("file:") || uri.getHost() != null && uri.getHost().endsWith(hostname)) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }
}
