

function getUsers() {
    const users = JSON.parse(localStorage.getItem("users")) || [];
    return users;
}

function saveUsers(users) {
    localStorage.setItem("users", JSON.stringify(users));
}


function displayErrors(errors) {}
    /*
    if (!targetDiv) return; // Exit if the error div isn't available

    targetDiv.innerHTML = ''; // Clear previous errors
    if (errors && errors.length > 0) {
        let ul = document.createElement('ul');
        ul.style.listStyle = 'none';
        ul.style.padding = '0';
        ul.style.color = 'red'; // Ensure errors are visible
        errors.forEach(function(error) {
            let li = document.createElement('li');
            li.textContent = error;
            ul.appendChild(li);
        });
        targetDiv.appendChild(ul);
    }
}
    */

// Helper function for email validation (simple regex)
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Stricter password validation regex (from your example)
function isValidPassword(password) {
    // Min 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special char
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return passwordRegex.test(password);
}

// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {

    // --- DOM Element References ---
    // Get references only if they exist on the current page
    const loginForm = document.getElementById('loginForm');
    const signupForm = document.getElementById('signupForm');
    const loginSection = document.getElementById('loginSection');
    const signupSection = document.getElementById('signupSection');
    const showSignupLink = document.getElementById('showSignup');
    const showLoginLink = document.getElementById('showLogin');
    const errorMessagesDiv = document.getElementById('errorMessages');

    // Navbar elements (needed on pages with the main nav)
    const mainNavUl = document.getElementById('main-nav-ul');
    const signupNavLinkLi = document.getElementById('signup-link'); // The <li> element
    const loginNavLinkLi = document.getElementById('login-link');   // The <li> element

    // --- Utility Functions ---


    // --- Form Toggling Logic (Only needed on auth.html) ---
    if (showSignupLink && showLoginLink && loginSection && signupSection) {
        showSignupLink.addEventListener('click', function(event) {
            event.preventDefault();
            loginSection.style.display = 'none';
            signupSection.style.display = 'block';
            displayErrors([]); // Clear errors when switching
        });

        showLoginLink.addEventListener('click', function(event) {
            event.preventDefault();
            signupSection.style.display = 'none';
            loginSection.style.display = 'block';
            displayErrors([]); // Clear errors when switching
        });
    }

    // --- Login Form Logic (Only if loginForm exists on the page) ---
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault();
            displayErrors([]); // Clear previous errors

            const identifier = document.getElementById('loginUsername').value.trim();
            const password = document.getElementById('loginPassword').value;
            const users = getUsers();
            let errorMessages = [];
            let loginSuccess = false;

            if (!identifier) errorMessages.push('Please enter your username or email.');
            if (!password) errorMessages.push('Please enter your password.');

            if (errorMessages.length === 0) {
                // **SECURITY WARNING:** Comparing plain text passwords is insecure! Only for demo.
                const foundUser = users.find(user =>
                    (user.username === identifier || user.email === identifier) && user.password === password
                );

                if (foundUser) {
                    loginSuccess = true;
                    localStorage.setItem('loggedIn', 'true');
                    localStorage.setItem('currentUser', foundUser.username); // Store username
                    alert('Login successful!');
                    window.location.href = 'index.html'; // Redirect to homepage
                } else {
                    errorMessages.push('Invalid username/email or password.');
                }
            }

            if (!loginSuccess) {
                displayErrors(errorMessages);
            }
        });
    }

    // --- Signup Form Logic (Only if signupForm exists on the page) ---
    if (signupForm) {
        signupForm.addEventListener('submit', function(event) {
            event.preventDefault();
            displayErrors([]); // Clear previous errors

            let errorMessages = [];
            let username = document.getElementById('signupUsername').value.trim();
            let email = document.getElementById('signupEmail').value.trim();
            let password = document.getElementById('signupPassword').value;
            const users = getUsers();

            // Validation
            if (username.length < 3) errorMessages.push('Username must be at least 3 characters long.');
            if (users.some(user => user.username === username)) errorMessages.push('Username already exists. Please choose a different username.');

            if (!isValidEmail(email)) errorMessages.push('Please enter a valid email address.');
            if (users.some(user => user.email === email)) errorMessages.push('Email already exists. Please use a different email.');

            if (!isValidPassword(password)) {
                 errorMessages.push('Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a number, and a special character (@$!%*?&).');
            }
            // Note: No password repeat field in this version, add if needed.

            if (errorMessages.length === 0) {
                // Signup successful
                 // **SECURITY WARNING:** Storing plain text passwords is insecure! Only for demo.
                users.push({ username: username, email: email, password: password });
                saveUsers(users);

                localStorage.setItem('loggedIn', 'true');
                localStorage.setItem('currentUser', username); // Store username
                alert('Signup successful! You are now logged in.');
                window.location.href = 'index.html'; // Redirect to homepage
            } else {
                // Display validation errors
                displayErrors(errorMessages);
            }
        });
    }

    // --- Navbar Update Logic (Runs on all pages including this script) ---
    function updateNavbar() {
        const isLoggedIn = localStorage.getItem('loggedIn') === 'true';
        const existingLogoutLink = document.getElementById('logout-link'); // Check if logout LI exists

        // Hide/Show the static Login/Signup List Items based on login state
        if (signupNavLinkLi) signupNavLinkLi.style.display = isLoggedIn ? 'none' : '';
        if (loginNavLinkLi) loginNavLinkLi.style.display = isLoggedIn ? 'none' : '';

        if (isLoggedIn) {
            // User is Logged In: Add Sign Out link if it doesn't already exist
             // Ensure mainNavUl exists before trying to append to it
            if (mainNavUl) {
                //const logoutLi = document.createElement('li');
                //logoutLi.id = 'logout-link'; // Give the LI an ID

                //const logoutAnchor = document.createElement('a');
                var logoutAnchor = document.getElementById('logout-link');
                logoutAnchor.href = '#'; // Prevent page jump
                logoutAnchor.textContent = 'Sign Out';
                logoutAnchor.classList.add('cta-button'); // Style like other buttons (optional)
                logoutAnchor.style.cursor = 'pointer'; // Indicate it's clickable

                logoutAnchor.addEventListener('click', function(event) {
                    event.preventDefault(); // Stop the '#' link behavior
                    localStorage.removeItem('loggedIn');
                    localStorage.removeItem('currentUser');
                    alert('You have been signed out.');
                    // Redirect or reload to reflect the change everywhere
                     window.location.href = 'index.html'; // Redirecting ensures clean state
                });

                logoutLi.appendChild(logoutAnchor);
                mainNavUl.appendChild(logoutLi); // Append the new LI to the main nav list
            }
        } 
    }

    // --- Initial Execution ---
    updateNavbar(); // Call updateNavbar on every page load where this script is included

}); // End DOMContentLoaded

function SignupButton() {
    // Clear previous errors

    let errorMessages = [];
    let username = document.getElementById('signupUsername').value.trim();
    let email = document.getElementById('signupEmail').value.trim();
    let password = document.getElementById('signupPassword').value;

    // Validation
    if (username.length < 3) alert('Username must be at least 3 characters long.');
    //if (users.some(user => user.username === username)) alert('Username already exists. Please choose a different username.');

    if (!isValidEmail(email)) alert('Please enter a valid email address.');
    //if (users.some(user => user.email === email)) alert('Email already exists. Please use a different email.');

    if (!isValidPassword(password)) {
         alert('Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a number, and a special character (@$!%*?&).');
    }
    // Note: No password repeat field in this version, add if needed.

    if (errorMessages.length === 0) {
        // Signup successful
         // **SECURITY WARNING:** Storing plain text passwords is insecure! Only for demo.
        users.push({ username: username, email: email, password: password });
        //saveUsers(users);

        localStorage.setItem('loggedIn', 'true');
        localStorage.setItem('currentUser', username); // Store username
        alert('Signup successful! You are now logged in.');
        window.location.href = 'index.html'; // Redirect to homepage
    } 
}
