package ukma.springboot.nextskill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.PostEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.mappers.CourseMapper;
import ukma.springboot.nextskill.models.mappers.PostMapper;
import ukma.springboot.nextskill.models.mappers.SectionMapper;
import ukma.springboot.nextskill.models.mappers.UserMapper;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.PostResponse;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.CourseView;
import ukma.springboot.nextskill.models.views.PostView;
import ukma.springboot.nextskill.models.views.SectionView;
import ukma.springboot.nextskill.models.views.UserView;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.PostRepository;
import ukma.springboot.nextskill.repositories.SectionRepository;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.PostService;
import ukma.springboot.nextskill.services.SectionService;
import ukma.springboot.nextskill.services.UserService;

import java.util.List;

@SpringBootApplication
public class NextSkillApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private PostService postService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(NextSkillApplication.class, args);
    }

    @Override
    public void run(String... args) {

        UserEntity teacher = UserEntity.builder()
                .username("teacher")
                .name("Oleksander")
                .surname("Olesiy")
                .email("email.teacher")
                .role(UserRole.TEACHER)
                .passwordHash(passwordEncoder.encode("teacher"))
                .build();

        UserEntity admin = UserEntity.builder()
                .username("admin")
                .name("name")
                .surname("surname")
                .email("email.admin")
                .role(UserRole.ADMIN)
                .passwordHash(passwordEncoder.encode("admin"))
                .build();

        UserEntity student = UserEntity.builder()
                .username("student")
                .name("Grygorii")
                .surname("surname")
                .email("email.student")
                .role(UserRole.STUDENT)
                .passwordHash(passwordEncoder.encode("student"))
                .build();

        UserEntity newTeacher = UserEntity.builder()
                .username("newTeacher")
                .name("Natalia")
                .surname("Kovalenko")
                .email("email.newTeacher")
                .role(UserRole.TEACHER)
                .passwordHash(passwordEncoder.encode("newTeacher"))
                .build();

        userRepository.save(teacher);
        userRepository.save(student);
        userRepository.save(admin);
        userRepository.save(newTeacher);


        CourseEntity course = CourseEntity.builder()
                .name("Web Development")
                .description("Learn how to build single-page applications!")
                .teacher(teacher)
                .students(List.of(student))
                .build();

        CourseEntity course2 = CourseEntity.builder()
                .name("Data Structures and Algorithms")
                .description("Master the fundamentals of algorithms and data structures.")
                .teacher(teacher)
                .students(List.of(student))
                .build();

        CourseEntity course3 = CourseEntity.builder()
                .name("Introduction to AI")
                .description("Learn the basics of Artificial Intelligence and its applications.")
                .teacher(newTeacher)
                .students(List.of(student))
                .build();

        courseRepository.save(course);
        courseRepository.save(course2);
        courseRepository.save(course3);

        SectionEntity section = SectionEntity.builder()
                .name("name")
                .course(course)
                .build();

        SectionEntity section2 = SectionEntity.builder()
                .name("Basics")
                .course(course2)
                .build();

        SectionEntity section3 = SectionEntity.builder()
                .name("Introduction")
                .course(course3)
                .build();

        sectionRepository.save(section);
        sectionRepository.save(section2);
        sectionRepository.save(section3);

        PostEntity post = PostEntity.builder()
                .name("name")
                .content("content")
                .section(section)
                .build();

        PostEntity post2 = PostEntity.builder()
                .name("Algorithm Basics")
                .content("Let's discuss the fundamentals of algorithms.")
                .section(section2)
                .build();

        PostEntity post3 = PostEntity.builder()
                .name("What is AI?")
                .content("An introductory post about Artificial Intelligence.")
                .section(section3)
                .build();

        postRepository.save(post);
        postRepository.save(post2);
        postRepository.save(post3);


        UserEntity userEntity = userRepository.findById(student.getUuid()).orElseThrow();
        UserResponse userResponse = UserMapper.toUserResponse(userEntity);

        CourseEntity courseEntity = courseRepository.findById(course.getUuid()).orElseThrow();
        CourseResponse courseResponse = CourseMapper.toCourseResponse(courseEntity);

        SectionEntity sectionEntity = sectionRepository.findById(section.getUuid()).orElseThrow();
        SectionResponse sectionResponse = SectionMapper.toSectionResponse(sectionEntity);

        PostEntity postEntity = postRepository.findById(post.getUuid()).orElseThrow();
        PostResponse postResponse = PostMapper.toPostResponse(postEntity);



        UserView userView = new UserView();
        userView.setUuid(teacher.getUuid());
        userView.setName("teacher 2.0");
        userService.update(userView);

        CourseView courseView = new CourseView();
        courseView.setUuid(course.getUuid());
        courseView.setName("course 2.0");
        courseService.update(courseView);

        SectionView sectionView = new SectionView();
        sectionView.setUuid(section.getUuid());
        sectionView.setName("section 2.0");
        sectionService.update(sectionView);

        PostView postView = new PostView();
        postView.setUuid(post.getUuid());
        postView.setName("post 2.0");
        postService.update(postView);

        System.out.println();
    }
}
