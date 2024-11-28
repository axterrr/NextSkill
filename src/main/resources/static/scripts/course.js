function toggleContent(button) {
    const sectionContent = button.nextElementSibling;
    if (sectionContent.style.display === "none" || sectionContent.style.display === "") {
        sectionContent.style.display = "block";
    } else {
        sectionContent.style.display = "none";
    }
}

function toggleSettings() {
    const sectionContent = document.getElementById('settings-buttons');
    if (sectionContent.style.display === "none" || sectionContent.style.display === "") {
        sectionContent.style.display = "flex";
    } else {
        sectionContent.style.display = "none";
    }
}

function toggleSectionSettings(button) {
    const sectionContent = button.previousElementSibling;
    if (sectionContent.style.display === "none" || sectionContent.style.display === "") {
        sectionContent.style.display = "flex";
    } else {
        sectionContent.style.display = "none";
    }
}
