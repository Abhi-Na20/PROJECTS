// Function to get users from localStorage (needed here too)
function getUsers() {
    const users = JSON.parse(localStorage.getItem("users")) || [];
    return users;
}

// Function to display error messages (can be kept as is or improved)
function displayErrors(errors) {
    let errorContainer = document.getElementById('errorMessages');
    if (!errorContainer) {
        // If the element doesn't exist, create it (optional)
        console.error("Error message container not found!");
        alert(errors.join("\n")); // Fallback to alert
        return;
    }
    errorContainer.innerHTML = ''; // Clear previous errors

    if (errors.length > 0) {
        let ul = document.createElement('ul');
        ul.style.listStyle = 'none'; // Optional styling
        ul.style.padding = '0';
        errors.forEach(function(error) {
            let li = document.createElement('li');
            li.textContent = error;
            ul.appendChild(li);
        });
        errorContainer.appendChild(ul);
    }
}


document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    const identifier = document.getElementById('username').value.trim(); // Can be username or email
    const password = document.getElementById('password').value;
    const users = getUsers();
    let errorMessages = [];
    let loginSuccess = false;

    // Basic input validation (optional, but good practice)
    if (!identifier) {
        errorMessages.push('Please enter your username or email.');
    }
    if (!password) {
        errorMessages.push('Please enter your password.');
    }

    if (errorMessages.length === 0) {
        // Find user by username OR email
        const foundUser = users.find(user =>
            (user.username === identifier || user.email === identifier) && user.password === password
        );

        if (foundUser) {
            // *** Login Successful ***
            loginSuccess = true;
            localStorage.setItem('loggedIn', 'true');
            // Store the logged-in user's identifier (e.g., username) if needed elsewhere
            localStorage.setItem('currentUser', foundUser.username);
            alert('Login successful!');
            window.location.href = 'index.html'; // Redirect to homepage
        } else {
            // *** Login Failed ***
            errorMessages.push('Invalid username/email or password.');
        }
    }

    // Display errors if login failed or basic validation failed
    if (!loginSuccess) {
        displayErrors(errorMessages);
    }
});