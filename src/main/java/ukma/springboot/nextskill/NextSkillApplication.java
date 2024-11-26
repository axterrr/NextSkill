package ukma.springboot.nextskill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.PostRepository;
import ukma.springboot.nextskill.repositories.SectionRepository;
import ukma.springboot.nextskill.repositories.UserRepository;

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

    public static void main(String[] args) {
        SpringApplication.run(NextSkillApplication.class, args);
    }

    @Override
    public void run(String... args) {

        UserEntity user = UserEntity.builder()
                .username("username")
                .name("name")
                .surname("surname")
                .email("email")
                .role(UserRole.TEACHER)
                .passwordHash("password")
                .build();

        userRepository.save(user);

        CourseEntity course = CourseEntity.builder()
                .name("name")
                .teacher(user)
                .students(List.of(user))
                .build();

        courseRepository.save(course);

        SectionEntity section = SectionEntity.builder()
                .name("name")
                .course(course)
                .build();

        sectionRepository.save(section);

        PostEntity post = PostEntity.builder()
                .name("name")
                .content("content")
                .section(section)
                .build();

        postRepository.save(post);



        UserEntity userEntity = userRepository.findById(user.getUuid()).orElseThrow();
        UserResponse userResponse = UserMapper.toUserResponse(userEntity);

        CourseEntity courseEntity = courseRepository.findById(course.getUuid()).orElseThrow();
        CourseResponse courseResponse = CourseMapper.toCourseResponse(courseEntity);

        SectionEntity sectionEntity = sectionRepository.findById(section.getUuid()).orElseThrow();
        SectionResponse sectionResponse = SectionMapper.toSectionResponse(sectionEntity);

        PostEntity postEntity = postRepository.findById(post.getUuid()).orElseThrow();
        PostResponse postResponse = PostMapper.toPostResponse(postEntity);

        System.out.println();
    }
}
