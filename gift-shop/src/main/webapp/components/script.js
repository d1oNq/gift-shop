function togglePasswordVisibility() {
    const passwordField = document.getElementById('inputPassword');
    const toggleButton = document.querySelector('.toggle-password');
    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        toggleButton.textContent = 'Hide';
    } else {
        passwordField.type = 'password';
        toggleButton.textContent = 'Show';
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const textarea = document.getElementById('areaProductName');

    textarea.addEventListener('input', function() {
        this.style.height = 'auto';
        this.style.height = this.scrollHeight + 'px';
    });
});