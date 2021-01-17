package com.example.kafkaproducer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class ProducerTask {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    public void sendVideoMessage(){
        Video video = getVideo();
        sendMessage(video);
    }

    private Video getVideo(){

        Video video =new Video();

        video.setVideoId(String.valueOf(RandomUtil.randomInt(10000,99999)));
        video.setLikeCount(RandomUtil.randomInt(90000,110000));
        video.setType(RandomUtil.randomInt(0,1) == 0? "搞笑":"情感" );
        video.setUid(String.valueOf(RandomUtil.randomInt(10000,99999)));
        video.setPublishTime(DateUtil.date());

        return video;
    }

    private void sendMessage(Video video){

        kafkaTemplate.send("test", JSONUtil.toJsonStr(video)).addCallback(success -> {
            // 消息发送到的topic
            String topic = Objects.requireNonNull(success).getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
        }, failure -> {
            System.out.println("发送消息失败:" + failure.getMessage());
        });
    }


}
