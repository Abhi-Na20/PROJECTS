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