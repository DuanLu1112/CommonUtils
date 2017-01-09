package com.dl.commonutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by DuanLu on 2016/12/18 12:26.
 *
 * @author DuanLu
 * @version 1.0.0
 * @class WebViewUtils
 * @describe WebView工具类
 */
public class WebViewUtils {
    private static final String TAG = "WebViewUtils";

    public static void setRichText(WebView webView, String baseUrl, String richText) {
        webView.loadDataWithBaseURL(baseUrl,
                "<head><meta name=\"viewport\" content=\"width=device-width," +
                        " initial-scale=1.0,user-scalable=no\"/><style type=\"text/css\">" +
                        "img,table,video{max-width: 100% !important;height:auto !important;}" +
                        "</style></head>" +
                        richText,
                "text/html", "UTF-8", null);
//        webView.loadDataWithBaseURL(ServerURL.SERVER_API_URL,
//                "<head><meta name=\"viewport\" content=\"width=device-width, " +
//                        "initial-scale=1.0,user-scalable=no\"/>" +
//                        "<style type=\"text/css\">img,table," +
//                        "video{ max-width: 100% !important;  }" +
//                        "</style></head>" +
//                        richText,
//                "text/html", "UTF-8", null);
    }

    /**
     * 初始化WebView
     *
     * @param webView WebView
     */
    public static void initWebView(WebView webView) {
        // 获取WebView属性
        WebSettings ws = webView.getSettings();
        // 设置支持javascript代码
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setDatabaseEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setSaveFormData(false);
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        ws.setLoadWithOverviewMode(false);
        ws.setUseWideViewPort(true);
        ws.setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            /**
             * 处理https请求，为WebView处理ssl证书设置
             * @param view
             * @param handler
             * @param error
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受信任所有网站的证书
                //handler.cancel();   // 默认操作 不处理
                //handler.handleMessage(null);  // 可做其他处理
                //super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    public static void onPause(WebView webView) {
        try {
            if (webView != null) {
                webView.onPause();
                webView.stopLoading();
                webView.reload();
            }
        } catch (Exception e) {
            Log.e(TAG, "WebViewUtils: WebView onPause happend Exception");
        }
    }

    public static void onResume(WebView webView) {
        if (webView != null) {
            webView.onResume();
        }
    }

    public static void onDestroy(WebView webView) {
        if (webView != null) {
            webView.loadUrl("");
            webView.stopLoading();
        }
    }

    public static void onKeyDown(Activity activity, WebView webView, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null) {
                webView.loadUrl("");
                webView.stopLoading();
            }
            activity.finish();
        }
    }

}
