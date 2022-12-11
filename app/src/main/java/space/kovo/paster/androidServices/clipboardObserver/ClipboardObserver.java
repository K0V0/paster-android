package space.kovo.paster.androidServices.clipboardObserver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import space.kovo.paster.R;
import space.kovo.paster.activities.mainActivity.MainActivity;
import space.kovo.paster.repositories.item.ItemGlobalVariablesRepository;
import space.kovo.paster.repositories.item.ItemRepository;
import space.kovo.paster.services.clipboardService.ClipboardService;
import space.kovo.paster.services.clipboardService.ClipboardServiceImpl;
import space.kovo.paster.services.itemService.ItemService;
import space.kovo.paster.services.itemService.ItemServiceImpl;
import space.kovo.paster.utils.Logging;

public class ClipboardObserver extends Service {
    private static final String CHANNEL_ID = "2000";
    private static final String CHANNEL_NAME = "pasterNotification";

    private ClipboardObserverBinder binder = new ClipboardObserverBinder();//send to application
    private ItemService itemService;
    private ItemRepository itemRepository;
    private ClipboardService clipboardService;

    public class ClipboardObserverBinder extends Binder {
        public ClipboardObserver getIncomingDataObserver() {
            return ClipboardObserver.this;
        }
    }

    @Override
    public void onCreate() {
        Logging.log("backgroundService: ClipboardObserver", "created");
        this.itemService = new ItemServiceImpl(getApplicationContext());
        this.itemRepository = new ItemGlobalVariablesRepository();
        this.clipboardService = new ClipboardServiceImpl(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification();
        } else {
            startForeground(1, new Notification());
        }
        this.listeners();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logging.log("backgroundService: IncomingDataObserver", "binded");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logging.log("backgroundService: ClipboardObserver", "started");
        this.notification();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopSelf();
        Logging.log("backgroundService: ClipboardObserver", "destroyed");
    }

    @SuppressLint("NewApi")
    private void notification() {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notifyIntent.setAction("close_app");
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        //TODO nastavenia channel ine ako test
        //TODO mozno netreba, od android 10 je aj tak monitorovanie clipboardu na pozadi zakazane
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription(CHANNEL_NAME);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getApplicationContext().getString(R.string.notification_running_title))
                .setContentText(getApplicationContext().getString(R.string.notification_running_text))
                .setSmallIcon(R.drawable.ic_stat_name)
                .addAction(0, getApplicationContext().getString(R.string.notification_button_close_app), pendingIntent)
                .build();
        startForeground(2, notification);
    }

    private void listeners() {
        clipboardService.onAdd(text -> {
            Logging.log("backgroundService: ClipboardObserver",
                    String.format("something copied to clipboard: %s", text));
            if (!itemRepository.existByText(text)) {
                try {
                    itemService.sendItem(text);
                    EventBus.getDefault().post(new ClipboardObserverEvent(text));
                    Logging.log("backgroundService: ClipboardObserver", "clipboard content sent to server");
                } catch (JSONException e) {
                    //throw new RuntimeException(e);
                }
            }
        });
        clipboardService.startListener();
    }
}
