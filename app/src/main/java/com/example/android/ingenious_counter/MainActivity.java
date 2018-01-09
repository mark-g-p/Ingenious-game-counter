package com.example.android.ingenious_counter;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {


    private ArrayList<Player> players;
    private int active_player;
    private TextView currentlyWinning;
    private TableRow rowTable1;
    private TableRow rowTable2;
    private TableRow rowTable3;
    private TableRow rowTable4;
    private TableRow buttonPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rowTable1 = findViewById(R.id.player1);
        rowTable2 = findViewById(R.id.player2);
        rowTable3 = findViewById(R.id.player3);
        rowTable4 = findViewById(R.id.player4);
        currentlyWinning = findViewById(R.id.winner);
        buttonPanel = findViewById(R.id.button_panel);
        start();
    }

    protected class Player implements Comparable<Player> {

        private String name;
        private ArrayList<Integer> points;
        private TableRow tableRow;

        /**
         * @param pointsTable row from table of points, contains player's name and points.
         */
        private Player(TableRow pointsTable) {

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

        @Override
        public int compareTo(@NonNull Player otherPlayer) {
            return this.getScore() - otherPlayer.getScore();
        }

        TableRow getTableRow() {
            return tableRow;
        }

        String getName() {
            return name;
        }

        int getScore() {
            return points.get(0);
        }

        int getIthPoints(int index) {
            return points.get(index);
        }

        void addPointTo(int index) {
            int currentPoints = points.get(index);
            if (currentPoints < 18) {
                points.set(index, ++currentPoints);
                //Set first element of the points as player's score.
                points.set(0, Collections.min(points.subList(1, points.size())));
                displayPoints(players.get(active_player));
            }
        }
    }

    /**
     * This method resets all points setting them to 0.
     */
    public void start() {

        players = new ArrayList<>(0);


        Player player1 = new Player(rowTable1);
        displayPoints(player1);
        players.add(player1);

        Player player2 = new Player(rowTable2);
        displayPoints(player2);
        players.add(player2);

        Player player3 = new Player(rowTable3);
        displayPoints(player3);
        players.add(player3);

        Player player4 = new Player(rowTable4);
        displayPoints(player4);
        players.add(player4);

        revertColors(players.get(active_player).getTableRow().getChildAt(0));
        active_player = 0;
        setActivePlayer(players.get(active_player).getTableRow().getChildAt(0));
        displayWinner("None");
    }

    /*
    * Fuctions used by buttons*/
    public void addPoint1(View view) {
        players.get(active_player).addPointTo(1);
    }

    public void addPoint2(View view) {
        players.get(active_player).addPointTo(2);
    }

    public void addPoint3(View view) {
        players.get(active_player).addPointTo(3);
    }

    public void addPoint4(View view) {
        players.get(active_player).addPointTo(4);
    }

    public void addPoint5(View view) {
        players.get(active_player).addPointTo(5);
    }

    public void addPoint6(View view) {
        players.get(active_player).addPointTo(6);
    }

    //    Give turn to the next player. Back to first player after one round.
    public void nextPlayer(View view) {
        revertColors(players.get(active_player).getTableRow().getChildAt(0));
        active_player = (active_player + 1) % 4;
        setActivePlayer(players.get(active_player).getTableRow().getChildAt(0));
    }

    // Resets all points 0.
    public void reset(View view) {
        start();
    }

    public void getWinner(View view) {
        String winner = Collections.max(players).getName();
        displayWinner(winner);
    }

    // Displaying functions.
    private void displayWinner(String winner) {

        currentlyWinning.setText(winner);
    }

    //
    private void revertColors(View view) {
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPointsTable));
    }

    private void setActivePlayer(View view) {
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorActive));
    }

    private void displayPoints(Player playersPoints) {

        TableRow playersPointsView = playersPoints.getTableRow();

        int index = playersPointsView.getChildCount();

        for (int i = 1; i < index; i++) {
            TextView pointCell = (TextView) playersPointsView.getChildAt(i);
            int point = playersPoints.getIthPoints(i);

//             Keep original color of TextView by giving it transparent background
            revertColors(pointCell);

//             When player reaches 18 points in one color he gets 'genius'.
//             Change color of TextView when it happens.
            if (point == 18) {

                Button button = (Button) buttonPanel.getChildAt(i);
                ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                pointCell.setBackgroundColor(buttonColor.getColor());
            }
            pointCell.setText(String.valueOf(point));
        }
    }


}
