package cn.disttime.diitoa;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private static  final  String oaWebUrl = "http://211.103.164.29:8081/oa/login.jsp";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * 初始化代码
     */
    private void init(){
        webView = (WebView) findViewById(R.id.webView);

        //加载OA网页
        webView.loadUrl(oaWebUrl);

        //设置打开方式为内置WebView控件
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                //返回false阻止用第三方浏览器打开页面
                return  false;
            }

        });

        //启用javascript支持
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    /**
     * 为了防止点击返回物理按键直接退出应用，所以需要捕获返回事件
     *
     * @param keyCode   按键动作标识
     * @param event     按键动作
     *
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
                return true;
            }else{
                System.exit(0);
            }
        }

        return  super.onKeyDown(keyCode, event);
    }
}
