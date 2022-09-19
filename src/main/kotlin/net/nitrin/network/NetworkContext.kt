package net.nitrin.network

import io.netty.channel.Channel
import net.nitrin.network.component.NetworkComponent

/**
 * Used to supply handler with [NetworkComponent] and [Channel]
 */
data class NetworkContext(val component: NetworkComponent, val channel: Channel)