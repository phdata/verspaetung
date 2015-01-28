package com.github.lookout.verspaetung.zk

import groovy.json.JsonSlurper
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import groovy.transform.InheritConstructors
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.cache.ChildData

/**
 * KafkaSpoutTreeWatcher process Zookeeper paths normally associated with Storm
 * KafkaSpout based consumers
 */
@TypeChecked
@InheritConstructors
class KafkaSpoutTreeWatcher extends AbstractConsumerTreeWatcher {
    private static final String ZK_PATH = '/kafka_spout'
    private JsonSlurper json

    KafkaSpoutTreeWatcher(CuratorFramework client, AbstractMap consumersMap) {
        super(client, consumersMap)

        this.json = new JsonSlurper()
    }

    String zookeeperPath() { return ZK_PATH }

    /* skipping type checking since Groovy's JsonSlurper gives us a pretty
     * loose Object to deal with
     */
    @TypeChecked(TypeCheckingMode.SKIP)
    ConsumerOffset processChildData(ChildData nodeData) {
        Object offsetData = json.parseText(new String(nodeData.data, 'UTF-8'))
        /*
        [broker:[host:REDACTED, port:6667], offset:179, partition:7, topic:device_data, topology:[id:01c0d1fc-e956-4b35-9891-dd835488cf45, name:unwrap_topology]]
        */
        ConsumerOffset offset = new ConsumerOffset()
        offset.groupName = offsetData.topology.name
        offset.topic = offsetData.topic
        offset.partition = offsetData.partition
        offset.offset = offsetData.offset

        return offset
    }

    /**
     * We're expecting things to look like:
    *    /kafka_spout/topologyname/partition_0
    */
    Boolean isOffsetSubtree(String path) {
        return (path =~ /\/kafka_spout\/(.*)\/partition_(\d+)/)
    }
}
