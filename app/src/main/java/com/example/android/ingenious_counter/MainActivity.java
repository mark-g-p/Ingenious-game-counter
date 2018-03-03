package com.example.android.ingenious_counter;

import android.databinding.DataBindingUtil;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.ingenious_counter.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {


    private static final String PLAYERS_LIST = "players";
    private static final String ACTIVE_PLAYER = "activePlayer";
    private ArrayList<Player> players;
    private int activePlayer;
    private ArrayList<LinearLayout> rows;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // initialize views
        rows = new ArrayList<>(0);
        rows.add(binding.player1);
        rows.add(binding.player2);

        // restore savedInstance if possible
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
            getWinner(null);
            displayPlayers();
        } else {
            players = new ArrayList<>(0);
//          Add players to the game
            for (int i = 0; i < rows.size(); i++) {
                players.add(new Player(rows.get(i)));
            }
            activePlayer = 0;
            displayWinner("None");
        }

        for (int i = 0; i < rows.size(); i++) {
            displayPoints(i);
        }

        toggleActiveButtons();
        displayScore();
        revertColors(rows.get(activePlayer).getChildAt(0));
        setActivePlayer(rows.get(activePlayer).getChildAt(0));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(PLAYERS_LIST, players);
        savedInstanceState.putInt(ACTIVE_PLAYER, activePlayer);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        players = (ArrayList<Player>) savedInstanceState.getSerializable(PLAYERS_LIST);
        activePlayer = savedInstanceState.getInt(ACTIVE_PLAYER);
    }

    //  Player class definition.
    protected static class Player implements Comparable<Player>, Parcelable {

        private String name;
        private ArrayList<Integer> points;

        /**
         * @param pointsTable row from table of points, contains player's name and points.
         */
        private Player(LinearLayout pointsTable) {

            final int index = pointsTable.getChildCount();


            // Get player name
            TextView nameField = (TextView) pointsTable.getChildAt(0);
            name = nameField.getText().toString();

            // Fill points table with zeroes.
            points = new ArrayList<>(0);
            for (int i = 0; i < index; i++) {
                points.add(0);
            }
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
            }
        }

        //        Part needed for Interface Comparable<Player>
        @Override
        public int compareTo(@NonNull Player otherPlayer) {
            return this.getScore() - otherPlayer.getScore();
        }

        //        Part needed for Interface Parcelable
        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeString(name);
            out.writeSerializable(points);
        }

        public static final Parcelable.Creator<Player> CREATOR
                = new Parcelable.Creator<Player>() {
            public Player createFromParcel(Parcel in) {
                return new Player(in);
            }

            public Player[] newArray(int size) {
                return new Player[size];
            }
        };

        private Player(Parcel in) {
            name = in.readString();
            points = (ArrayList<Integer>) in.readSerializable();
        }


    }

    /* Fuctions used by buttons*/
    public void addPoint1(View view) {
        players.get(activePlayer).addPointTo(1);
        displayPoints(activePlayer);
        displayScore();
    }

    public void addPoint2(View view) {
        players.get(activePlayer).addPointTo(2);
        displayPoints(activePlayer);
        displayScore();
    }

    public void addPoint3(View view) {
        players.get(activePlayer).addPointTo(3);
        displayPoints(activePlayer);
        displayScore();
    }

    public void addPoint4(View view) {
        players.get(activePlayer).addPointTo(4);
        displayPoints(activePlayer);
        displayScore();
    }

    public void addPoint5(View view) {
        players.get(activePlayer).addPointTo(5);
        displayPoints(activePlayer);
        displayScore();
    }

    public void addPoint6(View view) {
        players.get(activePlayer).addPointTo(6);
        displayPoints(activePlayer);
        displayScore();
    }

    //    Give turn to the next player. Back to first player after one round.
    public void nextPlayer(View view) {

        //Disable buttons for non-active player
        revertColors(rows.get(activePlayer).getChildAt(0));
        //Activate buttons for active player
        activePlayer = (activePlayer + 1) % players.size();
        setActivePlayer(rows.get(activePlayer).getChildAt(0));
        toggleActiveButtons();
    }

    // Resets all points 0.
    public void reset(View view) {
        players = new ArrayList<>(0);
        for (int i = 0; i < rows.size(); i++) {
            players.add(new Player(rows.get(i)));
            displayPoints(i);
        }
        revertColors(rows.get(activePlayer).getChildAt(0));
        activePlayer = 0;
        setActivePlayer(rows.get(activePlayer).getChildAt(0));
        displayWinner("None");
        displayScore();
        toggleActiveButtons();
    }

    public void getWinner(View view) {
        String winner = Collections.max(players).getName();
        displayWinner(winner);
    }

    // Displaying functions.
    private void displayPlayers() {
        for (int i = 0; i < rows.size(); i++) {
            LinearLayout playersPointsView = rows.get(i);
            TextView nameCell = (TextView) playersPointsView.getChildAt(0);
            nameCell.setText(players.get(i).getName());
        }
    }

    private void displayWinner(String winner) {

        binding.winner.setText(winner);
    }

    private void displayScore() {
        binding.player1Score.setText(String.valueOf(players.get(0).getScore()));
        binding.player2Score.setText(String.valueOf(players.get(1).getScore()));
    }

    private void displayPoints(int active_player) {

        LinearLayout playersPointsView = rows.get(active_player);
        Player playersPoints = players.get(active_player);
        int index = playersPointsView.getChildCount();

        for (int i = 1; i < index; i++) {
            TextView pointCell = (TextView) playersPointsView.getChildAt(i);
            int point = playersPoints.getIthPoints(i);

//             Keep original color of TextView by giving it transparent background
            revertColors(pointCell);

//             When player reaches 18 points in one color he gets 'ingenious'.
//             Change color of TextView when it happens.
            if (point == 18) {
                switch (i) {
                    case 1:
                        pointCell.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRedStar));
                        break;
                    case 2:
                        pointCell.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreenCircle));
                        break;
                    case 3:
                        pointCell.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueStar));
                        break;
                    case 4:
                        pointCell.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOrangeHexagon));
                        break;
                    case 5:
                        pointCell.setBackgroundColor(ContextCompat.getColor(this, R.color.colorYellowStar));
                        break;
                    case 6:
                        pointCell.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPurpleRing));
                        break;
                    default:
                        break;
                }
            }
            pointCell.setText(String.valueOf(point));

        }
    }

    private void revertColors(View view) {
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPointsTable));
    }

    private void toggleActiveButtons() {
        int pointsLength = rows.get(activePlayer).getChildCount();
        for (int j = 0; j < rows.size(); j++) {
            if (j == activePlayer) {
                for (int i = 1; i < pointsLength; i++) {
                    rows.get(j).getChildAt(i).setEnabled(true);
                }
            } else {
                for (int i = 1; i < pointsLength; i++) {
                    rows.get(j).getChildAt(i).setEnabled(false);
                }
            }
        }
    }

    private void setActivePlayer(View view) {
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorActive));
    }


}
