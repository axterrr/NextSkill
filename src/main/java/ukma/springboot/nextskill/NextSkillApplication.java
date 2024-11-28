package ukma.springboot.nextskill;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.*;
import ukma.springboot.nextskill.models.views.*;
import ukma.springboot.nextskill.services.*;

@SpringBootApplication
@AllArgsConstructor
public class NextSkillApplication implements CommandLineRunner {

    private UserService userService;
    private CourseService courseService;
    private SectionService sectionService;
    private PostService postService;
    private TestService testService;
    private QuestionService questionService;
    private QuestionOptionService optionService;

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
                .surname("surname1")
                .email("email@admin")
                .role(UserRole.ADMIN)
                .password("Admin123")
                .confirmPassword("Admin123")
                .build();

        UserView student = UserView.builder()
                .username("student")
                .name("Grygorii")
                .surname("surname2")
                .email("email@student")
                .role(UserRole.STUDENT)
                .password("Student1")
                .confirmPassword("Student1")
                .build();

        UserView student2 = UserView.builder()
                .username("student2")
                .name("Grygori2")
                .surname("surname3")
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

        CourseView course4 = CourseView.builder()
                .name("Introduction")
                .description("Learn the basics of Artificial Intelligence and its applications.")
                .teacherId(createdNewTeacher.getUuid())
                .build();

        CourseResponse createdCourse1 = courseService.create(course1);
        CourseResponse createdCourse2 = courseService.create(course2);
        CourseResponse createdCourse3 = courseService.create(course3);
        CourseResponse createdCourse4 = courseService.create(course4);

        courseService.enrollStudent(createdCourse1.getUuid(), createdStudent.getUuid());
        courseService.enrollStudent(createdCourse1.getUuid(), createdAdmin.getUuid());
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

        TestView test = TestView.builder()
                .name("Testing Test")
                .description("Pass it if you can!")
                .sectionId(createdSection1.getUuid())
                .build();

        TestResponse createdTest = testService.create(test);

        QuestionView question = QuestionView.builder()
                .questionText("How do you feel?")
                .testId(createdTest.getUuid())
                .build();

        QuestionResponse createdQuestion = questionService.create(question);

        QuestionOptionView option1 = QuestionOptionView.builder()
                .isCorrect(true)
                .optionText("Good")
                .questionId(createdQuestion.getId())
                .build();

        QuestionOptionView option2 = QuestionOptionView.builder()
                .isCorrect(false)
                .optionText("Bad")
                .questionId(createdQuestion.getId())
                .build();

        optionService.create(option1);
        optionService.create(option2);

        QuestionView question2 = QuestionView.builder()
                .questionText("Which animal do you like?")
                .testId(createdTest.getUuid())
                .build();

        QuestionResponse createdQuestion2 = questionService.create(question2);

        QuestionOptionView option21 = QuestionOptionView.builder()
                .isCorrect(true)
                .optionText("Cats")
                .questionId(createdQuestion2.getId())
                .build();

        QuestionOptionView option22 = QuestionOptionView.builder()
                .isCorrect(false)
                .optionText("Dogs")
                .questionId(createdQuestion2.getId())
                .build();

        QuestionOptionView option23 = QuestionOptionView.builder()
                .isCorrect(false)
                .optionText("Lions")
                .questionId(createdQuestion2.getId())
                .build();

        QuestionOptionView option24 = QuestionOptionView.builder()
                .isCorrect(false)
                .optionText("Capybara")
                .questionId(createdQuestion2.getId())
                .build();

        optionService.create(option21);
        optionService.create(option22);
        optionService.create(option23);
        optionService.create(option24);

        postService.create(post1);
        postService.create(post2);
        postService.create(post3);

        // для дебагу щоб поставити брейкпойнт
        System.out.println();
    }
}
