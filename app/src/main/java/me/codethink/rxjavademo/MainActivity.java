package me.codethink.rxjavademo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends ActionBarActivity {
    private static final String LOG_TAG = "RX_JAVA_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hello World
        basicRxHelloWorld();
        simplifiedRxHelloWorld();
        simplifiedRxHelloWorldWithLambda();

        //Markdown to HTML
        basicMarkdown2Html();
        basicMarkdown2HtmlSimplifiedWithLambda();

        //unsubscribe
        unsubscribe();
    }

    private void basicRxHelloWorld() {
        // 创建observable
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxJava");
                subscriber.onCompleted();
            }
        });

        // 创建subscriber
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String s) {
                log(s);
            }
        };

        // 订阅
        observable.subscribe(subscriber);
    }

    private void simplifiedRxHelloWorld() {
        Observable.just("Hello RxJava Simplified").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log(s);
            }
        });
    }

    private void simplifiedRxHelloWorldWithLambda() {
        Observable.just("Hello RxJava Simplified with Lambda").subscribe(s -> log(s));
    }

    private void basicMarkdown2Html() {
        Observable.just("#Basic Markdown to HTML").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                if (s != null && s.startsWith("#")) {
                    return "<h1>" + s.substring(1, s.length()) + "</h1>";
                }
                return null;
            }
        }).subscribe(s -> log(s));
    }

    private void basicMarkdown2HtmlSimplifiedWithLambda() {
        Observable.just("#Basic Markdown to HTML with lambda")
                .map(s -> s != null && s.startsWith("#") ? "<h1>" + s.substring(1, s.length()) + "</h1>" : null)
                .subscribe(s -> log(s));
    }

    private void unsubscribe() {
        Subscription subscription = Observable.just("Unsubscribe me later").subscribe(s -> log(s));
        subscription.unsubscribe();
        log("isUnsubscribed = " + subscription.isUnsubscribed());
    }
    private void log (String s) {
        Log.v(LOG_TAG, s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
