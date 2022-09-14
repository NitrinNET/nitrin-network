package net.nitrin.network

import io.netty.channel.Channel

data class NetworkContext(val component: NetworkComponent, val channel: Channel)