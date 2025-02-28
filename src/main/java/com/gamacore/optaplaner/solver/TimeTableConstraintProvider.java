package com.gamacore.optaplaner.solver;

import com.gamacore.optaplaner.domain.Session;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class TimeTableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                // Hard constraints
                roomConflict(constraintFactory),
                teacherConflict(constraintFactory),
                studentGroupConflict(constraintFactory),
                // Soft constraints are only implemented in the optaplanner-quickstarts code
        };
    }

    private Constraint roomConflict(ConstraintFactory constraintFactory) {
        // A room can accommodate at most one lesson at the same time.

        // Select a lesson ...
        return constraintFactory
                .forEach(Session.class)
                // ... and pair it with another lesson ...
                .join(Session.class,
                        // ... in the same timeslot ...
                        Joiners.equal(Session::getTimeslot),
                        // ... in the same room ...
                        Joiners.equal(Session::getRoom),
                        // ... and the pair is unique (different id, no reverse pairs) ...
                        Joiners.lessThan(Session::getId))
                // ... then penalize each pair with a hard weight.
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Room conflict");
    }

    private Constraint teacherConflict(ConstraintFactory constraintFactory) {
        // A teacher can teach at most one lesson at the same time.
        return constraintFactory.forEach(Session.class)
                .join(Session.class,
                        Joiners.equal(Session::getTimeslot),
                        Joiners.equal(Session::getTeacher),
                        Joiners.lessThan(Session::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Teacher conflict");
    }

    private Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        // A student can attend at most one lesson at the same time.
        return constraintFactory.forEach(Session.class)
                .join(Session.class,
                        Joiners.equal(Session::getTimeslot),
                        Joiners.equal(Session::getStudentGroup),
                        Joiners.lessThan(Session::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Student group conflict");
    }

}