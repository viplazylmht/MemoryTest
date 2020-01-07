package com.viplazy.memorytest;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    RelativeLayout window;
    static int width, height;

    static TextView tvRound, divider, tvTime;

    static long startTime;

    static int round = 0;

    static final ArrayList<GameObject> listNum = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        window = findViewById(R.id.container);

        window.post(new Runnable() {
            @Override
            public void run() {

                width = window.getMeasuredWidth();
                height = window.getMeasuredHeight();

            }
        });

        tvRound = findViewById(R.id.tv_round);
        tvRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar("Touch any number to start!", 2000);
                generateAllNum();

                window.removeAllViews();

                tvRound.setText(String.format("ROUND %d", round));
                window.addView(tvRound);
                window.addView(divider);
                window.addView(tvTime);

                for (GameObject item : listNum) {
                    item.addView();
                }
            }
        });

        divider = findViewById(R.id.divider);
        tvTime = findViewById(R.id.tv_time);

        //listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_0), "0"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_1), "1"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_2), "2"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_3), "3"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_4), "4"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_5), "5"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_6), "6"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_7), "7"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_8), "8"));
        listNum.add(new GameObject(this, window, this.getResources().getDrawable(R.drawable.ic_num_9), "9"));

        for (GameObject item : listNum) {

            item.getImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(ImageView.INVISIBLE);
                    GameActivity.checkList();
                }
            });
        }


        resetTime();
    }



    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }

    private void showSnackbar(String message, int duration)
    {
        // Create snackbar
        final Snackbar snackbar = Snackbar.make(window , message, duration);

        // Set an action on it, and a handler

        /*
        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        */
        snackbar.setBackgroundTint(this.getResources().getColor(R.color.colorDarkGrey));
        snackbar.show();
    }
    private static void showSnackbar(RelativeLayout container, String message, int duration)
    {
        // Create snackbar
        final Snackbar snackbar = Snackbar.make(container, message, duration);

        // Set an action on it, and a handler

        /*
        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        */
        snackbar.setBackgroundTint(container.getResources().getColor(R.color.colorDarkGrey));
        snackbar.show();
    }


    public static void generateAllNum() {
        for (GameObject item : listNum) {

            Rectangle rect = new Rectangle(120, 120);


            boolean valid = false;
            while (!valid) {

                rect.posX = (int) Math.round(getRandomDoubleBetweenRange(20, width - item.getWidth()*1.2));
                rect.posY = (int) getRandomDoubleBetweenRange(20, height - item.getHeight()*4);

                valid = true;

                for (int i = 0; i < listNum.size(); i++) {
                    valid = valid && listNum.get(i).isVaildPos(rect);
                }
            }

            item.setRect(rect);
        }
    }

    public static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }

    public static void checkList() {

        int sum = countChecked();


        if (sum == 1) {
            // first time
            resetTime();
        }

        if (sum == listNum.size()) {
            // all check
            tvTime.setText(String.format("%f", getLap()/1000.0));
            round += 1;

            String str = String.format("ROUND %d", round);
            tvRound.setText(str);

            RelativeLayout window = listNum.get(0).getContainer();
            showSnackbar(window, str, 1000);

            // reinitialize data
            generateAllNum();
            window.removeAllViews();


            window.addView(tvRound);
            window.addView(divider);
            window.addView(tvTime);

            for (GameObject item : listNum) {
                item.addView();
            }


        }
    }

    private static boolean isAllChecked() {
        return countChecked() == listNum.size();
    }

    private static int countChecked() {

        int sum = 0;
        for (GameObject item : listNum) {
            if (item.getImage().getVisibility() != ImageView.VISIBLE) sum += 1;
        }

        return sum;
    }


    public static void resetTime() {
        startTime = System.nanoTime();
    }
    public static long getLap() {
        long now = System.nanoTime() ;
        // Thời gian cập nhập lại giao diện Game
        // (Đổi từ Nanosecond ra milisecond).
        long waitTime = (now - startTime)/1000000;
        if(waitTime < 10) waitTime = 10; // Millisecond

        return waitTime;
    }
}