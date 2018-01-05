package com.example.android.ingenious_counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private ArrayList<Player> players;
    private int active_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    private class Player {

        private String name;
        private ArrayList<Integer> points;
        private TableRow tableRow;

        /**
         * @param pointsTable row from table of points, contains player's name and points.
         */
        public Player(TableRow pointsTable) {

            final int index = pointsTable.getChildCount();
            tableRow = pointsTable;

            // Get player name
            TextView nameField = (TextView) pointsTable.getChildAt(0);
            name = nameField.getText().toString();


            // Fill points table with zeroes.
            points = new ArrayList<>(0);
            for (int i = 0; i < index; i++) {
                points.add(0);
            }
        }

        public TableRow getTableRow() {
            return tableRow;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return points.get(0);
        }

        public int getIthPoints(int index) {
            return points.get(index);
        }

        public void addPointTo(int index) {

        }
    }

    /**
     * This method resets all points setting them to 0.
     */
    public void start() {

    }

    /*
    * Fuctions used by buttons*/
    public void addPoint1(View view) {

    }

    public void addPoint2(View view) {
    }

    public void addPoint3(View view) {
    }

    public void addPoint4(View view) {
    }

    public void addPoint5(View view) {
    }

    public void addPoint6(View view) {
    }

    //    Give turn to the next player. Back to first player after one round.
    public void nextPlayer(View view) {
    }

    // Resets all points 0.
    public void reset(View view) {
        start();
    }

    public void getWinner(View view) {
    }

    // Displaying functions.
    private void displayWinner(String winner) {
    }


}
