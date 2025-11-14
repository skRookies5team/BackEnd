package com.AIagnet.agent.meetingroom.repository;

import com.AIagnet.agent.meetingroom.entity.MeetingRoom;
import com.AIagnet.agent.meetingroom.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {

    Optional<MeetingRoom> findByRoomCode(String roomCode);

    List<MeetingRoom> findByBuilding(String building);

    List<MeetingRoom> findByBuildingAndFloor(String building, String floor);

    List<MeetingRoom> findByCapacityGreaterThanEqual(Integer capacity);

    List<MeetingRoom> findByHasVideo(Boolean hasVideo);

    List<MeetingRoom> findByBuildingAndCapacityGreaterThanEqualAndHasVideo(
            String building,
            Integer capacity,
            Boolean hasVideo
    );

    @Query("""
            SELECT mr FROM MeetingRoom mr
            WHERE mr.capacity >= :capacity
              AND (:requireVideo = false OR mr.hasVideo = true)
              AND (:building IS NULL OR mr.building = :building)
              AND NOT EXISTS (
                  SELECT 1 FROM Reservation r
                  WHERE r.meetingRoom = mr
                    AND r.status = 'CONFIRMED'
                    AND r.startTime < :endTime
                    AND r.endTime > :startTime
              )
            ORDER BY mr.capacity ASC, mr.hasVideo DESC
            """)
    List<MeetingRoom> findAvailableRooms(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("capacity") Integer capacity,
            @Param("requireVideo") Boolean requireVideo,
            @Param("building") String building
    );

    List<MeetingRoom> findByNameContaining(String name);
}