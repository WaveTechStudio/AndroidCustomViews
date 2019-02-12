package wavetechstudio.androidCustomViews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        CustomViewInstantiateFromCode customViewInstantiateFromCode = new CustomViewInstantiateFromCode(this);
        customViewInstantiateFromCode.setFillColor(getResources().getColor(R.color.random));
        linearLayout.addView(customViewInstantiateFromCode);

        setContentView(linearLayout);
    }
}
