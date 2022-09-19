package net.nitrin.network.component

import io.netty.channel.Channel
import net.nitrin.network.component.NetworkComponent

interface ComponentFactory {

    /**
     * Creates new [NetworkComponent] on first connect
     *
     * @param channel who connected
     */
    fun create(channel: Channel): NetworkComponent
}