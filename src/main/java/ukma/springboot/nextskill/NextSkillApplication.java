package ukma.springboot.nextskill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.PostResponse;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.CourseView;
import ukma.springboot.nextskill.models.views.PostView;
import ukma.springboot.nextskill.models.views.SectionView;
import ukma.springboot.nextskill.models.views.UserView;
import ukma.springboot.nextskill.repositories.*;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.PostService;
import ukma.springboot.nextskill.services.SectionService;
import ukma.springboot.nextskill.services.UserService;

@SpringBootApplication
public class NextSkillApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private PostService postService;
    @Autowired
    private TestRepository testRepository;

    public static void main(String[] args) {
        SpringApplication.run(NextSkillApplication.class, args);
    }

    @Override
    public void run(String... args) {

        UserView teacher = UserView.builder()
                .username("teacher")
                .name("Oleksander")
                .surname("Oleksiy")
                .email("email@teacher")
                .role(UserRole.TEACHER)
                .password("Teacher1")
                .confirmPassword("Teacher1")
                .build();

        UserView admin = UserView.builder()
                .username("admin")
                .name("name")
                .surname("surname")
                .email("email@admin")
                .role(UserRole.ADMIN)
                .password("Admin123")
                .confirmPassword("Admin123")
                .build();

        UserView student = UserView.builder()
                .username("student")
                .name("Grygorii")
                .surname("surname")
                .email("email@student")
                .role(UserRole.STUDENT)
                .password("Student1")
                .confirmPassword("Student1")
                .build();

        UserView student2 = UserView.builder()
                .username("student2")
                .name("Grygori2")
                .surname("surname")
                .email("email_2@student")
                .role(UserRole.STUDENT)
                .password("Student2")
                .confirmPassword("Student2")
                .build();

        UserView newTeacher = UserView.builder()
                .username("newteacher")
                .name("Natalia")
                .surname("Kovalenko")
                .email("email@newTeacher")
                .role(UserRole.TEACHER)
                .password("NewTeacher1")
                .confirmPassword("NewTeacher1")
                .build();


        UserResponse createdTeacher = userService.create(teacher);
        UserResponse createdStudent = userService.create(student);
        UserResponse createdStudent2 = userService.create(student2);
        UserResponse createdAdmin = userService.create(admin);
        UserResponse createdNewTeacher =  userService.create(newTeacher);


        CourseView course1 = CourseView.builder()
                .name("Web Development")
                .description("Learn how to build single-page applications!")
                .teacherId(createdTeacher.getUuid())
                .build();

        CourseView course2 = CourseView.builder()
                .name("Data Structures and Algorithms")
                .description("Master the fundamentals of algorithms and data structures.")
                .teacherId(createdTeacher.getUuid())
                .build();

        CourseView course3 = CourseView.builder()
                .name("Introduction to AI")
                .description("Learn the basics of Artificial Intelligence and its applications.")
                .teacherId(createdNewTeacher.getUuid())
                .build();

        CourseResponse createdCourse1 = courseService.create(course1);
        CourseResponse createdCourse2 = courseService.create(course2);
        CourseResponse createdCourse3 = courseService.create(course3);

        courseService.enrollStudent(createdCourse1.getUuid(), createdStudent.getUuid());
        courseService.enrollStudent(createdCourse1.getUuid(), createdStudent2.getUuid());
        courseService.enrollStudent(createdCourse2.getUuid(), createdStudent.getUuid());
        courseService.enrollStudent(createdCourse3.getUuid(), createdStudent.getUuid());

        SectionView section1 = SectionView.builder()
                .name("name")
                .courseId(createdCourse1.getUuid())
                .build();

        SectionView section2 = SectionView.builder()
                .name("Basics")
                .courseId(createdCourse2.getUuid())
                .build();

        SectionView section3 = SectionView.builder()
                .name("Introduction")
                .courseId(createdCourse3.getUuid())
                .build();

        SectionResponse createdSection1 = sectionService.create(section1);
        SectionResponse createdSection2 = sectionService.create(section2);
        SectionResponse createdSection3 = sectionService.create(section3);

        PostView post1 = PostView.builder()
                .name("name")
                .content("content")
                .sectionId(createdSection1.getUuid())
                .build();

        PostView post2 = PostView.builder()
                .name("Algorithm Basics")
                .content("Let's discuss the fundamentals of algorithms.")
                .sectionId(createdSection2.getUuid())
                .build();

        PostView post3 = PostView.builder()
                .name("What is AI?")
                .content("An introductory post about Artificial Intelligence.")
                .sectionId(createdSection3.getUuid())
                .build();

        PostResponse createdPost1 = postService.create(post1);
        PostResponse createdPost2 = postService.create(post2);
        PostResponse createdPost3 = postService.create(post3);

        /*UserView userView = UserView.builder()
                .uuid(createdTeacher.getUuid())
                .name("teacher 2.0")
                .build();
        userService.update(userView);

        CourseView courseView = CourseView.builder()
                .uuid(createdCourse1.getUuid())
                .name("course 2.0")
                .build();
        courseService.update(courseView);

        SectionView sectionView = SectionView.builder()
                .uuid(createdSection1.getUuid())
                .name("section 2.0")
                .build();
        sectionService.update(sectionView);

        PostView postView = PostView.builder()
                .uuid(createdPost1.getUuid())
                .name("post 2.0")
                .build();
        postService.update(postView);*/

//        TestEntity test = TestEntity.builder()
//            .name("Testing test")
//            .description("Complete this test!")
//            .section(section)
//            .build();
//
//        testRepository.save(test);

        // для дебагу щоб поставити брейкпойнт
        System.out.println();
    }
}
