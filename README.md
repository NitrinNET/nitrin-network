# nitrin-network

This is our current network code which we use to power our cloud and server. It uses its own packet implementation.<br>
We use the framework <a href="https://github.com/netty/netty">netty</a> for good performance and simplicity

> ⚠️ **This code is experimental** ⚠️
<br>

## How do I use it?

- [How do I create a packet, register it and handle it?](#packet)
- [ComponentFactory and ComponentHandler](#factory-handler)
- [How do I create a server or client](#server-client)


### <a id="packet">How do I create a packet, register it and handle it?</a>

For outgoing packets you need to implement the WriteablePacket and for incoming packets you need to implement Packet. Only a Packet needs to be registered in order to receive the packet.
We use the already provided ByteBuf from netty to write and read data.

```kotlin
class PingPacket: WriteablePacket(PacketRegistry.DEFAULT + 0), Packet {

    override fun write(buffer: ByteBuf) {}
}
```

When a packet is received the system will try to find a PacketInHandler.

```kotlin
class PingPacketInHandler: PacketInHandler<PingPacket> {

    override fun handle(context: NetworkContext, packet: PingPacket) {
        val (component: NetworkComponent, _: Channel) = context

        component.sendPacket(PongPacket())
    }
}
```

Packets and PacketInHandler must be registered in the PacketRegistry using the code below. WriteablePackets don't need to be registered!

```kotlin
PacketRegistry.register(PingPacket::class, PingPacketInHandler())

PacketRegistry.register(PacketRegistry.DEFAULT + 0) { PingPacket() }
```
<br>

### <a id="factory-handler">How does a component get created?</a>

A component gets created when a channel connects. It is created by using the ComponentFactory. You can either define your own ComponentFactory or use our build-in factory.
It will create a component with no information which just handles the packet sending

```kotlin 
val factory = object : ComponentFactory {
    override fun create(channel: Channel): NetworkComponent {
        return DefaultNetworkComponent(channel)
    }
}
```
<br>

### How does exceptions or a disconnect get handled?

The ComponentHandler listens to disconnects and exceptions, either way the channel is closed before the event is triggered

```kotlin 
val handler = object : ComponentHandler {
    override fun handleDisconnect(context: NetworkContext) {
        TODO("Not yet implemented")
    }

    override fun handleException(context: NetworkContext) {
        TODO("Not yet implemented")
    }
}
```
<br>

### <a id="server-client">How do I create a server?</a>

You will need a SocketAddress and a ComponentHandler in order to create a server, the ComponentFactory is optional. 

```kotlin 
val server = Server(factory, handler)

server.start(InetSocketAddress(port))
```
<br>

### How do I create a client?

You will need a SocketAddress and a ComponentHandler in order to create a client. 

```kotlin 
val client = Client(handler)

client.connect(InetSocketAddress(port))
```


<br><br>
You can find out about our project at https://www.nitrin.net/ or e-mail us your questions info@nitrin.net
