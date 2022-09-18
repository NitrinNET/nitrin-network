package net.nitrin.network

import io.netty.channel.Channel

/**
 * Used to supply handler with [NetworkComponent] and [Channel]
 */
data class NetworkContext(val component: NetworkComponent, val channel: Channel)