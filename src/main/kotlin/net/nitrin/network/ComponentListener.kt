package net.nitrin.network

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