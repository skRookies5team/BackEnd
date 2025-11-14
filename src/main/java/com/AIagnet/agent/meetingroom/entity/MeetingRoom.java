package com.AIagnet.agent.meetingroom.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")   // DB PK 그대로 매핑
    private Integer id;

    @Column(name = "room_code", length = 50)
    private String roomCode;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "building", length = 100)
    private String building;

    @Column(name = "floor", length = 50)
    private String floor;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "has_video")
    private Boolean hasVideo;

    @Column(name = "created_at")  // DB는 DATETIME → LocalDateTime OK
    private LocalDateTime createdAt;

    @Column(name = "updated_at")  // DB는 TIMESTAMP → LocalDateTime OK
    private LocalDateTime updatedAt;
}
