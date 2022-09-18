package net.nitrin.network

interface ComponentHandler {

    /**
     * Fired when [NetworkComponent] disconnects
     */
    fun handleDisconnect(context: NetworkContext)

    /**
     * Fired when [NetworkComponent] throws an exception
     */
    fun handleException(context: NetworkContext)
}