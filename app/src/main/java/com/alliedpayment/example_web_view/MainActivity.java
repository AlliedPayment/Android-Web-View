package com.alliedpayment.example_web_view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String WEB_VIEW_URL = "com.allied_payment.example_web_view.URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_urls);
        String[] countries = getResources().getStringArray(R.array.example_urls_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);
    }

    public void launchWebView(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        EditText editText = (EditText) findViewById(R.id.autocomplete_urls);
        String message = editText.getText().toString();
        intent.putExtra(WEB_VIEW_URL, message);
        startActivity(intent);
    }
}
