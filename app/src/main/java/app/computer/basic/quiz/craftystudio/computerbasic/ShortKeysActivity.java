package app.computer.basic.quiz.craftystudio.computerbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;

public class ShortKeysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_keys);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        try{
            Answers.getInstance().logCustom(new CustomEvent("Short key list open"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void basicShortKeys(View view) {

        Intent intent = new Intent(ShortKeysActivity.this, ShortKeyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ShortKeyType", "Basic Shortcut Keys");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void microsoftWindowsKeys(View view) {

        Intent intent = new Intent(ShortKeysActivity.this, ShortKeyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ShortKeyType", "Microsoft Windows Short Keys");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void micrsoftWordKey(View view) {

        Intent intent = new Intent(ShortKeysActivity.this, ShortKeyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ShortKeyType", "Micrsoft Word Short Key");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void microsoftExcelKey(View view) {

        Intent intent = new Intent(ShortKeysActivity.this, ShortKeyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ShortKeyType", "Microsoft Excel Short Key");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void googleChromeKey(View view) {

        Intent intent = new Intent(ShortKeysActivity.this, ShortKeyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ShortKeyType", "Google Chrome Short Key");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void specialChracterKey(View view) {

        Intent intent = new Intent(ShortKeysActivity.this, ShortKeyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ShortKeyType", "Special Chracter Short Key");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void linuAndUnixKey(View view) {

        Intent intent = new Intent(ShortKeysActivity.this, ShortKeyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ShortKeyType", "Linux And Unix Short Key");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void appleShortKey(View view) {

        Intent intent = new Intent(ShortKeysActivity.this, ShortKeyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ShortKeyType", "Apple Short Key");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
