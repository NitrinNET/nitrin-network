package net.nitrin.network.packet

import java.net.SocketAddress

class PacketNotFoundException(address: SocketAddress, id: Int): RuntimeException("Packet from $address with id $id could not be found")