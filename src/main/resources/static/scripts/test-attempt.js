document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form#testForm');

    const testUuid = form.getAttribute('data-test-uuid');
    const attemptId = form.getAttribute('data-attempt-id');

    document.querySelectorAll('.question-option').forEach(radio => {
        radio.addEventListener('change', function () {
            const formData = new URLSearchParams();

            form.querySelectorAll('input[type="radio"]:checked').forEach(checkedInput => {
                const questionId = checkedInput.name;
                const selectedOptionId = checkedInput.value;
                formData.append(questionId, selectedOptionId);
            });

            fetch(`/test/${testUuid}/attempt/${attemptId}/save`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData.toString()
            })
                .then(response => {
                    if (!response.ok) {
                        console.error('Failed to save answers');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    });
});
