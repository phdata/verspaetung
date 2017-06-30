package com.github.reiseburo.verspaetung.zk

/**
 * POJO representing data from Zookeeper for a consumer, topic and offset
 */
class ConsumerOffset {
    String topic
    String groupName
    Long offset
    Integer partition

    ConsumerOffset() {
    }

    ConsumerOffset(String topic, Integer partition, Long offset) {
        this.topic = topic
        this.partition = partition
        this.offset = offset
    }

    String toString() {
        return "ConsumerOffset[${hashCode()}] ${topic}:${partition} ${groupName} is at ${offset}".toString()
    }
}

