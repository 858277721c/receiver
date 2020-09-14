package com.sd.lib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;

public abstract class BaseBroadcastReceiver extends BroadcastReceiver
{
    private boolean mHasRegister;

    /**
     * 是否已经注册
     *
     * @return
     */
    public boolean hasRegister()
    {
        return mHasRegister;
    }

    /**
     * 注册广播
     *
     * @param context
     */
    public final void register(Context context)
    {
        if (mHasRegister)
            return;

        if (registerImpl(context))
            mHasRegister = true;
    }

    /**
     * 取消注册广播
     *
     * @param context
     */
    public final void unregister(Context context)
    {
        if (mHasRegister)
        {
            unregisterImpl(context);
            mHasRegister = false;
        }
    }

    protected abstract boolean registerImpl(Context context);

    protected void unregisterImpl(Context context)
    {
        context.unregisterReceiver(this);
    }
}
