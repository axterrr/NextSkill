function setCorrectOption(radio) {
    const optionId = radio.value;
    fetch(`/option/${optionId}/set-correct`, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            alert('Correct option updated successfully!');
        } else {
            alert('Failed to update the correct option.');
        }
    });
}