function visitLink(button) {
    const url = button.getAttribute('data-url');
    window.location.href = url;
}