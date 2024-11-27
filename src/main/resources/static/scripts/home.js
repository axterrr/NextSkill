let allCourses = [];
let currentPage = 0;
const pageSize = 10;

fetch('/api/courses-for-user')
    .then(response => response.json())
    .then(data => {
        allCourses = data;
        displayCourses();
        displayPagination();
    });

function displayCourses() {
    const coursesContainer = document.getElementById('courses-container');
    coursesContainer.innerHTML = '';
    const filteredCourses = getFilteredCourses();
    const paginatedCourses = getPaginatedCourses(filteredCourses);
    const descriptionLimit = 100; // Character limit for description

    paginatedCourses.forEach(course => {
        // Create main container for the card
        const courseCard = document.createElement('div');
        courseCard.className = 'col';

        // Create card element
        const card = document.createElement('div');
        card.className = 'card';
        card.setAttribute('data-teacher-id', course.teacher ? course.teacher.uuid : '');

        // Add image
        const courseImage = document.createElement('img');
        courseImage.className = 'card-img-top';
        courseImage.src = 'https://minfin.com.ua/img/2024/131540542/efca88826f28e64a4ea3c4b334109920.jpeg'; // External link
        courseImage.alt = 'Default Course Image';

        // Create card body
        const cardBody = document.createElement('div');
        cardBody.className = 'card-body';

        // Add title (course name)
        const cardTitle = document.createElement('h5');
        cardTitle.className = 'card-title';

        const courseLink = document.createElement('a');
        courseLink.className = 'text-body';
        courseLink.href = `/course/${course.uuid}`;
        courseLink.textContent = course.name;
        cardTitle.appendChild(courseLink);

        // Add subtitle (teacher)
        const cardSubtitle = document.createElement('h6');
        cardSubtitle.className = 'card-subtitle mb-2 text-body-secondary';
        cardSubtitle.textContent = course.teacher ? `${course.teacher.name} ${course.teacher.surname}` : '';

        // Add course description
        const cardText = document.createElement('p');
        cardText.className = 'card-text';

        // Truncate text if it exceeds the limit
        if (course.description.length > descriptionLimit) {
            const truncatedDescription = truncateText(course.description, descriptionLimit);
            cardText.textContent = truncatedDescription;
        } else {
            cardText.textContent = course.description;
        }

        // Assemble elements in the hierarchy
        cardBody.appendChild(cardTitle);
        cardBody.appendChild(cardSubtitle);
        cardBody.appendChild(cardText);
        card.appendChild(courseImage); // Add image before card body
        card.appendChild(cardBody);
        courseCard.appendChild(card);

        // Add to container
        coursesContainer.appendChild(courseCard);
    });
}

// Function to truncate text
function truncateText(text, limit) {
    const truncated = text.slice(0, limit).trim();
    const lastSpaceIndex = truncated.lastIndexOf(' '); // Find the last space
    if (lastSpaceIndex > 0) {
        return truncated.slice(0, lastSpaceIndex) + '...'; // Truncate to the last word
    }
    return truncated + ' ...'; // If no spaces, just add dots
}

function getFilteredCourses() {
    const searchTerm = document.getElementById('search').value.toLowerCase();
    const dateFilter = document.getElementById('filter-date').value;
    const popularityFilter = document.getElementById('filter-popularity').value;
    let filteredCourses = allCourses.filter(course => {
        return course.name.toLowerCase().includes(searchTerm);
    });

    // Apply date filter
    if (dateFilter) {
        filteredCourses.sort((a, b) => {
            if (dateFilter === 'newest') {
                return new Date(b.createdAt) - new Date(a.createdAt);
            } else if (dateFilter === 'oldest') {
                return new Date(a.createdAt) - new Date(b.createdAt);
            }
        });
    }

    // Apply popularity filter
    if (popularityFilter) {
        filteredCourses.sort((a, b) => {
            if (popularityFilter === 'most') {
                console.log('Sorting by popularity');
                console.log('Course A:', a.name, 'Students:', a.students.length);
                console.log('Course B:', b.name, 'Students:', b.students.length); if (popularityFilter === 'most')
                return b.students.length - a.students.length;
            } else if (popularityFilter === 'least') {
                return a.students.length - b.students.length;
            }
        });
    }

    return filteredCourses;
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

document.getElementById('filter-date').addEventListener('change', () => {
    currentPage = 0;
    displayCourses();
    displayPagination();
});

document.getElementById('filter-popularity').addEventListener('change', () => {
    currentPage = 0;
    displayCourses();
    displayPagination();
});
