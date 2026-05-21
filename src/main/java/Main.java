import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("========== TEST CONSULTANT ==========");

        System.out.println("\n--- findConsultantById ---");
        Consultant consultant = dataRetriever.findConsultantById("c-001");
        System.out.println(consultant);

        System.out.println("\n--- findAllConsultants ---");
        List<Consultant> consultants = dataRetriever.findAllConsultants();
        for (Consultant c : consultants) {
            System.out.println(c);
        }

        System.out.println("\n--- findConsultantsByGrade ---");
        List<Consultant> managers = dataRetriever.findConsultantsByGrade(Grade.MANAGER);
        for (Consultant manager : managers) {
            System.out.println(manager);
        }

        System.out.println("\n========== TEST MISSION ==========");

        System.out.println("\n--- findMissionById ---");
        Mission mission = dataRetriever.findMissionById("m-001");
        System.out.println(mission);

        System.out.println("\n--- findAllMissions ---");
        List<Mission> missions = dataRetriever.findAllMissions();
        for (Mission m : missions) {
            System.out.println(m);
        }

        System.out.println("\n--- findMissionsActiveOn ---");
        LocalDate testDate = LocalDate.of(2026, 5, 15);
        List<Mission> activeMissions = dataRetriever.findMissionsActiveOn(testDate);

        System.out.println("Missions actives le " + testDate + " :");
        for (Mission m : activeMissions) {
            System.out.println(m);
        }

        System.out.println("\n========== TEST ASSIGNMENT ==========");

        System.out.println("\n--- findAssignmentByMissionAndConsultant ---");
        Assignment assignment = dataRetriever.findAssignmentByMissionAndConsultant("m-001", "c-001");
        System.out.println(assignment);

        System.out.println("\n--- findAssignmentsByMission ---");
        List<Assignment> assignmentsByMission = dataRetriever.findAssignmentsByMission("m-001");
        for (Assignment a : assignmentsByMission) {
            System.out.println(a);
        }

        System.out.println("\n--- findAssignmentsByConsultant ---");
        List<Assignment> assignmentsByConsultant = dataRetriever.findAssignmentsByConsultant("c-005");
        for (Assignment a : assignmentsByConsultant) {
            System.out.println(a);
        }

        System.out.println("\n--- findAllActiveAssignments ---");
        List<Assignment> activeAssignments = dataRetriever.findAllActiveAssignments();
        for (Assignment a : activeAssignments) {
            System.out.println(a);
        }
    }
}