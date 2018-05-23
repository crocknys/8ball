package fr.wcs.thedecider;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    HashMap<Integer, String> mDecission = new HashMap<Integer, String>();

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout fond = findViewById(R.id.ecran);

        mDecission.put(1, "Essaye plus tard");
        mDecission.put(2, "Essaye encore");
        mDecission.put(3, "Pas d'avis");
        mDecission.put(4, "C'est ton destin");
        mDecission.put(5, "Le sort en est jeté");
        mDecission.put(6, "Une chance sur deux");
        mDecission.put(7, "Repose ta question");
        mDecission.put(8, "D'après moi oui");
        mDecission.put(9, "C'est certain");
        mDecission.put(10, "Oui absolument");
        mDecission.put(11, "Tu peux compter dessus");
        mDecission.put(12, "Sans aucun doute");
        mDecission.put(13, "Très probable");
        mDecission.put(14, "Oui");
        mDecission.put(15, "C'est bien parti");
        mDecission.put(16, "C'est non");
        mDecission.put(17, "Peu probable");
        mDecission.put(18, "Faut pas rêver");
        mDecission.put(19, "N'y compte pas");
        mDecission.put(20, "Impossible");

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });

        fond.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                TextView text = findViewById(R.id.text);
                ImageView ball = findViewById(R.id.ball);

                text.setVisibility(View.GONE);
                ball.setImageResource(R.drawable.ball_front);

                return false;
            }
        });
    }

    public void handleShakeEvent(int count) {

        TextView text = findViewById(R.id.text);
        ImageView ball = findViewById(R.id.ball);
        if (count >= 3) {
            double random = Math.floor(Math.random() * Math.floor(21));
            int answer = (int) Math.round(random);
            if (answer<=0) {
                answer = 1;
            }

            text.setVisibility(View.VISIBLE);
            ball.setImageResource(R.drawable.ball_back);
            text.setText(mDecission.get(answer));
            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
            text.startAnimation(startAnimation);

        }
    }
}
