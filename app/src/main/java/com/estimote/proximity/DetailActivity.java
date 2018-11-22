package com.estimote.proximity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.estimote.proximity.estimote.Utils;

public class DetailActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view);

        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        ConstraintLayout view = findViewById(R.id.detalView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String titleString = bundle.getString("title");
            title.setText(titleString);
            subtitle.setText(bundle.getString("subtitle"));

            view.setBackgroundColor(Utils.getEstimoteColor(titleString));
        }




    }
}
