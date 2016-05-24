package net.yoching.android.collections;

/**
 * Created by marcrisney on 5/23/16.
 */
public class TossedCoins {


    public TossedCoins() {
        // set null or default listener or accept as argument to constructor
        this.listener = null;
    }


    // Assign the listener implementing events interface that will receive the events
    public void setTossedCoinsListener(TossedCoinsListener listener) {
        this.listener = listener;
    }

    public interface TossedCoinsListener {


        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onObjectReady(String title);


        // or when data has been loaded
        public void onDataLoaded(String data);
    }

    private TossedCoinsListener listener;


}
