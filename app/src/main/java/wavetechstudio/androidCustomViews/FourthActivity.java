package wavetechstudio.androidCustomViews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import java.util.Random;

public class FourthActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        CustomViewGroup customLayout = findViewById(R.id.custom_layout);

        Random rnd = new Random();
        for (int i = 0; i < 50; i++) {
            EnhancedCustomView view = new EnhancedCustomView(this);

            int width = rnd.nextInt(200) + 50;
            int height = rnd.nextInt(100) + 100;
            view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            view.setPadding(2, 2, 2, 2);

            customLayout.addView(view);
        }
    }
}
