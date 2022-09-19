package net.nitrin.network.component

import net.nitrin.network.NetworkContext

interface ComponentListener {

    /**
     * Fired when [NetworkComponent] disconnects
     */
    fun disconnect(context: NetworkContext)

    /**
     * Fired when [NetworkComponent] throws an exception
     */
    fun exception(context: NetworkContext)
}