package com.gamacore.optaplaner.domain;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class TimeTable {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslotList;
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Room> roomList;
    @PlanningEntityCollectionProperty
    private List<Session> sessionList;

    @PlanningScore
    private HardSoftScore score;

    public TimeTable() {
    }

    public TimeTable(List<Timeslot> timeslotList, List<Room> roomList, List<Session> sessionList) {
        this.timeslotList = timeslotList;
        this.roomList = roomList;
        this.sessionList = sessionList;
    }

    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public List<Session> getLessonList() {
        return sessionList;
    }

    public HardSoftScore getScore() {
        return score;
    }

}