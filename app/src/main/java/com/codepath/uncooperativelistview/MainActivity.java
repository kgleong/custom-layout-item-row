package com.codepath.uncooperativelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
    }

    private void setupViews() {
        myListView = (ListView) findViewById(R.id.myListView);
        List<String> items = MyAdapter.generateList(1000);
        myListView.setAdapter(new MyAdapter(this, items));
    }
}
