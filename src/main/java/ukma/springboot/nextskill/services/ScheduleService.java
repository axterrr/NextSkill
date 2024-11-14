package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.utils.CSVUtility;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Value("${statistics.save-dir}")
    private String saveDir;

    private UserService userService;
    private CSVUtility csvUtility;

    @Scheduled(cron = "0 59 23 * * SUN", zone = "Europe/Kyiv")
    void collectWeeklyStats() {
        collectUsers();
    }

    private void collectUsers() {
        List<User> users = userService.getAllUsersCreatedAfter(LocalDateTime.now().minusDays(7));
        csvUtility.saveTo(saveDir,"registered_users", users);
    }

    @Autowired
    public void setCsvUtility(CSVUtility csvUtility) {
        this.csvUtility = csvUtility;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
