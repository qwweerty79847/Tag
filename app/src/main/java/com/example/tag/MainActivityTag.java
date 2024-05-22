package com.example.tag;

import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivityTag extends AppCompatActivity {
    private GridLayout gridLayout;
    private final int SIDE = 4;
    private CellTag[][] gameField = new CellTag[SIDE][SIDE];
    private boolean isGameStopped;
    private int count, xEmpty, yEmpty;
    private TextView tvTask;
    private Chronometer chronometer;
    private final int[] numImageArray = new int[]{R.drawable.num1, R.drawable.num2, R.drawable.num3,
            R.drawable.num4, R.drawable.num5, R.drawable.num6, R.drawable.num7,
            R.drawable.num8, R.drawable.num9, R.drawable.num10, R.drawable.num11,
            R.drawable.num12, R.drawable.num13, R.drawable.num14, R.drawable.num15, R.drawable.num0};
    private final ArrayList<Integer> numArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tag);
        initializeTag();
        startGameTag();
    }

    private void initializeTag() {
        gridLayout = findViewById(R.id.grid_tag);
        chronometer = findViewById(R.id.tv_time_tag);
        tvTask = findViewById(R.id.tv_task_tag);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        count = 1;
        int displayWidth = size.x;
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                ImageView imageView = new ImageView(this);
                if (count == 16) {
                    xEmpty = x;
                    yEmpty = y;
                }
                imageView.setImageResource(numImageArray[count - 1]);
                int cellSize = (displayWidth / SIDE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellSize, cellSize);
                imageView.setLayoutParams(params);
                CellTag cell = new CellTag(x, y, count, imageView);
                int finalX = x;
                int finalY = y;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isGameStopped) {
                            if (checkEmptyNeighbor(finalY, finalX)) {

                                gameField[yEmpty][xEmpty].getImageView().setImageResource(numImageArray[gameField[finalY][finalX].getNumber() - 1]);
                                gameField[yEmpty][xEmpty].setNumber(gameField[finalY][finalX].getNumber());

                                gameField[finalY][finalX].getImageView().setImageResource(R.drawable.num0);
                                gameField[finalY][finalX].setNumber(16);

                                yEmpty = finalY;
                                xEmpty = finalX;
                            }
                            if (checkWin()) {
                                win();
                            }
                        }
                    }
                });
                gameField[y][x] = cell;
                numArray.add(count);
                gridLayout.addView(imageView);
                count++;
            }
        }
    }

    private boolean checkEmptyNeighbor(int y, int x) {
        if (x == xEmpty) {
            if (Math.abs((y - yEmpty)) == 1) {
                return true;
            }
        } else if (y == yEmpty) {
            if (Math.abs((x - xEmpty)) == 1) {
                return true;
            }
        }
        return false;
    }

    private void startGameTag() {
        tvTask.setText(getString(R.string.task_tag));
        tvTask.setTextColor(getResources().getColor(R.color.black));
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        isGameStopped = false;
        setField();
    }

    private void setField() {
        Collections.shuffle(numArray);
        int c = 0;
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (numArray.get(c) == 16) {
                    xEmpty = x;
                    yEmpty = y;
                }
                gameField[y][x].setNumber(numArray.get(c));
                gameField[y][x].getImageView().setImageResource(numImageArray[numArray.get(c) - 1]);
                c++;
            }
        }
    }

    private boolean checkWin() {
        int c = 1;
        int check = 0;
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (c == gameField[y][x].getNumber()) {
                    check++;
                }
                c++;
            }
        }
        return check == 16;
    }

    private void win() {
        chronometer.stop();
        isGameStopped = true;
        setImageEnable();
        tvTask.setText(getString(R.string.win_tag));
        tvTask.setTextColor(getResources().getColor(R.color.red));
    }

    private void setImageEnable() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                gameField[j][i].getImageView().setEnabled(true);
            }
        }
    }

    public void onClickRestart(View view) {
        startGameTag();
    }

    public void onClickExit(View view) {
        finish();
    }
}