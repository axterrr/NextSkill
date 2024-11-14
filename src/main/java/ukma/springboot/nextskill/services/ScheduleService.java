package ukma.springboot.nextskill.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.utils.CSVUtility;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private static final Marker marker = MarkerFactory.getMarker("SCHEDULE");

    @Value("${statistics.save-stats-dir}")
    private String saveStatsDir;

    @Value("${backups.save-db-backup-dir}")
    private String saveBackupDir;

    @Value("${spring.application.name}")
    private String appName;

    private UserService userService;
    private CSVUtility csvUtility;
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 59 23 * * SUN", zone = "Europe/Kyiv")
    void collectWeeklyStats() {
        collectUsers();
        //TODO: Collect other data
    }

    @Scheduled(fixedDelay = 259200000)
    void doDatabaseBackup() {
        String filepath = saveBackupDir + File.separator + appName.toUpperCase() + "_" + LocalDateTime.now() + ".sql";
        String query = "SCRIPT TO '" + filepath + "'";
        jdbcTemplate.execute(query);
        logger.info(marker, "The job to do a database backup was called, expected path: {}", filepath);
    }

    private void collectUsers() {
        List<User> users = userService.getAllUsersCreatedAfter(LocalDateTime.now().minusDays(7));
        csvUtility.saveTo(saveStatsDir,"registered_users", users);
    }

    @Autowired
    public void setCsvUtility(CSVUtility csvUtility) {
        this.csvUtility = csvUtility;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
