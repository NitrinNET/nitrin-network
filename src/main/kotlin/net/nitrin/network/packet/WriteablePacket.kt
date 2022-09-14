package net.nitrin.network.packet

import io.netty.buffer.ByteBuf

abstract class WriteablePacket(val id: Int) {

    abstract fun write(buffer: ByteBuf)
}