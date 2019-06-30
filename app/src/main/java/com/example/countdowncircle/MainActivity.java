package com.example.countdowncircle;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn_start,btn_stop;

    private long timeCountInMilliSeconds = 1 * 10000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;

    private ProgressBar progressBarCircle;
    private TextView textViewTime;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        // method call to initialize the listeners
        initListeners();

    }

    /**
     * method to initialize the views
     */
    private void initViews() {
        progressBarCircle = findViewById(R.id.progressBarCircle);

        textViewTime =  findViewById(R.id.textViewTime);
//        imageViewStartStop = findViewById(R.id.imageViewStartStop);
        btn_start=findViewById(R.id.btnStart);
        btn_stop =findViewById(R.id.btnStop);
    }

    /**
     * method to initialize the click listeners
     */
    private void initListeners() {

        btn_start.setOnClickListener(this);
    }

    /**
     * implemented method to listen clicks
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                startStop();
                break;
        }
    }


    /**
     * method to start and stop count down timer
     */
    private void startStop() {

        if (timerStatus == TimerStatus.STOPPED) {

            progressBarCircle.setVisibility(View.VISIBLE);
            textViewTime.setVisibility(View.VISIBLE);


            // call to initialize the progress bar values
            setProgressBarValues();


            btn_start.setVisibility(View.GONE);
            btn_stop.setVisibility(View.VISIBLE);

            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();

        } else {


            progressBarCircle.setVisibility(View.GONE);
            textViewTime.setVisibility(View.GONE);

            btn_start.setVisibility(View.VISIBLE);
            btn_stop.setVisibility(View.GONE);

            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();

        }

    }


    /**
     * method to start count down timer
     */
    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {

                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));

                setProgressBarValues();

                btn_start.setVisibility(View.VISIBLE);
                btn_stop.setVisibility(View.GONE);
                progressBarCircle.setVisibility(View.GONE);
                textViewTime.setVisibility(View.GONE);

                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;
            }

        }.start();
        countDownTimer.start();
    }

    /**
     * method to stop count down timer
     */
    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    /**
     * method to set circular progress bar values
     */
    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {

//        String hms = String.format("%02d:%02d:%02d",
//                TimeUnit.MILLISECONDS.toHours(milliSeconds),
//                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
//                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
//
//        return hms;

        String hms = String.format("%02d",
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;
    }

}
