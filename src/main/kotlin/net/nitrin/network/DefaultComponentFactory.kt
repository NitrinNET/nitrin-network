package net.nitrin.network

import io.netty.channel.Channel

class DefaultComponentFactory: ComponentFactory {

    override fun create(channel: Channel): NetworkComponent {
        return DefaultNetworkComponent(channel)
    }
}