document.getElementById('myForm').addEventListener('submit', function(event) {
    event.preventDefault(); 
    
    let errorMessages = [];
    let username = document.getElementById('username').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    
    if (username.length < 3) {
        errormessage.push('Username must be at least 3 characters long.');
    }
    
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        errormessage.push('Please enter a valid email address.');
    }
    
    if (password.length < 6) {
        errormessage.push('Password must be at least 6 characters long.');
    }
    
 
        
    } else {
        // Submit the form if no errors
        alert('Form submitted successfully!');
        document.getElementById('myForm').submit();
    }
});
