package mx.com.pegasus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.root.kotlinappliacation.R;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        RippleCircleButton rippleCircleButton = findViewById(R.id.pulse_circle_button);
        rippleCircleButton.setSecondaryCirclesNumber(5);

    }

}
