package com.first.hdz.ninelock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NineActivity extends AppCompatActivity implements NineGroupView.OnFinishListener {

    private List<Integer> lock;
    private NineGroupView nineLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine);
        lock = new ArrayList<>();
        nineLock = findViewById(R.id.nine_lock);
        nineLock.setOnFinishListener(this);
    }

    @Override
    public void onFinish(List<Integer> positionSet) {
        if (lock != null && lock.size() > 0) {
            if (positionSet.size() != lock.size()) {
                Toast.makeText(this, "解锁错误！", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < lock.size(); i++) {
                    int lockI = lock.get(i);
                    int unLockI = positionSet.get(i);
                    if (lockI != unLockI) {
                        Toast.makeText(this, "解锁错误！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(this, "解锁正确！", Toast.LENGTH_SHORT).show();
            }
        } else {
            lock.addAll(positionSet);
        }
    }
}
