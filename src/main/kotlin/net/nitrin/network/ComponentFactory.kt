package net.nitrin.network

import io.netty.channel.Channel

interface ComponentFactory {

    fun create(channel: Channel): NetworkComponent
}