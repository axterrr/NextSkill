let allCourses = [];
let currentPage = 0;
const pageSize = 1;

fetch('/api/courses-for-student')
    .then(response => response.json())
    .then(data => {
        allCourses = data;
        updateTeacherFilter();
        displayCourses();
        displayPagination();
    });

function updateTeacherFilter() {
    const teacherSelect = document.getElementById('filter-teacher');
    const teachers = [...new Set(allCourses.map(course => course.teacher.uuid))];
    teachers.forEach(teacherId => {
        const teacher = allCourses.find(course => course.teacher.uuid === teacherId).teacher;
        const option = document.createElement('option');
        option.value = teacher.uuid;
        option.textContent = teacher.name + ' ' + teacher.surname;
        teacherSelect.appendChild(option);
    });
}

function displayCourses() {
    const coursesContainer = document.getElementById('courses-container');
    coursesContainer.innerHTML = '';
    const filteredCourses = getFilteredCourses();
    const paginatedCourses = getPaginatedCourses(filteredCourses);
    paginatedCourses.forEach(course => {
        // Створення основного контейнера для картки
        const courseCard = document.createElement('div');
        courseCard.className = 'col';

        // Створення елемента картки
        const card = document.createElement('div');
        card.className = 'card';
        card.setAttribute('data-teacher-id', course.teacher ? course.teacher.uuid : '');

        // Створення тіла картки
        const cardBody = document.createElement('div');
        cardBody.className = 'card-body';

        // Додавання заголовка (назви курсу)
        const cardTitle = document.createElement('h5');
        cardTitle.className = 'card-title';

        const courseLink = document.createElement('a');
        courseLink.className = 'text-body';
        courseLink.href = `/course/${course.uuid}`;
        courseLink.textContent = course.name;
        cardTitle.appendChild(courseLink);

        // Додавання підзаголовка (викладача)
        const cardSubtitle = document.createElement('h6');
        cardSubtitle.className = 'card-subtitle mb-2 text-body-secondary';
        cardSubtitle.textContent = course.teacher ? `${course.teacher.name} ${course.teacher.surname}` : '';

        // Додавання опису курсу
        const cardText = document.createElement('p');
        cardText.className = 'card-text';
        cardText.textContent = course.description;

        // Збирання елементів в ієрархію
        cardBody.appendChild(cardTitle);
        cardBody.appendChild(cardSubtitle);
        cardBody.appendChild(cardText);
        card.appendChild(cardBody);
        courseCard.appendChild(card);

        // Додавання до контейнера
        coursesContainer.appendChild(courseCard);
    });
}

function getFilteredCourses() {
    const searchTerm = document.getElementById('search').value.toLowerCase();
    const teacherId = document.getElementById('filter-teacher').value;
    return allCourses.filter(course => {
        const matchesSearch = course.name.toLowerCase().includes(searchTerm);
        const matchesTeacher = !teacherId || (course.teacher && course.teacher.uuid === teacherId);
        return matchesSearch && matchesTeacher;
    });
}

function getPaginatedCourses(courses) {
    const start = currentPage * pageSize;
    return courses.slice(start, start + pageSize);
}

function displayPagination() {
    const pagination = document.getElementById('pagination');
    const filteredCourses = getFilteredCourses();
    const totalPages = Math.ceil(filteredCourses.length / pageSize);
    pagination.innerHTML = '';
    for (let i = 0; i < totalPages; i++) {
        const pageItem = document.createElement('li');
        pageItem.className = 'page-item ' + (i === currentPage ? 'active' : '');
        const pageLink = document.createElement('a');
        pageLink.className = 'page-link';
        pageLink.textContent = i + 1;
        pageLink.addEventListener('click', () => {
            currentPage = i;
            displayCourses();
            displayPagination();
        });
        pageItem.appendChild(pageLink);
        pagination.appendChild(pageItem);
    }
}

document.getElementById('search').addEventListener('input', () => {
    currentPage = 0;
    displayCourses();
    displayPagination();
});

document.getElementById('filter-teacher').addEventListener('change', () => {
    currentPage = 0;
    displayCourses();
    displayPagination();
});
