package net.nitrin.network.component

import io.netty.channel.Channel

interface ComponentFactory {

    /**
     * Creates new [NetworkComponent] on first connect
     *
     * @param channel who connected
     */
    fun create(channel: Channel): NetworkComponent
}