package com.bjfu.mcs.utils.network;

/**
 * 描述:
 */

public interface NetChangeObserver {

    void onConnect(NetworkUtils.NetworkType type);

    void onDisConnect();

}
