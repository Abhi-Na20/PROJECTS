document.getElementById('SignUpForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    let errorMessages = [];
    let username = document.getElementById('username').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    // Validate username
    if (username.length < 3) {
        errorMessages.push('Username must be at least 3 characters long.');
    }

    // Validate email format using regex
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        errorMessages.push('Please enter a valid email address.');
    }

    // Validate password length
    if (password.length < 6) {
        errorMessages.push('Password must be at least 6 characters long.');
    }

    if (errorMessages.length === 0) {
        // If no errors, perform your signup logic and redirect
        const users = JSON.parse(localStorage.getItem("users")) || [];
        if (users.some(user => user.email === email)) {
            alert('Email already exists. Please use a different email.');
            return;
        }
        users.push({ username: username, email: email, password: password });
        localStorage.setItem("users", JSON.stringify(users));
        localStorage.setItem('loggedIn', 'true');
        alert('Signup successful!');
        window.location.href = 'index.html'; // Redirect here
    } else {
        // Display error messages if validation fails
        displayErrors(errorMessages);
    }
});



// ... (keep existing code for validation and saving users) ...

if (errorMessages.length === 0) {
    // If no errors, perform your signup logic and redirect
    const users = JSON.parse(localStorage.getItem("users")) || [];
    if (users.some(user => user.email === email)) {
        alert('Email already exists. Please use a different email.');
        return;
    }
    users.push({ username: username, email: email, password: password });
    localStorage.setItem("users", JSON.stringify(users));

    // *** Set login status and current user ***
    localStorage.setItem('loggedIn', 'true');
    localStorage.setItem('currentUser', username); // Store the username

    alert('Signup successful! You are now logged in.');
    window.location.href = 'index.html'; // Redirect here
} else {
    // Display error messages if validation fails
    displayErrors(errorMessages);
}

// ... (keep displayErrors function) ...





// Function to display error messages
function displayErrors(errors) {
    let errorContainer = document.getElementById('errorMessages');
    if (!errorContainer) {
        errorContainer = document.createElement('div');
        errorContainer.id = 'errorMessages';
        document.body.prepend(errorContainer); // Show errors at the top of the page
    }

    errorContainer.innerHTML = ''; // Clear any existing error messages
    let ul = document.createElement('ul');

    errors.forEach(function(error) {
        let li = document.createElement('li');
        li.textContent = error;
        ul.appendChild(li);
    });

    errorContainer.appendChild(ul);
}