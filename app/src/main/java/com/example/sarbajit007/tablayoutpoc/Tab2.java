package com.example.sarbajit007.tablayoutpoc;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

/**
 * Created by sarbajit007 on 12/2/2017.
 */

public class Tab2 extends Fragment {
    WebView webView;
    EditText editText;
    ImageButton imageButton;
    ProgressBar progressBar;
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editText = (EditText) getView().findViewById(R.id.search_txt);
        webView = (WebView) getView().findViewById(R.id.webview_id);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressbar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.GONE);
        imageButton = (ImageButton) getView().findViewById(R.id.search_imgbtn);
        String newUA = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.31 (KHTML, like gecko)Chrome/26.0.1410.63 Safari/537.31";
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.getSettings().setUserAgentString(newUA);
            webView.getSettings().setJavaScriptEnabled(true);

            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);

            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setBackgroundColor(Color.WHITE);
            webView.setWebViewClient(new ourViewClient());
            webView.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    progressBar.setProgress(newProgress);
                    if (newProgress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                        progressBar.setVisibility(progressBar.VISIBLE);
                    }
                    if (newProgress == 100) {
                        progressBar.setVisibility(progressBar.GONE);
                    }
                }
            });
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                webView.loadUrl("https://"+editText.getText().toString());
                editText.setText("");
            }
        });

    }
    private class ourViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            CookieManager.getInstance().setAcceptCookie(true);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater=getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_back:
                if (webView.canGoBack())
                    webView.goBack();
                return true;
            case R.id.go_forwrd:
                if (webView.canGoForward())
                    webView.goForward();
                return true;
            case R.id.go_home:
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                webView.loadUrl("https://images.google.com");
                editText.setText("");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
    setHasOptionsMenu(true);
        return inflater.inflate(R.layout.tab2, container, false);
    }

}
