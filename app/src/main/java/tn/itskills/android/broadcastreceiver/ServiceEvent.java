package tn.itskills.android.broadcastreceiver;

/**
 * Created by adnenhamdouni on 10/12/2016.
 */

public class ServiceEvent {

    public static class NetworkChangeEvent {

        private boolean isActived;

        public NetworkChangeEvent(){}

        public boolean isActived() {
            return isActived;
        }

        public void setActived(boolean actived) {
            isActived = actived;
        }
    }
}
