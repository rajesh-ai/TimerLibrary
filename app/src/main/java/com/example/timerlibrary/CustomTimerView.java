package com.example.timerlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CustomTimerView extends AppCompatTextView
{
    private long endTime;
    private String expiryMessage;
    private boolean isTimerExpired = false;
    private CountDownTimer timer;
    private CountDownListener countDownListener;
    private boolean showTimeLeft;

    public CustomTimerView(Context context) {
        this(context, null);
        setFont(context);
    }

    public CustomTimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setFont(context);
    }

    public CustomTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimerView, defStyleAttr, 0);
        System.out.println("message >>>");

        try {
            expiryMessage = a.getString(R.styleable.TimerView_expiry_message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }
        createTimer();

    }

    private void createTimer() {
        if (endTime > 0) {
            timer = new CountDownTimer(endTime - System.currentTimeMillis(), 1000) {
                @Override
                public void onTick(long l) {
                    setTime(l);
                    setTimerExpired(false);
                }

                @Override
                public void onFinish() {
                    onExpired();
                    if (countDownListener != null)
                        countDownListener.onFinish();
                }
            };
        }

    }

    private void setTime(long l) {

        long days = TimeUnit.MILLISECONDS.toDays(l);
        long secs = TimeUnit.MILLISECONDS.toSeconds(l) % 60;
        long mins = TimeUnit.MILLISECONDS.toMinutes(l) % 60;
        long hrs = TimeUnit.MILLISECONDS.toHours(l) % 24;

        String strDays = days > 1 ? "Days" : "Day";

        if (days >= 1) {
            setText(String.format(Locale.US, (showTimeLeft ? "Ends in %s %s" : "%s %s"), days, strDays));
        } else {
            if (days == 0 && hrs == 0 && mins == 0 && secs < 11) {
                setText(String.format(Locale.US, "%02d", secs));
                if (countDownListener != null) {
                    countDownListener.onTick(secs);
                }
            } else {
                setText(String.format(Locale.US, "%02d:%02d:%02d", hrs, mins, secs));
            }
        }

    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != VISIBLE) {
            stop();
        } else {
            start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void onExpired() {
        setText(expiryMessage);
        setCompoundDrawablePadding(16);
        isTimerExpired = true;
    }

    public void start() {

        if (endTime > System.currentTimeMillis()) {
            start(endTime);
        } else if (endTime > 0) {
            onExpired();
        } else {

        }

    }

    public void start(long endTime) {
        this.endTime = endTime;
        createTimer();

        if (endTime - System.currentTimeMillis() < 24 * 60 * 60 * 1000) {
            timer.start();
        } else {
            setTime(endTime - System.currentTimeMillis());
        }

    }

    public void start(long endTime, boolean showTimeLeft) {
        this.showTimeLeft = showTimeLeft;
        start(endTime);
    }

    public boolean isTimerExpired() {
        return isTimerExpired;
    }

    public void setTimerExpired(boolean timerExpired) {
        isTimerExpired = timerExpired;
    }

    public void stop() {
        if (timer != null)
            timer.cancel();
    }

    public void setFont(Context context) {
        this.setTypeface(FontManager.getTimerFont(context));
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }

    public interface CountDownListener {
        void onTick(long seconds);

        void onFinish();
    }
}
