package ru.job4j.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer implements Runnable {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer buf1 = ByteBuffer.allocate(256);
    private ByteBuffer buf2 = ByteBuffer.allocate(256);
    private ByteBuffer buf3 = ByteBuffer.allocate(256);
    private byte[] bytes = new byte[buf1.capacity()];
    private int counterOfClient = 0;

    public NioServer() throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(8189));
        this.serverSocketChannel.configureBlocking(false);
        this.selector = Selector.open();
        this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        try {
            System.out.println("Сервер запущен (Порт: 8189)");
            Iterator<SelectionKey> iterator;
            SelectionKey key;
            while (this.serverSocketChannel.isOpen()) {
                selector.select();
                iterator = this.selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        this.handleAccept(key);
                    }
                    if (key.isReadable()) {
                        this.handleRead(key);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ,
                String.valueOf(++this.counterOfClient));
        System.out.println("Подключился новый клиент");
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        StringBuilder stringBuilder = new StringBuilder();

        buf1.clear();
        int read = 0;

        while ((read = socketChannel.read(buf1)) > 0) {
            buf1.flip();
            buf1.get(bytes, 0, buf1.remaining());
            stringBuilder.append(new String(bytes, 0, read));
            buf1.clear();
        }

        String msg;

        if (read < 0) {
            msg = key.attachment() + " покинул чат\n";
            socketChannel.close();
        } else {
            msg = key.attachment() + ": " + stringBuilder;
        }

        System.out.println(msg);
    }

    public static void main(String[] args) throws IOException {
        new Thread(new NioServer()).start();
    }
}
