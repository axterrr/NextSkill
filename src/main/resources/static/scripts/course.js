function toggleContent(button) {
    const sectionContent = button.nextElementSibling;
    if (sectionContent.style.display === "none" || sectionContent.style.display === "") {
        sectionContent.style.display = "block";
    } else {
        sectionContent.style.display = "none";
    }
}
