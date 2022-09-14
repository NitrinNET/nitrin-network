package net.nitrin.network.packet

import io.netty.buffer.ByteBuf
import net.nitrin.network.NetworkContext
import net.nitrin.network.packet.idle.PingPacket
import net.nitrin.network.packet.idle.PingPacketInHandler
import net.nitrin.network.packet.idle.PongPacket
import kotlin.reflect.KClass

object PacketRegistry {

    const val DEFAULT: Int = 1000

    private val packets: HashMap<Int, (ByteBuf) -> Packet> = hashMapOf()
    private val packetHandler: HashMap<KClass<out Packet>, PacketInHandler<out Packet>> = hashMapOf()

    init {
        register(PingPacket::class, PingPacketInHandler())

        register(PacketRegistry.DEFAULT + 0) { PingPacket() }
        register(PacketRegistry.DEFAULT + 1) { PongPacket() }
    }

    fun register(id: Int, function: (ByteBuf) -> Packet) {
        packets[id] = function
    }

    fun  createPacket(id: Int, buffer: ByteBuf): Packet? {
        val function = packets[id] ?: return null
        return function(buffer)
    }

    fun <T: Packet> register(clazz: KClass<T>, packetInHandler: PacketInHandler<T>) {
        packetHandler[clazz] = packetInHandler
    }

    fun <T: Packet> handle(context: NetworkContext, packet: T) {
        if (packetHandler.containsKey(packet::class)) {
            @Suppress("UNCHECKED_CAST")
            val packetInHandler: PacketInHandler<T> = packetHandler[packet::class] as PacketInHandler<T>

            packetInHandler.handle(context, packet)
        }
    }
}