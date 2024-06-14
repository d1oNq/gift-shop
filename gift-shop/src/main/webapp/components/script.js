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

function validatePhoneInput(input) {
    const phonePattern = /^\+?[0-9]{13}$/;
    if (!phonePattern.test(input.value)) {
        input.setCustomValidity("Невірний формат телефону. Використовуйте лише цифри та символ '+'. Довжина повинна бути 13 символів.");
    } else {
        input.setCustomValidity("");
    }
}
