package space.kovo.paster.services.connectivityService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectivityServiceImpl implements ConnectivityService {

    private static final List<Integer> connectionsWithNet = new ArrayList<Integer>() {{
        add(ConnectivityManager.TYPE_MOBILE);
        add(ConnectivityManager.TYPE_ETHERNET);
        add(ConnectivityManager.TYPE_MOBILE_DUN);
        add(ConnectivityManager.TYPE_WIFI);
        add(ConnectivityManager.TYPE_WIMAX);
    }};

    private final Context context;

    public ConnectivityServiceImpl(Context context) {
        this.context = context;
    }

    public boolean isConnectedToNetwork() {
        return Optional.ofNullable((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .map(ConnectivityManager::getActiveNetworkInfo)
                .map(NetworkInfo::getType)
                .map(connectionsWithNet::contains)
                .orElse(false);
    }

    //TODO cannot run on main thread
    public boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(sockaddr, timeoutMs);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
