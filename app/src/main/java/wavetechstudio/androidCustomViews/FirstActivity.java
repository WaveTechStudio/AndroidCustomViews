package wavetechstudio.androidCustomViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomTextView launchSecondActivity = findViewById(R.id.launchSecondActivity);
        launchSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));
            }
        });

    }

    public void launchThirdScreen(View v) {
        startActivity(new Intent(FirstActivity.this, ThirdActivity.class));
    }
}
