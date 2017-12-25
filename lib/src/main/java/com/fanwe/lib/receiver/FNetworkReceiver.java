package com.fanwe.lib.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络变化监听
 * <p>
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
public abstract class FNetworkReceiver extends FBroadcastReceiver
{
    public final static String FANWE_ANDROID_NET_CHANGE_ACTION = "com.fanwe.android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action) || FANWE_ANDROID_NET_CHANGE_ACTION.equals(action))
        {
            final int type = getNetworkType(context);
            onNetworkChanged(type);
        }
    }

    /**
     * 网络变化监听
     *
     * @param type {@link ConnectivityManager}
     */
    protected abstract void onNetworkChanged(int type);

    @Override
    public void register(Context context)
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(FANWE_ANDROID_NET_CHANGE_ACTION);
        context.registerReceiver(this, filter);
    }

    /**
     * 发送网络检测广播
     *
     * @param context
     */
    public static void sendBroadcast(Context context)
    {
        Intent intent = new Intent();
        intent.setAction(FANWE_ANDROID_NET_CHANGE_ACTION);
        context.sendBroadcast(intent);
    }

    //----------utils----------

    /**
     * 获得ConnectivityManager对象
     *
     * @param context
     * @return
     */
    public static ConnectivityManager getConnectivityManager(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager;
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager manager = getConnectivityManager(context);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null)
        {
            return info.isConnected();
        } else
        {
            return false;
        }
    }

    /**
     * wifi是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager manager = getConnectivityManager(context);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null)
        {
            return false;
        } else
        {
            return ConnectivityManager.TYPE_WIFI == networkInfo.getType();
        }
    }

    /**
     * 获得网络类型
     *
     * @param context
     * @return {@link ConnectivityManager}
     */
    public static int getNetworkType(Context context)
    {
        ConnectivityManager manager = getConnectivityManager(context);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null)
        {
            return -1;
        } else
        {
            int type = networkInfo.getType();
            return type;
        }
    }

    public interface SDNetworkCallback
    {
        /**
         * 网络变化监听
         *
         * @param type {@link ConnectivityManager}
         */
        void onNetworkChanged(int type);
    }

}
