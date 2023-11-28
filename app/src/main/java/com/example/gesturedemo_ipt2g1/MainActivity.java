package com.example.gesturedemo_ipt2g1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;

public class MainActivity extends AppCompatActivity {
    private static final long LONG_PRESS_DURATION = 1000; // 1 second in milliseconds
    private long pressStartTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressStartTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        long pressDuration = System.currentTimeMillis() - pressStartTime;
                        if (pressDuration >= LONG_PRESS_DURATION) {
                            // Long press detected
                            showCircle(motionEvent.getX(), motionEvent.getY());
                            showMessageNote();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void showCircle(float x, float y) {
        try {
            SVG svgBall = SVG.getFromString("<svg height=\"50\" width=\"50\"><circle cx=\"25\" cy=\"25\" r=\"20\" fill=\"red\" /></svg>");
            Drawable drawable = new PictureDrawable(svgBall.renderToPicture());
            addSvgToLayout(drawable, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showMessageNote() {
        Toast.makeText(this, "Yay Ball is Created!", Toast.LENGTH_SHORT).show();
    }

    private void addSvgToLayout(Drawable drawable, float x, float y) {
        SVGImageView svgImageViewObject = new SVGImageView(this);
        svgImageViewObject.setImageDrawable(drawable);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        svgImageViewObject.setLayoutParams(layoutParams);
        svgImageViewObject.setX(x - drawable.getIntrinsicWidth() / 2);
        svgImageViewObject.setY(y - drawable.getIntrinsicHeight() / 2);
        ViewGroup rootView = findViewById(android.R.id.content);
        rootView.addView(svgImageViewObject);
    }
}