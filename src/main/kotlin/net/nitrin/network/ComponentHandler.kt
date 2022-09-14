package net.nitrin.network

interface ComponentHandler {

    fun handleDisconnect(context: NetworkContext)

    fun handleException(context: NetworkContext)
}